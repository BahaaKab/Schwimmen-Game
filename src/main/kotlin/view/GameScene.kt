package view

import entity.Card
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

class GameScene(private val rootService: RootService) :
    BoardGameScene(background = ImageVisual("card game background.png")), Refreshable {

    private var handCardChosen: Card? = null
    private var tableCardChosen: Card? = null

    private val passButton = Button(
        width = 310, height = 50,
        posX = 100, posY = 860,
        text = "Pass",
        font = Font(30)

    ).apply {
        onMouseEntered = {
            visual = ColorVisual(255, 255, 0)
        }
        onMouseExited = {
            visual = ColorVisual(255, 255, 255)
        }
        onMouseClicked = {
            rootService.playerActionService.pass()
        }
    }

    private val changeAllCardsButton = Button(
        width = 310, height = 50,
        posX = 100, posY = 920,
        text = "Change all cards",
        font = Font(30)
    ).apply {
        onMouseEntered = {
            visual = ColorVisual(255, 255, 0)
        }
        onMouseExited = {
            visual = ColorVisual(255, 255, 255)
        }
        onMouseClicked = {
            rootService.playerActionService.changeAllCards()
        }
    }

    private val knockButton = Button(
        width = 310, height = 50,
        posX = 1500, posY = 920,
        text = "Knock",
        font = Font(30)
    ).apply {
        onMouseEntered = {
            visual = ColorVisual(255, 255, 0)
        }
        onMouseExited = {
            visual = ColorVisual(255, 255, 255)
        }
        onMouseClicked = {
            this.isVisible = false
            rootService.playerActionService.knock()

        }
    }

    private val changeCardButton = Button(
        width = 310, height = 50,
        posX = 100, posY = 800,
        text = "Change one card",
        font = Font(30)
    )
        .apply {
            onMouseEntered = {
                visual = ColorVisual(255, 255, 0)
            }
            onMouseExited = {
                visual = ColorVisual(255, 255, 255)
            }
            onMouseClicked = {
                if (handCardChosen != null && tableCardChosen != null){
                    rootService.playerActionService.changeOneCard(handCardChosen!!, tableCardChosen!!)
                resetHandCardOpacity()
                resetTableCardOpacity()
                }
            }
        }

    private val handCard1 = CardView(
        posX = 730, posY = 800, height = 200, width = 130,
        front = ImageVisual(CardImageLoader().blankImage),
        back = ImageVisual(CardImageLoader().backImage)
    ).apply {
        onMouseClicked = {
            resetHandCardOpacity()
            opacity = 0.5
            handCardChosen = rootService.currentGame!!.activePlayer.handCards[0]
        }
    }

    // Second card of the current player.
    private val handCard2 = CardView(
        posX = 895, posY = 800, height = 200, width = 130,
        front = ImageVisual(CardImageLoader().blankImage),
        back = ImageVisual(CardImageLoader().backImage)
    ).apply {
        onMouseClicked = {
            resetHandCardOpacity()
            opacity = 0.5
            handCardChosen = rootService.currentGame!!.activePlayer.handCards[1]
        }
    }

    // Third card of the current player.
    private val handCard3 = CardView(
        posX = 1060, posY = 800, height = 200, width = 130,
        front = ImageVisual(CardImageLoader().blankImage),
        back = ImageVisual(CardImageLoader().backImage)
    ).apply {
        onMouseClicked = {
            resetHandCardOpacity()
            opacity = 0.5
            handCardChosen = rootService.currentGame!!.activePlayer.handCards[2]
        }
    }

    // First table card.
    private val openCard1 = CardView(
        posX = 700, posY = 450, height = 200, width = 130,
        front = ImageVisual(CardImageLoader().blankImage),
        back = ImageVisual(CardImageLoader().backImage)
    ).apply {
        onMouseClicked = {
            resetTableCardOpacity()
            opacity = 0.5
            tableCardChosen = rootService.currentGame!!.openCards[0]
        }
    }

    // Second table card.
    private val openCard2 = CardView(
        posX = 895, posY = 450, height = 200, width = 130,
        front = ImageVisual(CardImageLoader().blankImage),
        back = ImageVisual(CardImageLoader().backImage)
    ).apply {
        onMouseClicked = {
            resetTableCardOpacity()
            opacity = 0.5
            tableCardChosen = rootService.currentGame!!.openCards[1]
        }
    }


    // Third table card.
    private val openCard3 = CardView(
        posX = 1080, posY = 450, height = 200, width = 130,
        front = ImageVisual(CardImageLoader().blankImage),
        back = ImageVisual(CardImageLoader().backImage)
    ).apply {
        onMouseClicked = {
            resetTableCardOpacity()
            opacity = 0.5
            tableCardChosen = rootService.currentGame!!.openCards[2]
        }

    }

    private val deckCard = CardView(
        posX = 1580, posY = 450, height = 200, width = 130,
        front = ImageVisual(CardImageLoader().blankImage),
        back = ImageVisual(CardImageLoader().backImage)
    )

    private val playerTurn = Label(
        width = 350, height = 200, posX = 1470,
        posY = 160, font = Font(
            30, color = Color.blue,
            fontStyle = Font.FontStyle.OBLIQUE
        ), text = "Player's turn:"
    )

    // holds the current player's name.
    private val playerName = Label(
        width = 400, height = 200, posX = 1440, posY = 195,
        font = Font(30, color = Color.DARK_GRAY)
    )

    // represents the "Score:" text during the game.
    private val score = Label(
        width = 350, height = 200, posX = 1470, posY = 235,
        font = Font(30, color = Color.blue, fontStyle = Font.FontStyle.OBLIQUE), text = "Hand's score:"
    )

    // holds the current player's score.
    private val playerScore = Label(
        width = 350, height = 200, posX = 1470, posY = 270,
        font = Font(30, color = Color.DARK_GRAY)
    )

    private val drawStackSize = Label(
        width = 200, height = 200, posX = 1550, posY = 580,
        font = Font(24, color = Color.black)
    )

    private val ifKnocked = Label(
        width = 700, height = 200, posX = 160, posY = 370,
        alignment = Alignment.TOP_LEFT,

        font = Font(
            40, color = Color.CYAN, fontWeight = Font.FontWeight.BOLD,
            fontStyle = Font.FontStyle.OBLIQUE
        )
    )

    private val textAfterKnock = Label(
        width = 700, height = 200, posX = 50, posY = 450,
        alignment = Alignment.CENTER_LEFT,
        font = Font(
            30, color = Color.CYAN, fontWeight = Font.FontWeight.BOLD,
            fontStyle = Font.FontStyle.OBLIQUE
        )
    )

    private fun resetTableCardOpacity(){
        openCard1.opacity= 1.0
        openCard2.opacity = 1.0
        openCard3.opacity = 1.0
    }

    private fun resetHandCardOpacity(){
        handCard1.opacity = 1.0
        handCard2.opacity = 1.0
        handCard3.opacity = 1.0
    }

    override fun refreshAfterStartSchwimmen() {
        val game = rootService.currentGame
        checkNotNull(game)
        val cardImageLoader = CardImageLoader()
        knockButton.isVisible = true
        ifKnocked.text = ""
        textAfterKnock.text = ""
        //Initialize open cards
        initializeCard(game.openCards[0], openCard1, cardImageLoader)
        initializeCard(game.openCards[1], openCard2, cardImageLoader)
        initializeCard(game.openCards[2], openCard3, cardImageLoader)
        //Initialize first player hand

        initializeCard(game.players[0].handCards[0], handCard1, cardImageLoader)
        initializeCard(game.players[0].handCards[1], handCard2, cardImageLoader)
        initializeCard(game.players[0].handCards[2], handCard3, cardImageLoader)
        listOf(openCard1, openCard2, openCard3, handCard1, handCard2, handCard3).forEach { it.showFront() }
        drawStackSize.text = "${game.cards.size}/32 Cards"
        playerName.text = game.players[0].name
        playerScore.text = "${game.players[0].calculatePoints()}"


    }

    override fun refreshAfterNextTurn() {
        val game = rootService.currentGame
        checkNotNull(game)
        val cardImageLoader = CardImageLoader()

        //Initialize open cards
        initializeCard(game.openCards[0], openCard1, cardImageLoader)
        initializeCard(game.openCards[1], openCard2, cardImageLoader)
        initializeCard(game.openCards[2], openCard3, cardImageLoader)
        //Initialize active player hand

        initializeCard(game.activePlayer.handCards[0], handCard1, cardImageLoader)
        initializeCard(game.activePlayer.handCards[1], handCard2, cardImageLoader)
        initializeCard(game.activePlayer.handCards[2], handCard3, cardImageLoader)
        listOf(openCard1, openCard2, openCard3, handCard1, handCard2, handCard3).forEach { it.showFront() }
        drawStackSize.text = "${game.cards.size}/32 Cards"
        playerName.text = game.activePlayer.name
        playerScore.text = "${game.activePlayer.calculatePoints()}"
        if (!knockButton.isVisible) {
            ifKnocked.text = "${game.endGamePlayer.name} has\n knocked!"
            textAfterKnock.text = "last chance to raise your score!"
        }

    }

    private fun initializeCard(card: Card, cardView: CardView, cardImageLoader: CardImageLoader) {
        cardView.frontVisual = ImageVisual(
            cardImageLoader.frontImageFor(card.suit, card.value)
        )
    }

    init {





        addComponents(
            passButton,
            changeAllCardsButton,
            knockButton,
            changeCardButton,
            handCard1, handCard2, handCard3,
            playerTurn, playerName, score, playerScore, drawStackSize, deckCard,
            ifKnocked, textAfterKnock,
            openCard1, openCard2, openCard3,
        )
    }
}