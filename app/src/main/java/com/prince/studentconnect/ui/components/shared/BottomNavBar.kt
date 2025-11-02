package com.prince.studentconnect.ui.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.prince.studentconnect.navigation.Screen
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController,
    currentRoute: String,
    authViewModel: AuthViewModel
) {
    val currentUserId by authViewModel.currentUserId.collectAsState()

    Column( modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .navigationBarsPadding()
                .padding(top = 8.dp, bottom = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {


            items.forEach { item ->
                val isSelected = currentRoute == item.route
                var itemRoute = item.route

                if (
                    itemRoute == Screen.StudentProfile.route ||
                    itemRoute == Screen.LecturerProfile.route ||
                    itemRoute == Screen.CampusAdminViewProfile.route ||
                    itemRoute == Screen.SystemAdminViewProfile.route
                    ) {
                    itemRoute = itemRoute.replace("{user_id}", currentUserId)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { navController.navigate(itemRoute) }
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        tint = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
