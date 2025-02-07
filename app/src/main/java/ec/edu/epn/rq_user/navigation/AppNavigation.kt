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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ec.edu.epn.rq_user.MainActivity
import ec.edu.epn.rq_user.NavBar
import ec.edu.epn.rq_user.uin.ExploraScreen
import ec.edu.epn.rq_user.uin.CrearRutaScreen
import ec.edu.epn.rq_user.uin.FavoritasScreen
import ec.edu.epn.rq_user.uin.loginSignup.LogInScreen
import ec.edu.epn.rq_user.uin.PerfilScreen
import ec.edu.epn.rq_user.uin.RutaDetalleScreen
import ec.edu.epn.rq_user.uin.RutaDetalleScreen2
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
      startDestination = if (usuarioAutenticado) "crearuta" else "crearuta",
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
      composable(
        "explora?startLat={startLat}&startLng={startLng}&endLat={endLat}&endLng={endLng}",
        arguments = listOf(
          navArgument("startLat") { type = NavType.FloatType },
          navArgument("startLng") { type = NavType.FloatType },
          navArgument("endLat") { type = NavType.FloatType },
          navArgument("endLng") { type = NavType.FloatType }
        )
      ) { backStackEntry ->
        val startLat = backStackEntry.arguments?.getFloat("startLat") ?: 0f
        val startLng = backStackEntry.arguments?.getFloat("startLng") ?: 0f
        val endLat = backStackEntry.arguments?.getFloat("endLat") ?: 0f
        val endLng = backStackEntry.arguments?.getFloat("endLng") ?: 0f

        ExploraScreen(
          navController = navController,
          startLat = startLat,
          startLng = startLng,
          endLat = endLat,
          endLng = endLng
        )
      }
      composable("crearuta") { CrearRutaScreen(navController) }
      composable("favoritas") { FavoritasScreen(navController) }
      composable("perfil") { PerfilScreen(navController, authViewModel::cerrarSesion) }
      // Ruta detalle - Lesly
      composable("ruta_detalle1/{rutaNombre}") { backStackEntry ->
        val rutaNombre = backStackEntry.arguments?.getString("rutaNombre")
        if (rutaNombre != null) {
          RutaDetalleScreen(navController = navController, rutaNombre = rutaNombre)
        }
      }
      composable("ruta_detalle2/{rutaNombre}") { backStackEntry ->
        val rutaNombre = backStackEntry.arguments?.getString("rutaNombre")
        if (rutaNombre != null) {
          RutaDetalleScreen2(navController = navController, rutaNombre = rutaNombre)
        }
      }

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
