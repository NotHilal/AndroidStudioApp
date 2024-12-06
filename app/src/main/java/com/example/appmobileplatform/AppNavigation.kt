package com.example.appmobileplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmobileplatform.HomeScreen
import com.example.appmobileplatform.LevelSelectionScreen
import com.example.appmobileplatform.LevelDesignScreen
import com.example.appmobileplatform.MyProfileScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch



@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("lvlselection") { LevelSelectionScreen(navController) }
        composable("level/{levelId}") { backStackEntry ->
            val levelId = backStackEntry.arguments?.getString("levelId")?.toInt() ?: 1
            LevelDesignScreen(navController, levelId)
        }
        composable("profile") { MyProfileScreen(navController) }
    }
}


@Composable
fun PermissionHandler(
    onPermissionsGranted: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    val cameraPermission = Manifest.permission.CAMERA

    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val permissions = listOf(cameraPermission, galleryPermission).toTypedArray()


    var allPermissionsGranted by remember {
        mutableStateOf(
            permissions.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    // Launcher to request permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsResult ->
            allPermissionsGranted = permissionsResult.all { it.value }
            if (allPermissionsGranted) {
                onPermissionsGranted()
            } else {

                coroutineScope.launch {

                }
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        if (!allPermissionsGranted) {
            permissionLauncher.launch(permissions)
        } else {
            onPermissionsGranted()
        }
    }

    if (!allPermissionsGranted) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
            Text(text = "Requesting permissions...")
        }
    }
}
