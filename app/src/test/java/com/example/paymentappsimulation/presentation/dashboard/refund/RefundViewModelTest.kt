package com.example.paymentappsimulation.presentation.dashboard.refund

import com.example.paymentappsimulation.data.model.PaymentEntity
import com.example.paymentappsimulation.data.respository.SaleReposityLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class RefundViewModelTest {

    private lateinit var repository: SaleReposityLocal
    private lateinit var viewModel: RefundViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = RefundViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refundSale should insert refunded sale with updated fields`() = runTest {
        val original = PaymentEntity(
            id = 1,
            amount = 100.0,
            cardType = "TAP",
            timestamp = "1234567890",
            status = "approved",
            transactionId = "TX123"
        )

        // Act
        viewModel.refundSale(original, 25.0)
        advanceUntilIdle()

        // Assert
        val captor = argumentCaptor<PaymentEntity>()
        verify(repository).insertSale(captor.capture())

        val refunded = captor.firstValue
        Assert.assertEquals(25.0, refunded.amount, 0.01)
        Assert.assertEquals("refunded", refunded.status)
        Assert.assertTrue(refunded.transactionId.startsWith("R-"))
        Assert.assertNotEquals("1234567890", refunded.timestamp)
        Assert.assertEquals(0, refunded.id)
    }
}
