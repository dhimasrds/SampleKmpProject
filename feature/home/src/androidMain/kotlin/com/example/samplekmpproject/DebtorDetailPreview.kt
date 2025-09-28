package com.example.samplekmpproject

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Preview(
    showBackground = true,
    apiLevel = 35,
    device = Devices.PIXEL_4
)
@Composable
fun DebtorDetailPreview() {
    MaterialTheme {
        DebtorDetailScreen(
            state = DebtorDetailUiState(
                name = "John Doe",
                userId = "JD123456",
                isPaidOff = false,
                discountVisible = true,
                vaVisible = true,
                dpdValue = "30 days",
                outstandingAmount = "Rp 5.000.000",
                lastPayment = "Rp 500.000",
                lastPaymentDate = "01 Sep 2025",
                lastPaymentMethod = "Bank Transfer",
                fullName = "John Alexander Doe",
                gender = "Male",
                dob = "15 Jan 1990",
                religion = "Catholic",
                maritalStatus = "Married",
                phoneNumber = "+62 812 3456 7890",
                employerName = "PT Example Company",
                activationDate = "01 Jan 2025"
            ),
            onMakeReport = {},
            onViewDiscount = {},
            onViewVaNumber = {},
            onCopy = { _, _ -> }
        )
    }
}
