package com.prince.studentconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.prince.studentconnect.navigation.RootNavGraph
import com.prince.studentconnect.ui.theme.BaseScreen
import com.prince.studentconnect.ui.theme.StudentConnectTheme
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentConnectTheme {
                StudentConnectApp()
            }
        }
    }
}

@Composable
fun StudentConnectApp() {
    BaseScreen {
        val navController = rememberNavController()
        RootNavGraph(navController = navController)
    }
}
