package com.example.appmobileplatform

data class IntroPage(
    val description: String,
    val imageRes: Int
)


data class LevelIntro(
    val title: String,
    val pages: List<IntroPage>
)


val levelIntros = mapOf(
    1 to LevelIntro(
        title = "Welcome to the first level !",
        pages = listOf(
            IntroPage(
                description = "We know you will do great here but here is a small recap of what will happen:",
                imageRes = R.drawable.robot
            ),
            IntroPage(
                description = "You can move Up Down Left and Right, pretty easy right?",
                imageRes = R.drawable.robot
            ),
            IntroPage(
                description = "Be careful with the spikes and take a look at their behavior.\nIf your timing is wrong you might regret it..",
                imageRes = R.drawable.lvl1spike,

            ),
            IntroPage(
                description = "An enemy is here and doesnt really like when people are arround.. \nThankfully we managed to lock him up for you.",
                imageRes = R.drawable.lvl1enemy,

            ),
            IntroPage(
                description = "Here is a special watch that you will need to get out of here, so try to find your way out before the time runs out !",
                imageRes = R.drawable.robot,

            )

        )
    ),
    2 to LevelIntro(
        title = "Welcome to level 2!",
        pages = listOf(
            IntroPage(
                description = "Congrats on unlocking this level ! You were wondering why you had many buttons on the watch we gave you right? They will be useful here :",
                imageRes = R.drawable.robot
            ),
            IntroPage(
                description = "You can now teleport ! Try to find the magical rock and use your TP button !",
                imageRes = R.drawable.lvl2teleporter1
            ),
            IntroPage(
                description = "Enemies roam randomly arround the map, they are very unpredictable but thankfully we managed to find a way to stop them for 5 turns ! Use the freeze button on your watch for that but you might need some fuel somehow..",
                imageRes = R.drawable.lvl2freeze
            )
//            IntroPage(
//                description = "Because we wanted to be nice we included a way for you to  save your gamestate in case something bad happens to you, so good luck out there !",
//                imageRes = R.drawable.lvl2save
//            ),

        )
    ),
    3 to LevelIntro(
        title = "Welcome to level 3!",
        pages = listOf(
            IntroPage(
                description = "Enough agressivity, we decided to make the level a little bit more fun to watch..",
                imageRes = R.drawable.robot
            ),
            IntroPage(
                description = "Lets say we took you somewhere a little bit too cold and you might slide a bit because of the weather so be careful where you are going and good luck !",
                imageRes = R.drawable.robot
            )
        )
    )
)
