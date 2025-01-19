package ec.edu.epn.rq_user

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import ec.edu.epn.rq_user.navigation.AppNavigation
import ec.edu.epn.rq_user.ui.theme.Rq_userTheme
import ec.edu.epn.rq_user.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    private lateinit var clienteOneTap: SignInClient
    private val authViewModel: AuthViewModel by viewModels()
    private val launcherResultadoOneTap = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { authViewModel.launcherResultadoOneTap(it, lifecycleScope, clienteOneTap) }

    fun iniciarGoogleOneTap() {
        authViewModel.iniciarGoogleOneTap(clienteOneTap, launcherResultadoOneTap)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clienteOneTap = Identity.getSignInClient(this)

        enableEdgeToEdge()
        setContent {
            Rq_userTheme {
                val navController = rememberNavController() // Inicializa el controlador de navegación

                AppNavigation(navController, authViewModel)
            }
        }
    }
}