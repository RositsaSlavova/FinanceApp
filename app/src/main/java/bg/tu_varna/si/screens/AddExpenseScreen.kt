package bg.tu_varna.si.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.si.R
import bg.tu_varna.si.components.TransactionTopBar
import bg.tu_varna.si.ui.theme.*

@Composable
fun AddExpenseScreen(onBackClick: () -> Unit) {
    var date by remember { mutableStateOf("April 30 ,2024") }
    var amount by remember { mutableStateOf("$26,00") }
    var title by remember { mutableStateOf("Dinner") }
    var message by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(FinGreen)) {
        TransactionTopBar(title = "Add Expenses", onBackClick = onBackClick)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = FinBg,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExpenseInputField("Date", date, trailingIcon = R.drawable.ic_calender)
                ExpenseInputField("Category", "Select the category", trailingIcon = R.drawable.ic_arrow_down)
                ExpenseInputField("Amount", amount, onValueChange = { amount = it })
                ExpenseInputField("Expense Title", title, onValueChange = { title = it })

                Text("Enter Message", color = FinGreenLight, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = FinSurface2,
                        unfocusedContainerColor = FinSurface2,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { onBackClick() }, // Засега само ни връща назад
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FinGreen),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Save", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ExpenseInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    trailingIcon: Int? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            // ... останалата част от логиката на полето
        )
    }
}