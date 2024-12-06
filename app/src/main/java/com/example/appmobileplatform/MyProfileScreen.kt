package com.example.appmobileplatform

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun MyProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var userName by remember { mutableStateOf("User") }
    var showDialog by remember { mutableStateOf(false) }
    var newUserName by remember { mutableStateOf("") }

    val userNameFlow = getUsername(context)
    val userNameState by userNameFlow.collectAsState(initial = "User")
    userName = userNameState


    var profilePicUri by remember { mutableStateOf<String?>(null) }


    val profilePicUriFlow = getProfilePicUri(context)
    val profilePicUriState by profilePicUriFlow.collectAsState(initial = "")
    profilePicUri = if (profilePicUriState.isNotEmpty()) profilePicUriState else null

    val snackbarHostState = remember { SnackbarHostState() }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                // Persist the URI permission
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                profilePicUri = uri.toString()

                coroutineScope.launch {
                    saveProfilePicUri(context, uri.toString())
                    snackbarHostState.showSnackbar("Profile picture updated")
                }
            }
        }
    )

    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val isGalleryPermissionGranted = ContextCompat.checkSelfPermission(
        context,
        galleryPermission
    ) == PackageManager.PERMISSION_GRANTED


    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {

                imagePickerLauncher.launch(arrayOf("image/*"))
            } else {

                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Gallery permission denied")
                }
            }
        }
    )


    fun onChangePictureClicked() {
        if (isGalleryPermissionGranted) {

            imagePickerLauncher.launch(arrayOf("image/*"))
        } else {
            // Request gallery permission
            galleryPermissionLauncher.launch(galleryPermission)
        }
    }

    val imageSizePx = 750f
    val imageSizeDp = with(LocalDensity.current) { imageSizePx.toDp() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Image(
                painter = painterResource(id = R.drawable.profilebg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "My Profile",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 32.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = userName,
                        fontSize = 25.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (profilePicUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(profilePicUri),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(imageSizeDp)
                                .clip(RoundedCornerShape(imageSizeDp / 2))
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.profilenopic),
                            contentDescription = "Default Profile Picture",
                            modifier = Modifier
                                .size(imageSizeDp)
                                .clip(RoundedCornerShape(imageSizeDp / 2))
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(55.dp)
                                .clickable {
                                    onChangePictureClicked()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.btncyan),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = "Change Picture",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(55.dp)
                                .clickable {
                                    showDialog = true
                                    newUserName = userName
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.btncyan),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = "Change Name",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(140.dp)
                        .height(55.dp)
                        .clickable { navController.navigate("home") },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btnmarron),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Main Menu",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }


            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(text = "Change Name")
                    },
                    text = {
                        Column {
                            Text(text = "Enter your new name:")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = newUserName,
                                onValueChange = { newUserName = it },
                                singleLine = true
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newUserName.isNotBlank()) {
                                    userName = newUserName
                                    coroutineScope.launch {
                                        saveUsername(context, newUserName)
                                        snackbarHostState.showSnackbar("Username updated")
                                    }
                                }
                                showDialog = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
