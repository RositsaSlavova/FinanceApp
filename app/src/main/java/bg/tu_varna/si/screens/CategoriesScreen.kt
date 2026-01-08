package bg.tu_varna.si.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.si.components.BalanceRow
import bg.tu_varna.si.components.ProgressSection
import bg.tu_varna.si.components.TransactionTopBar
import bg.tu_varna.si.data.CategoryItem
import bg.tu_varna.si.data.allCategories
import bg.tu_varna.si.ui.theme.FinBg
import bg.tu_varna.si.ui.theme.FinExpenseBlue
import bg.tu_varna.si.ui.theme.FinGreen
import androidx.compose.material3.Surface
import androidx.compose.foundation.lazy.grid.items

@Composable
fun CategoriesScreen(onCategoryClick: (String) -> Unit,
                     onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(FinGreen)) {
        // Използваме твоя центриран TopBar
        TransactionTopBar(title = "Categories", onBackClick = onBackClick)

        BalanceRow(7783.0, 1187.40, false, FinExpenseBlue)
        Spacer(Modifier.height(16.dp))
        ProgressSection(30, 20000.0, false)
        Spacer(Modifier.height(24.dp))

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = FinBg,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(allCategories) { cat ->
                    CategoryCard(cat, onClick = { onCategoryClick(cat.name) })
                }
            }
        }
    }
}

@Composable
fun CategoryCard(cat: CategoryItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(cat.color, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(cat.iconRes), null, Modifier.size(40.dp), Color.White)
        }
        Text(cat.name, modifier = Modifier.padding(top = 8.dp), fontSize = 14.sp)
    }
}