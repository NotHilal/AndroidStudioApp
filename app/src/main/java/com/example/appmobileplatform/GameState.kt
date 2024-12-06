package com.example.appmobileplatform

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread
import kotlin.math.abs
import android.os.Handler
import android.os.Looper

enum class Direction {
    HAUT,
    BAS,
    DROITE,
    GAUCHE
}

class GameState(initialMap: Array<IntArray>) {

    var map: SnapshotStateList<SnapshotStateList<Int>> = mutableStateListOf()
    var larg: Int
    var long: Int
    var id: Int = 1
    var etat by mutableStateOf(0)
    var case_atm: Int = 1
    var nb_mort: Int = 0
    var nb_key: Int = 0
    var nb_tour: Int = 1
    var jouer: Boolean = true
    var checkpointState: Triple<Array<IntArray>, Pair<Int, Int>, Triple<Int, Int, Int>>? = null
    var timer by mutableStateOf(180)
    var timeThreadRunning = AtomicBoolean(true)
    val initialEnemyPositions = mutableListOf<Pair<Int, Int>>()
    var nb_freeze: Int = 0
    var freezeEnemiesTurns = 0
    var showTimeUpDialog by mutableStateOf(false)
    var cptflip : Int = 0
    var deathCause by mutableStateOf<String?>(null)

    private val initialMapCopy: Array<IntArray> = initialMap.map { it.copyOf() }.toTypedArray()

    init {
        long = initialMap.size
        larg = initialMap[0].size
        for (row in initialMap) {
            val mutableRow = row.toList().toMutableStateList()
            map.add(mutableRow)
        }
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 7) {
                    initialEnemyPositions.add(Pair(i, j))
                }
            }
        }
    }

    fun getPos(): Pair<Int, Int> {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 0) {
                    return Pair(i, j)
                }
            }
        }
        return Pair(-1, -1)
    }

    fun moveJ(d: Direction) {

        val startPos = getPos()
        if (startPos.first == -1 || startPos.second == -1) return

        val (startRow, startCol) = startPos


        val (targetRow, targetCol) = when (d) {
            Direction.HAUT -> Pair(startRow - 1, startCol)
            Direction.BAS -> Pair(startRow + 1, startCol)
            Direction.DROITE -> Pair(startRow, startCol + 1)
            Direction.GAUCHE -> Pair(startRow, startCol - 1)
        }


        if (targetRow < 0 || targetRow >= long || targetCol < 0 || targetCol >= larg) {

            return
        }


        if (map[targetRow][targetCol] == -1) {

            return
        }


        picMove()



        if (startPos.first != -1 && startPos.second != -1) {
            val tile = map[startPos.first][startPos.second]
            if (tile == -3) {
                println("💀 Vous êtes mort sur un pic devenu dangereux sous vos pieds !")
                etat = -1
                verifWinDead()
                return
            }
        }



        val startTile = if (startRow != -1 && startCol != -1) map[startRow][startCol] else 1

        val p: Pair<Int, Int> = startPos
        var currentRow = p.first
        var currentCol = p.second
        var sliding = false

        do {
            when (d) {
                Direction.HAUT -> if ((map[currentRow - 1][currentCol] > 0 || map[currentRow - 1][currentCol] == -3 || map[currentRow - 1][currentCol] == 20)) {
                    map[currentRow][currentCol] = case_atm
                    case_atm = map[currentRow - 1][currentCol]
                    map[currentRow - 1][currentCol] = 0
                    currentRow--
                    sliding = (case_atm == 20)
                } else if (map[currentRow - 1][currentCol] < -3) {
                    etat = -1
                    sliding = false
                } else sliding = false

                Direction.BAS -> if ((map[currentRow + 1][currentCol] > 0 || map[currentRow + 1][currentCol] == -3 || map[currentRow + 1][currentCol] == 20)) {
                    map[currentRow][currentCol] = case_atm
                    case_atm = map[currentRow + 1][currentCol]
                    map[currentRow + 1][currentCol] = 0
                    currentRow++
                    sliding = (case_atm == 20)
                } else if (currentRow + 1 < long && map[currentRow + 1][currentCol] < -3) {
                    etat = -1
                    sliding = false
                } else sliding = false

                Direction.DROITE -> if ((map[currentRow][currentCol + 1] > 0 || map[currentRow][currentCol + 1] == -3 || map[currentRow][currentCol + 1] == 20)) {
                    map[currentRow][currentCol] = case_atm
                    case_atm = map[currentRow][currentCol + 1]
                    map[currentRow][currentCol + 1] = 0
                    currentCol++
                    sliding = (case_atm == 20)
                } else if (currentCol + 1 < larg && map[currentRow][currentCol + 1] < -3) {
                    etat = -1
                    sliding = false
                } else sliding = false

                Direction.GAUCHE -> if ((map[currentRow][currentCol - 1] > 0 || map[currentRow][currentCol - 1] == -3 || map[currentRow][currentCol - 1] == 20)) {
                    map[currentRow][currentCol] = case_atm
                    case_atm = map[currentRow][currentCol - 1]
                    map[currentRow][currentCol - 1] = 0
                    currentCol--
                    sliding = (case_atm == 20)
                } else if (map[currentRow][currentCol - 1] < -3) {
                    etat = -1
                    sliding = false
                } else sliding = false
            }
        } while (sliding)


        when (case_atm) {
            2 -> {
                nb_key++
                println("Vous avez récupéré une clé !")
                println("Vous en avez maintenant $nb_key dans votre sac")
                case_atm = 1
            }
            5 -> etat = 1
            6 -> {
                val currentMap = Array(long) { i -> map[i].toIntArray() }
                val playerPosition = Pair(currentRow, currentCol)
                checkpointState = Triple(
                    currentMap,
                    playerPosition,
                    Triple(nb_key, 0, timer)
                )
                println("Checkpoint saved at position $playerPosition with $nb_key keys, turn $nb_tour, and time $timer seconds remaining.")
                case_atm = 1
            }
            8 -> {
                timer += 30
                println("⏳ Time+! 30 seconds added. Remaining time: $timer seconds.")
                case_atm = 1
            }
            9 -> {
                nb_freeze++
                println("❄️ Freeze bonus collected! You now have $nb_freeze freeze(s).")
                case_atm = 1
            }
            -3 -> {

                if ((nb_tour + 1) % 3 != 0) {
                    println("DEAD")
                    etat = -1
                }
            }
        }

        verifWinDead()
        moveEnemies()

        // Check if we just left a 3 spike on a turn it should become -3
        // If (nb_tour + 1) % 3 == 0 means the NEXT turn is divisible by 3, which is when spikes flip.
        // But we want to flip as soon as we leave it this turn if it's due to flip.
        // Let's say if nb_tour % 3 == 0 means this turn is the flipping turn.
        // Actually, we can check if THIS turn is a flipping turn:
        if (nb_tour % 3 == 0) {

            if (startRow != -1 && startCol != -1 && map[startRow][startCol] == 3 && case_atm != 3) {
                map[startRow][startCol] = -3
            }
        }


        nb_tour++
    }



    fun picMove() {
        // Check if nb_tour is divisible by 3 and not zero
        // Actually, we want to flip before the player moves on that turn, so do it based on (nb_tour+1)
        // Since we increment nb_tour at the end of moveJ, nb_tour now represents the turn after the player's move
        // We should flip spikes now if nb_tour is the turn about to happen
        if (nb_tour != 0 && nb_tour % 3 == 0) {
            for (i in 0 until long) {
                for (j in 0 until larg) {
                    if (abs(map[i][j]) == 3) {
                        map[i][j] = -map[i][j]
                        cptflip=-1
                    }
                }
            }

            val p: Pair<Int, Int> = getPos()
            if (p.first != -1 && p.second != -1) {
                if (map[p.first][p.second] == -3) {
                    println("💀 Vous êtes mort sur un pic devenu dangereux sous vos pieds !")
                    etat = -1
                    verifWinDead()
                }
            }
        }
    }



    fun verifWinDead() {
        when (etat) {
            1 -> {
                println("🎉 Félicitations, vous avez gagné ! 🎉")
                jouer = false
                timeThreadRunning.set(false)
            }
            -1 -> {
                println("💀 Vous êtes mort. Recommençons...")
                timeThreadRunning.set(false)
            }
        }
    }

    fun reset() {
        nb_mort++
        if (checkpointState != null) {
            println("Restoring from checkpoint...")
            val (savedMap, savedPosition, savedStats) = checkpointState!!

            for (i in savedMap.indices) {
                for (j in savedMap[i].indices) {
                    map[i][j] = savedMap[i][j]
                }
            }

            val (row, col) = savedPosition
            map[row][col] = 0

            nb_key = savedStats.first
            nb_tour = savedStats.second
            timer = savedStats.third

            println("Checkpoint restored:")
            println("Position: ($row, $col)")
            println("Keys: $nb_key")
            println("Turn: $nb_tour")
            println("Time: $timer seconds remaining.")
        } else {
            println("No checkpoint found. Restarting the level.")

            map.clear()
            for (row in initialMapCopy) {
                val mutableRow = row.toList().toMutableStateList()
                map.add(mutableRow)
            }
            nb_key = 0
            nb_tour = 1
            timer = 180
            case_atm = 1
        }

        etat = 0
        jouer = true
        deathCause = null
        timeThreadRunning.set(true)
        startTimer()
    }

    fun canOpenDoor(): Boolean {
        val p: Pair<Int, Int> = getPos()
        if (p.first + 1 < long && map[p.first + 1][p.second] == -2) {
            return true
        }
        if (p.first - 1 >= 0 && map[p.first - 1][p.second] == -2) {
            return true
        }
        if (p.second + 1 < larg && map[p.first][p.second + 1] == -2) {
            return true
        }
        if (p.second - 1 >= 0 && map[p.first][p.second - 1] == -2) {
            return true
        }
        return false
    }

    fun useKey() {
        if (nb_key > 0 && canOpenDoor()) {
            val p: Pair<Int, Int> = getPos()
            val doorPositions = mutableListOf<Pair<Int, Int>>()

            if (p.first + 1 < long && map[p.first + 1][p.second] == -2) {
                doorPositions.add(Pair(p.first + 1, p.second))
            }
            if (p.first - 1 >= 0 && map[p.first - 1][p.second] == -2) {
                doorPositions.add(Pair(p.first - 1, p.second))
            }
            if (p.second + 1 < larg && map[p.first][p.second + 1] == -2) {
                doorPositions.add(Pair(p.first, p.second + 1))
            }
            if (p.second - 1 >= 0 && map[p.first][p.second - 1] == -2) {
                doorPositions.add(Pair(p.first, p.second - 1))
            }

            for (door in doorPositions) {
                map[door.first][door.second] = 4
            }
            nb_key--
            println("Porte ouverte. Il vous reste $nb_key clé(s).")
        } else {
            println("Vous ne pouvez pas ouvrir la porte.")
        }
    }

    fun canUseTP(): Boolean {
        return case_atm >= 10 && case_atm <= 13
    }

    fun useTP(onTeleportationResult: (Boolean) -> Unit) {
        if (canUseTP()) {
            onTeleportationResult(true)
        } else {
            println("Vous n'êtes pas sur une case de téléportation.")
            onTeleportationResult(false)
        }
    }

    fun moveTP() {
        val playerPos = getPos()
        val currentTileValue = case_atm
        val numOut: Int = currentTileValue - (currentTileValue % 2) + ((currentTileValue + 1) % 2)

        var exitRow = -1
        var exitCol = -1

        outerLoop@ for (i in 0 until long) {
            for (j in 0 until larg) {
                if (map[i][j] == numOut) {
                    exitRow = i
                    exitCol = j
                    break@outerLoop
                }
            }
        }

        if (exitRow == -1 || exitCol == -1) {
            println("No matching teleportation exit found!")
            return
        }

        map[playerPos.first][playerPos.second] = case_atm
        case_atm = map[exitRow][exitCol]
        map[exitRow][exitCol] = 0

        println("You have been teleported to ($exitRow, $exitCol).")
    }

    fun useFreeze() {
        if (nb_freeze > 0) {
            nb_freeze--
            freezeEnemiesTurns = 3
            println("Vous avez utilisé un bonus Freeze. Les ennemis sont gelés pour $freezeEnemiesTurns tours.")
        } else {
            println("Vous n'avez pas de bonus Freeze.")
        }
    }

    fun moveEnemies() {
        if (freezeEnemiesTurns > 0) {
            println("❄️ Enemies are frozen for $freezeEnemiesTurns more turns!")
            freezeEnemiesTurns--
            return
        }
        if (nb_tour > 0) {
            val directions = listOf(
                Pair(-1, 0),
                Pair(1, 0),
                Pair(0, -1),
                Pair(0, 1)
            )

            val currentEnemyPositions = mutableListOf<Pair<Int, Int>>()
            val newEnemyPositions = mutableSetOf<Pair<Int, Int>>()

            for (i in 0 until long) {
                for (j in 0 until larg) {
                    if (map[i][j] == 7) {
                        currentEnemyPositions.add(Pair(i, j))
                    }
                }
            }

            val playerPos = getPos()

            for ((i, j) in currentEnemyPositions) {
                var moved = false
                for (dir in directions.shuffled()) {
                    val newI = i + dir.first
                    val newJ = j + dir.second

                    if (newI in 0 until long && newJ in 0 until larg &&

                        (map[newI][newJ] == 1 || map[newI][newJ] == 0 || map[newI][newJ] == 4) &&
                        Pair(newI, newJ) !in newEnemyPositions
                    ) {

                        if (playerPos.first == newI && playerPos.second == newJ) {
                            println("💀 Un ennemi vous a touché ! Vous êtes mort.")
                            etat = -1
                            deathCause = "enemy"
                            return
                        }


                        if (map[newI][newJ] == 4) {

                        }

                        map[i][j] = 1
                        map[newI][newJ] = 7
                        newEnemyPositions.add(Pair(newI, newJ))
                        moved = true
                        break
                    }
                }

                if (!moved) {
                    newEnemyPositions.add(Pair(i, j))
                }
            }

            println("Les ennemis se sont déplacés : $newEnemyPositions")
        }
    }





    fun startTimer() {
        timeThreadRunning.set(false)
        Thread.sleep(1000)

        val handler = Handler(Looper.getMainLooper())
        timeThreadRunning.set(true)
        thread(start = true) {
            while (timeThreadRunning.get()) {
                Thread.sleep(1000)
                handler.post {
                    if (!timeThreadRunning.get()) return@post
                    timer--
                    if (timer <= 0) {
                        println("⏰ Temps écoulé ! Vous avez perdu.")
                        jouer = false
                        timeThreadRunning.set(false)
                        showTimeUpDialog = true
                    }
                }
            }
        }
    }
}
