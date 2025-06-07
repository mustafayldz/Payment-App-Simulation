package com.example.paymentappsimulation.presentation.dashboard.sale

import com.example.paymentappsimulation.data.model.PaymentEntity
import com.example.paymentappsimulation.data.model.PaymentRequestModel
import com.example.paymentappsimulation.data.model.PaymentResponseModel
import com.example.paymentappsimulation.data.respository.SaleReposityLocal
import com.example.paymentappsimulation.data.respository.SaleReposityRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class SaleViewModelTest {

    private lateinit var apiService: SaleReposityRemote
    private lateinit var repository: SaleReposityLocal
    private lateinit var viewModel: SaleViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        apiService = mock()
        repository = mock()
        viewModel = SaleViewModel(apiService, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `makePayment should call API and insert sale when approved`() = runTest {
        // Arrange
        val amount = 100.0
        val tip = 20.0
        val cardType = "TAP"

        whenever(apiService.makePayment(any<PaymentRequestModel>()))
            .thenReturn(PaymentResponseModel(status = "approved", transactionId = "TX-MOCK", message = "OK"))

        // Act
        viewModel.makePayment(amount, tip, cardType)
        advanceUntilIdle()

        // Assert
        val requestCaptor = argumentCaptor<PaymentRequestModel>()
        verify(apiService).makePayment(requestCaptor.capture())

        val request = requestCaptor.firstValue
        Assert.assertEquals(amount, request.amount, 0.01)
        Assert.assertEquals(tip, request.tip, 0.01)
        Assert.assertEquals(cardType, request.cardType)

        val entityCaptor = argumentCaptor<PaymentEntity>()
        verify(repository).insertSale(entityCaptor.capture())

        val inserted = entityCaptor.firstValue
        Assert.assertEquals(amount + tip, inserted.amount, 0.01)
        Assert.assertEquals(cardType, inserted.cardType)
        Assert.assertEquals("approved", inserted.status)
        Assert.assertTrue(inserted.transactionId.isNotBlank())
        Assert.assertTrue(inserted.timestamp.isNotBlank())
    }

}
