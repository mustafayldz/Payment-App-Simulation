# 💳 PaymentAppSimulation

**PaymentAppSimulation** is a POS (Point of Sale) simulation app for Android. It allows users to create sales, view all transactions, and make refunds. It uses local storage and follows a clean architecture with MVVM.

---

## 🔐 Login Information

To simulate login, use the following credentials:

- **Username**: `rapidcent`
- **Password**: `qwe123`

---

## 🧪 Simulation Behavior

- **Card Type**: A random card type (e.g., TAP, CHIP, MANUEL) is assigned automatically when a sale is made.
- **High Amounts**: If the total amount (sale + tip) is **unrealistically high**, the transaction will simulate a failure response (used for testing error handling).

---


## 🛠 Features

- Create a new sale with amount, card type, and timestamp
- List all past transactions
- Make partial refunds
- Save data using Room database
- MVVM architecture with ViewModel
- Coroutine-based logic
- Unit testing with Mockito and JUnit
- Uses Retrofit for fake API requests
- Animation and receipt success screen
- (Coming soon) Printable receipts

---

## 🏗 Architecture

The project is structured like this:

com.example.paymentappsimulation
│
├── data
│   ├── db                # (optional for DB instance setup)
│   ├── local             # SaleDao (Room DAO)
│   ├── model             # PaymentEntity.kt
│   ├── remote            # ApiService.kt (mock or future API)
│   ├── repository        # Local and remote repositories
│   └── util              # Enums and time utils
│
├── di                    # Dependency Injection modules (Hilt)
│
├── navigation            # NavGraph and Screen routes
│
├── presentation
│   ├── dashboard
│   │   ├── components    # Shared UI components
│   │   ├── history       # Transaction history with filters
│   │   ├── refund        # Refund screen with transaction ID search
│   │   ├── sale          # New Sale screen
│   │   └── voidScreen    # Void screen for transactions
│   ├── login             # Simple login screen with username saving
│   └── ui.theme          # App theme configuration
│
├── MainActivity.kt       # Entry point
└── PaSumilation.kt       # (main app composable)
