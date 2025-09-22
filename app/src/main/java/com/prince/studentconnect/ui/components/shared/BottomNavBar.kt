package com.prince.studentconnect.ui.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Icon

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController,
    currentRoute: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = 8.dp,
                bottom = 20.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { navController.navigate(item.route) }
                    .padding(horizontal = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.label,
                    tint = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = item.label,
                    color = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
