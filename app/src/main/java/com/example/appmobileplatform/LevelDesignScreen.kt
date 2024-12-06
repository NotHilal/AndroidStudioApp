package com.example.appmobileplatform

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


val map1 = arrayOf(
    intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
    intArrayOf(-1,  1,  1, -2, -3,  1, -4,  1,  1, -1,  1,  1,  1,  1, -1),
    intArrayOf(-1,  1,  1, -1, -4,  1, -3,  1,  1, -1,  1, -1, -3, -3, -1),
    intArrayOf(-1, -3, -3, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1,  1, -1),
    intArrayOf(-1,  1,  1, -1,  1,  1,  1, -1,  1, -1,  1, -1, -3, -3, -1),
    intArrayOf(-1,  1, -4, -1,  5,  1,  1, -2,  1, -1,  1, -1,  1,  1, -1),
    intArrayOf(-1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1, -1,  2,  1, -1),
    intArrayOf(-1, -4,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
    intArrayOf(-1, -4,  1, -1,  7,  1, -1,  1,  1,  1,  1,  1,  1,  1, -1),
    intArrayOf(-1,  1,  1, -1, -2, -2, -1,  1,  1,  1,  1,  1, -4,  2, -1),
    intArrayOf(-1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1),
    intArrayOf(-1, -4, -4, -4, -4, -4,  1,  1,  1, -4, -4, -4, -4, -4, -1),
    intArrayOf(-1, -4, -4, -4, -4, -4,  1,  1,  1, -4, -4, -4, -4, -4, -1),
    intArrayOf(-1, -4, -4, -4, -4, -4,  1,  0,  1, -4, -4, -4, -4, -4, -1),
    intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
)
val map2 = arrayOf(
    intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
    intArrayOf(-1, -3,  2, -1, -4,  1,  1,  1, -4,  1, -2,  1,  7,  1, -1),
    intArrayOf(-1,  1, -3, -1, -4,  1, -4,  1, -4,  1, -1,  1,  1,  1, -1),
    intArrayOf(-1,  1,  1, -1, -4,  1, -4,  1, -4,  1, -1,  3, -3,  3, -1),
    intArrayOf(-1,  8,  1, -1, 12,  1, -4,  1,  1,  1, -1,  1,  1,  1, -1),
    intArrayOf(-1, -3, -3, -1, -1, -1, -1, -1, -1, -1, -1,  1,  7,  1, -1),
    intArrayOf(-1,  3,  3, -1,  1, -3,  1,  7,  1, 13, -1,  1,  1,  1, -1),
    intArrayOf(-1, -3, -3, -1,  1,  1,  3,  1,  1,  1, -1,  1,  1,  1, -1),
    intArrayOf(-1,  1,  1, -1,  1, -1, -1, -1, -1, -1, -1,  1,  1,  1, -1),
    intArrayOf(-1,  1,  6, -1,  5, -1, 10, -1, -4, -4, -1,  1,  9,  1, -1),
    intArrayOf(-1,  3,  3, -1, -1, -1,  1, -1, -4, -4, -1,  1,  1,  1, -1),
    intArrayOf(-1, 11, -3, -1,  2, -1,  1,  1,  1,  3, -2,  1,  1,  1, -1),
    intArrayOf(-1, -1, -1, -1,  1, -1,  1, -1, -4, -4, -1,  1,  1,  1, -1),
    intArrayOf(-1,  0,  1,  1, -3,  3,  3, -1, -4, -4, -1,  1,  7,  1, -1),
    intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
)
val map3 = arrayOf(
    intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
    intArrayOf(-1,  2, 20, 20, 20, 20, 20, -1, -1, 20, 20, -3, 10, -1, -1),
    intArrayOf(-1, -1, 20, -1, -1, -4, 20, 20, 20, 20, 20, 20, 20, 20, -1),
    intArrayOf(-1, -1, 20, -1, -1, 20, 20, 20, 20, 20, 20, -1, -1, 20, -1),
    intArrayOf(-1, -1, 20, 20, 20, 20, 20, 20, 20, -1, 20, 20, 20, 20, -1),
    intArrayOf(-1, 20, 20, 20, 20, 20, 20, 20, 20, -1, 20, 20, 20, 20, -1),
    intArrayOf(-1, -4, 20, 20, -4, 20, 20, 20, 20, 20, 20, 20, -1, 20, -1),
    intArrayOf(-1, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, -1),
    intArrayOf(-1, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, -1),
    intArrayOf(-1, 20, 20, 20, 20, -1, 20, 20, 20, 20, -1, 20, -4, -4, -1),
    intArrayOf(-1, 20, 20, 20, 20, 20, 20, 20, -1, 20, -1, 20, -4, 11, -1),
    intArrayOf(-1, 20, -1, 20, 20, 20, 20, 20, 20, -4, 20, 20, -4,  1, -1),
    intArrayOf(-1, 20, 20, 20, 20, 20, -1, 20, 20, 20, 20, -1, -4, -2, -1),
    intArrayOf(-1,  0, 20, 20, 20, 20, -1, -1, 20, 20, 20, 20, -4,  5, -1),
    intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
)

@Composable
fun LevelDesignScreen(navController: NavController, levelId: Int,  ) {

    val initialMap = when (levelId) {
        1 -> map1
        2 -> map2
        3 -> map3
        else -> emptyArray()
    }

    val gameState = remember { GameState(initialMap) }

    if (levelId in 1..3) {
        StartLevel(navController, levelId, gameState)
    } else {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Text(
                text = "Designing Level $levelId",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopCenter)
            )


            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .width(140.dp)
                    .height(55.dp)
                    .offset(y = (-40).dp)
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btnmarron),
                    contentDescription = "Back Button Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Back",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun getTileImageResource(level: Int, value: Int): Int {
    return when (value) {
        0 -> LocalContext.current.resources.getIdentifier("lvl${level}player", "drawable", LocalContext.current.packageName)
        -1 -> LocalContext.current.resources.getIdentifier("lvl${level}wall", "drawable", LocalContext.current.packageName)
        1 -> LocalContext.current.resources.getIdentifier("lvl${level}ground", "drawable", LocalContext.current.packageName)
        4 -> LocalContext.current.resources.getIdentifier("lvl${level}ground", "drawable", LocalContext.current.packageName)
        2 -> LocalContext.current.resources.getIdentifier("lvl${level}key", "drawable", LocalContext.current.packageName)
        -2 -> LocalContext.current.resources.getIdentifier("lvl${level}doorside", "drawable", LocalContext.current.packageName)
        -3 -> LocalContext.current.resources.getIdentifier("lvl${level}spike", "drawable", LocalContext.current.packageName)
        3 -> LocalContext.current.resources.getIdentifier("lvl${level}nospike", "drawable", LocalContext.current.packageName)
        5 -> LocalContext.current.resources.getIdentifier("lvl${level}star", "drawable", LocalContext.current.packageName)
        6 -> LocalContext.current.resources.getIdentifier("lvl${level}save", "drawable", LocalContext.current.packageName)
        7 -> LocalContext.current.resources.getIdentifier("lvl${level}enemy", "drawable", LocalContext.current.packageName)
        8 -> LocalContext.current.resources.getIdentifier("lvl${level}timerplus", "drawable", LocalContext.current.packageName)
        9 -> LocalContext.current.resources.getIdentifier("lvl${level}freeze", "drawable", LocalContext.current.packageName)
        10 -> LocalContext.current.resources.getIdentifier("lvl${level}teleporter1", "drawable", LocalContext.current.packageName)
        11 -> LocalContext.current.resources.getIdentifier("lvl${level}teleporter1", "drawable", LocalContext.current.packageName)
        12 -> LocalContext.current.resources.getIdentifier("lvl${level}teleporter2", "drawable", LocalContext.current.packageName)
        13 -> LocalContext.current.resources.getIdentifier("lvl${level}teleporter2", "drawable", LocalContext.current.packageName)
        20 -> LocalContext.current.resources.getIdentifier("lvl${level}icefreeze", "drawable", LocalContext.current.packageName)
        else -> 0
    }
}

@Composable
fun StartLevel(navController: NavController, level: Int, gameState: GameState) {

    LaunchedEffect(Unit) {
        gameState.startTimer()
    }


    DisposableEffect(Unit) {
        onDispose {
            gameState.timeThreadRunning.set(false)
        }
    }


    var showTeleportDialog by remember { mutableStateOf(false) }
    var showDoorDialog by remember { mutableStateOf(false) }
    var showWinDialog by remember { mutableStateOf(false) }
    var showDeathDialog by remember { mutableStateOf(false) }
    var deathCause by remember { mutableStateOf<String?>(null) }
    var showIntroDialog by remember { mutableStateOf(true) }
    var currentPage by remember { mutableStateOf(0) }



    LaunchedEffect(gameState.etat) {
        when (gameState.etat) {
            1 -> showWinDialog = true
            -1 -> showDeathDialog = true
        }
    }
    val introData = levelIntros[level]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top 3/4: Game Tile Grid
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.wrapContentSize()
                ) {
                    for (row in gameState.map.indices) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for (column in gameState.map[row].indices) {
                                val value = gameState.map[row][column]
                                val resourceId = getTileImageResource(level, value)
                                if (value == -1) {
                                    val wallType = determineWallType(
                                        gameState.map.map { it.toIntArray() }.toTypedArray(),
                                        row,
                                        column,
                                        level
                                    )
                                    val wallResourceId = getWallImageResource(level, wallType)
                                    Image(
                                        painter = painterResource(id = wallResourceId),
                                        contentDescription = "Wall",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .width(26.dp)
                                            .height(40.dp)
                                    )
                                } else if (resourceId != 0) {
                                    Image(
                                        painter = painterResource(id = resourceId),
                                        contentDescription = "Tile",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .width(26.dp)
                                            .height(40.dp)
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .width(26.dp)
                                            .height(40.dp)
                                            .background(
                                                getTileColor(value),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .padding(1.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = getTileLabel(value),
                                            color = Color.White,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom 1/4: Controls
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = 24.dp, y = -68.dp)
                        .clip(CircleShape)
                        .clickable { gameState.useFreeze() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.freezebtn),
                        contentDescription = "Use Freeze Button Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }


                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = 115.dp, y = 10.dp)
                        .clip(CircleShape)
                        .clickable {
                            if (gameState.canOpenDoor()) {
                                showDoorDialog = true // Show confirmation dialog
                            } else {

                                println("Vous ne pouvez pas ouvrir la porte.")
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.keybtn),
                        contentDescription = "Key Button Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }


                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = 195.dp, y = -68.dp)
                        .clip(CircleShape)
                        .clickable {
                            gameState.useTP { canTeleport ->
                                if (canTeleport) {
                                    showTeleportDialog = true
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tpbtn),
                        contentDescription = "TP Button Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }



                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = -160.dp, y = 115.dp)
                        .clip(CircleShape)
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.exitbtn),
                        contentDescription = "Back Button Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))


                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    val buttonWidthPx = 220f
                    val buttonHeightPx = 220f
                    val spacingPx = 24f


                    val buttonWidthDp = with(LocalDensity.current) { buttonWidthPx.toDp() }
                    val buttonHeightDp = with(LocalDensity.current) { buttonHeightPx.toDp() }
                    val spacingDp = with(LocalDensity.current) { spacingPx.toDp() }

                    Image(
                        painter = painterResource(id = R.drawable.moveup),
                        contentDescription = "Move Up",
                        modifier = Modifier
                            .width(buttonWidthDp)
                            .height(buttonHeightDp)
                            .align(Alignment.TopCenter)
                            .offset(x = -115.dp, y = (-12).dp)
                            .padding(bottom = spacingDp)
                            .clickable { gameState.moveJ(Direction.HAUT) }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.moveleft),
                        contentDescription = "Move Left",
                        modifier = Modifier
                            .width(buttonWidthDp)
                            .height(buttonHeightDp)
                            .align(Alignment.CenterStart)
                            .padding(end = spacingDp)
                            .offset(x = (-157).dp, y = 10.dp)
                            .scale(1.08f)
                            .clickable { gameState.moveJ(Direction.GAUCHE) }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.moveright),
                        contentDescription = "Move Right",
                        modifier = Modifier
                            .width(buttonWidthDp)
                            .height(buttonHeightDp)
                            .align(Alignment.CenterEnd)
                            .padding(start = spacingDp)
                            .offset(x = (-70).dp, y = 10.dp)
                            .scale(1.08f)
                            .clickable { gameState.moveJ(Direction.DROITE) }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.movedown),
                        contentDescription = "Move Down",
                        modifier = Modifier
                            .width(buttonWidthDp)
                            .height(buttonHeightDp)
                            .align(Alignment.BottomCenter)
                            .padding(top = spacingDp)
                            .offset(x = -115.dp, y = 32.dp)
                            .clickable { gameState.moveJ(Direction.BAS) }
                    )
                }
            }


            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(80.dp)
                    .offset(x = 260.dp, y = -1.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.timerframe),
                    contentDescription = "Timer Frame",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Time:${gameState.timer}s\nDeaths: ${gameState.nb_mort} ",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 7.dp)
                )

            }




        }

        if (showIntroDialog && introData != null) {
            val totalPages = introData.pages.size
            val currentIntroPage = introData.pages[currentPage]

            AlertDialog(
                onDismissRequest = { /* Prevent dismissal by clicking outside */ },
                title = {
                    Text(
                        text = introData.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = currentIntroPage.description,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = currentIntroPage.imageRes),
                            contentDescription = "Introductory Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                },
                confirmButton = {
                    Row {
                        if (currentPage > 0) {
                            TextButton(
                                onClick = { currentPage-- }
                            ) {
                                Text("Précédent")
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (currentPage < totalPages - 1) {
                            TextButton(
                                onClick = { currentPage++ }
                            ) {
                                Text("Suivant")
                            }
                        } else {
                            TextButton(
                                onClick = { showIntroDialog = false }
                            ) {
                                Text(
                                    text = "Continuer",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                },
                containerColor = Color.White.copy(alpha = 0.95f),
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            )
        }

        if (showTeleportDialog) {
            AlertDialog(
                onDismissRequest = { showTeleportDialog = false },
                title = { Text(text = "Téléportation", fontWeight = FontWeight.Bold) },
                text = {
                    Text(
                        text = "Voulez-vous utiliser le téléporteur ?",
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            gameState.moveTP()
                            showTeleportDialog = false
                        }
                    ) {
                        Text("Oui")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showTeleportDialog = false }
                    ) {
                        Text("Non")
                    }
                }
            )
        }

        if (showDoorDialog) {
            AlertDialog(
                onDismissRequest = { showDoorDialog = false },
                title = { Text(text = "Ouvrir la porte", fontWeight = FontWeight.Bold) },
                text = {
                    Text(
                        text = "Voulez-vous utiliser une clé pour ouvrir la porte ?",
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            gameState.useKey()
                            showDoorDialog = false
                        }
                    ) {
                        Text("Oui")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDoorDialog = false }
                    ) {
                        Text("Non")
                    }
                }
            )
        }

        if (showWinDialog) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text(text = "Victoire", fontWeight = FontWeight.Bold) },
                text = {
                    Text(
                        text = "🎉 Félicitations, vous avez gagné ! 🎉",
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Unlock the next level
                            currentLvl += 1

                            navController.navigate("lvlselection") {
                                popUpTo("lvlselection") { inclusive = true }
                            }
                            showWinDialog = false
                        }
                    ) {
                        Text("Retour à la sélection de niveau")
                    }
                }
            )
        }

        if (showDeathDialog) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text(text = "Défaite", fontWeight = FontWeight.Bold) },
                text = {
                    when (deathCause) {
                        "enemy" -> Text(
                            text = "💀 Un ennemi vous a touché ! Vous êtes mort.",
                            textAlign = TextAlign.Center
                        )
                        "spike" -> Text(
                            text = "💀 Vous êtes mort sur un pic.",
                            textAlign = TextAlign.Center
                        )
                        else -> Text(
                            text = "💀 Vous êtes mort.",
                            textAlign = TextAlign.Center
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {

                            gameState.reset()
                            showDeathDialog = false
                        }
                    ) {
                        Text("Réessayer")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            // Navigate back to level selection screen
                            navController.navigate("lvlselection") {
                                popUpTo("lvlselection") { inclusive = true }
                            }
                            showDeathDialog = false
                        }
                    ) {
                        Text("Abandonner")
                    }
                }
            )
        }

        if (gameState.showTimeUpDialog) {
            AlertDialog(
                onDismissRequest = { gameState.showTimeUpDialog = false },
                title = { Text(text = "Temps écoulé", fontWeight = FontWeight.Bold) },
                text = {
                    Text(
                        text = "⏰ Votre temps est écoulé. Voulez-vous réessayer ?",
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            gameState.reset() // Reset the game
                            gameState.showTimeUpDialog = false // Close the dialog
                        }
                    ) {
                        Text("Réessayer")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            navController.navigate("lvlselection") {
                                popUpTo("lvlselection") { inclusive = true }
                            }
                            gameState.showTimeUpDialog = false // Close the dialog
                        }
                    ) {
                        Text("Abandonner")
                    }
                }
            )
        }

    }

}

fun getTileColor(value: Int): Color {
    return when (value) {
        -1 -> Color.Blue // Wall
        1 -> Color.DarkGray // Path
        -3 -> Color.Red // Spikes
        -4 -> Color.Black // Hole
        5 -> Color.Yellow // Goal
        0 -> Color.Blue // Player
        else -> Color.Transparent // Default
    }
}

fun getTileLabel(value: Int): String {
    return when (value) {
        2 -> "🔑"
        5 -> "⭐"
        6 -> "@"
        7 -> "X"
        8 -> "⏰"
        9 -> "❄️"
        else -> ""
    }
}

fun determineWallType(map: Array<IntArray>, row: Int, column: Int, level:Int): WallType {
    val isTop = row == 0
    val isBottom = row == map.lastIndex
    val isLeft = column == 0
    val isRight = column == map[row].lastIndex

    return when {
        isTop && isLeft -> WallType.TopLeftCorner
        isTop && isRight -> WallType.TopRightCorner
        isBottom && isLeft -> WallType.BottomLeftCorner
        isBottom && isRight -> WallType.BottomRightCorner
        isTop -> WallType.Top
        isBottom -> WallType.Bottom
        isLeft -> WallType.Left
        isRight -> WallType.Right

        else -> determineInnerWallType(map, row, column,level)
    }
}

fun determineInnerWallType(map: Array<IntArray>, row: Int, column: Int, lvl: Int): WallType {

    if (lvl == 3) {
        return WallType.Inner
    }

    val hasWallRight = column < map[row].lastIndex && map[row][column + 1] == -1
    val hasWallLeft = column > 0 && map[row][column - 1] == -1
    val hasWallAbove = row > 0 && map[row - 1][column] == -1
    val hasWallBelow = row < map.lastIndex && map[row + 1][column] == -1

    return when {

        hasWallRight || hasWallLeft -> WallType.Top

        hasWallAbove || hasWallBelow -> WallType.Right

        else -> WallType.Inner
    }
}

enum class WallType {
    Top, Bottom, Left, Right,
    TopLeftCorner, TopRightCorner,
    BottomLeftCorner, BottomRightCorner,
    Inner
}

@Composable
fun getWallImageResource(level: Int, wallType: WallType): Int {
    val resourcePrefix = "lvl${level}wall"
    val resourceName = when (wallType) {
        WallType.Top -> "${resourcePrefix}top"
        WallType.Bottom -> "${resourcePrefix}bot"
        WallType.Left -> "${resourcePrefix}left"
        WallType.Right -> "${resourcePrefix}right"
        WallType.TopLeftCorner -> "${resourcePrefix}topleft"
        WallType.TopRightCorner -> "${resourcePrefix}topright"
        WallType.BottomLeftCorner -> "${resourcePrefix}botleft"
        WallType.BottomRightCorner -> "${resourcePrefix}botright"
        WallType.Inner -> "${resourcePrefix}inner"
    }

    return LocalContext.current.resources.getIdentifier(resourceName, "drawable", LocalContext.current.packageName)
}
