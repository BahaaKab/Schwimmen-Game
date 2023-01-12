package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class EndScene(private val rootService: RootService): MenuScene(500, 600), Refreshable  {

    private val headLineLabel = Label(
        width = 300, height = 50, posX = 100, posY = 50,
        text = "Schwimmen",
        font = Font(size = 30, fontWeight = Font.FontWeight.BOLD, color = Color(93,142,193)),
        alignment = Alignment.CENTER
    ).apply { }

    private val winnerName = Label(
        posX = 0,
        posY = 160,
        width = 500,

        font = Font(size= 15, fontWeight = Font.FontWeight.SEMI_BOLD, fontStyle = Font.FontStyle.ITALIC),
        alignment = Alignment.CENTER,
        isWrapText = true
    )

     val quitButton = Button(
         height = 50,
        width = 200,
        posX = 30,
        posY = 330,
        text = "Exit",
        font = Font(30, color = Color.BLACK, fontStyle = Font.FontStyle.ITALIC),
        visual = ColorVisual (221, 136,136)
    )

     val newGameButton = Button(
        width = 200, height = 50,
        posX = 270, posY = 330,
        text = "new Game",
         font = Font(30, color = Color.BLACK, fontStyle = Font.FontStyle.ITALIC),
         visual = ColorVisual(136, 221, 136)
    )



    // holds the scores of the players.
    private val pScore = Label(width = 100, height = 150, posX = 300, posY = 150, font = Font(16), isWrapText = true)

    // holds the names of the players.
    private val pName = Label(width = 300, height = 150, posX = 7, posY = 150,font=Font(16))




    override fun refreshAfterEndGame() {
        val game = rootService.currentGame
        checkNotNull(game)
        val listOfWinners = rootService.schwimmenService.calculateWinner()
        val namesOfWinners = ArrayList<String>()
        for (player in listOfWinners)
        {
            namesOfWinners.add(player.name)
        }
        if(namesOfWinners.size==1) {
            winnerName.text = "${namesOfWinners[0]} wins! Congrats"
        }
        else {
            winnerName.text = "There is a draw between $namesOfWinners"
        }
        var playerNames = ""
        var playerScores = ""
        val sortedByScores= game.players.sortedByDescending { it.calculatePoints() }
        for(player in sortedByScores){
            playerNames += player.name + "\n"
            playerScores += player.calculatePoints().toString() + "\n"
        }

            pName.text = playerNames
            pScore.text = playerScores




    }
    init {
        addComponents(headLineLabel,winnerName,
        quitButton, newGameButton, pScore, pName)
    }
}