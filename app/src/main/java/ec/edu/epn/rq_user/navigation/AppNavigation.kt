package ec.edu.epn.rq_user.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ec.edu.epn.rq_user.MainActivity
import ec.edu.epn.rq_user.NavBar
import ec.edu.epn.rq_user.uin.ExploraScreen
import ec.edu.epn.rq_user.uin.CreaRutaScreen
import ec.edu.epn.rq_user.uin.FavoritasScreen
import ec.edu.epn.rq_user.uin.loginSignup.LogInScreen
import ec.edu.epn.rq_user.uin.PerfilScreen
import ec.edu.epn.rq_user.uin.loginSignup.RecuperarCuentaScreen
import ec.edu.epn.rq_user.uin.loginSignup.SignUpScreen
import ec.edu.epn.rq_user.uin.profile.UserEmailScreen
import ec.edu.epn.rq_user.uin.profile.UserHouseScreen
import ec.edu.epn.rq_user.uin.profile.UserInfoScreen
import ec.edu.epn.rq_user.uin.profile.UserNameScreen
import ec.edu.epn.rq_user.uin.profile.UserPhoneScreen
import ec.edu.epn.rq_user.uin.profile.UserSettingsScreen
import ec.edu.epn.rq_user.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
  navController: NavHostController,
  authViewModel: AuthViewModel,
  modifier: Modifier = Modifier,
) {
  val usuarioAutenticado by authViewModel.usuarioAutenticado.collectAsState()
  val errorMessage by authViewModel.errorMessage.collectAsState()

  Scaffold(
    bottomBar = { if (usuarioAutenticado) NavBar(navController) },  // ✅ Se asegura que el NavBar esté presente
    modifier = modifier
  ) { innerPadding ->
    NavHost(
      navController = navController,
      startDestination = if (usuarioAutenticado) "explora" else "login",
      modifier = Modifier
        .fillMaxSize()  // ✅ Asegurar que el NavHost use el espacio correctamente
        .padding(innerPadding)  // ✅ Evita que el contenido se superponga con el NavBar
    ) {
      // Pantallas Módulo LogIn/SignUp
      composable("login") { LogInScreen(
        nav = navController,
        onLoginSuccess = { correo, contrasena -> authViewModel.iniciarSesion(correo, contrasena) },
        onGoogleLogin = { (navController.context as? MainActivity)?.iniciarGoogleOneTap() },
        errorMessage = errorMessage
      ) }
      composable("signup") { SignUpScreen(navController) }
      composable("recuperar") { RecuperarCuentaScreen(navController) }

      // Pantallas de la barra de navegación
      composable("explora") { ExploraScreen(navController) }
      composable("crearuta") { CreaRutaScreen(navController) }
      composable("favoritas") { FavoritasScreen(navController) }
      composable("perfil") { PerfilScreen(navController, authViewModel::cerrarSesion) }

      // PROFILE SCREENS
      composable("configuracion") { UserSettingsScreen(navController)}
      composable("informacion") { UserInfoScreen(navController) }
      composable("updateNombre") { UserNameScreen(navController) }
      composable("updateTelefono") { UserPhoneScreen(navController) }
      composable("updateEmail") { UserEmailScreen(navController) }
      composable("updateHouse") { UserHouseScreen(navController) }
    }
  }
}
