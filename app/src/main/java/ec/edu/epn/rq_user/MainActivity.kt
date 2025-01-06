package ec.edu.epn.rq_user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import ec.edu.epn.rq_user.navigation.AppNavigation
import ec.edu.epn.rq_user.ui.theme.Rq_userTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Rq_userTheme {
                val navController = rememberNavController() // Inicializa el controlador de navegación
                AppNavigation(navController)
            }
        }
    }
}