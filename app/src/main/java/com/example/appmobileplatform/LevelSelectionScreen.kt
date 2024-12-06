package com.example.appmobileplatform

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController

var currentLvl = 1

@Composable
fun LevelSelectionScreen(navController: NavController) {
    // State to control the visibility of the dialog
    var showDialog by remember { mutableStateOf(true) }

    // List of images for each level
    val levelImages = listOf(
        R.drawable.lvl1prison,
        R.drawable.lvl2cave,
        R.drawable.lvl3ice
    )

    // Background
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.lvlselection),
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
            // Scrollable content with LazyColumn
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(levelImages.size) { index ->
                    val levelNumber = index + 1
                    val isLocked = levelNumber > currentLvl

                    LevelItem(
                        imageRes = levelImages[index],
                        levelName = "Level $levelNumber",
                        isLocked = isLocked,
                        onButtonClick = {
                            if (!isLocked) {
                                navController.navigate("level/$levelNumber")
                            }
                        }
                    )
                }
            }

            // Back to Home Button
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

        // Conditional AlertDialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { /* Prevent dismissal by clicking outside */ },
                title = {
                    Text(
                        text = "Welcome young Time Traveler",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Here are all the missions we assigned you, we expect great things from a talented person like you but we will slowly test you and make the missions harder and harder, so try your best !",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.robot), // Replace with your explanatory image resource
                            contentDescription = "Explanation Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text(
                            text = "Continue",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                containerColor = Color.White.copy(alpha = 0.9f),
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            )
        }
    }
}

@Composable
fun LevelItem(imageRes: Int, levelName: String, isLocked: Boolean, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Changed from fillMaxSize to fillMaxWidth for better layout
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image for the level (placeholder for locked levels)
        Image(
            painter = painterResource(
                id = if (isLocked) R.drawable.question_mark else imageRes
            ),
            contentDescription = if (isLocked) "Locked Level" else levelName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Adjusted height for better UI
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Level Button
        Box(
            modifier = Modifier
                .padding(16.dp)
                .width(140.dp)
                .height(55.dp)
                .clickable(enabled = !isLocked) { onButtonClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.btnjaune),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = if (isLocked) "Locked" else levelName,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}
