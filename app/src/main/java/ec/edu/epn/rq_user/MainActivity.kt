package ec.edu.epn.rq_user

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import ec.edu.epn.rq_user.navigation.AppNavigation
import ec.edu.epn.rq_user.ui.theme.Rq_userTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Rq_userTheme {
                val navController = rememberNavController() // Inicializa el controlador de navegación
                val logged = remember { mutableStateOf(false) } // (provisional) Variable global para el estado de autenticación

                AppNavigation(navController, logged=logged)
            }
        }
    }
}