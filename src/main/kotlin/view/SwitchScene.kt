package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class SwitchScene(private val rootService: RootService): MenuScene(500, 600), Refreshable {


    val startTurnButton = Button(
        width = 200, height = 50,
        posX = 150, posY = 260, text = "Start Turn",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 20, fontStyle = Font.FontStyle.OBLIQUE),
        visual = ColorVisual(0, 0, 0)
    ).apply {
        onMouseEntered = {
            visual = ColorVisual(128, 0, 128)
        }
        onMouseExited = {
            visual = ColorVisual(0, 0, 0)
        }
    }

    override fun refreshAfterNextTurn() {
        outputLabel.text= "its your turn ${rootService.currentGame?.activePlayer?.name}!"
    }



    private val outputLabel = Label(
        posX = 0,
        posY = 200,
        width = 500,
        font = Font(size= 30, fontWeight = Font.FontWeight.BOLD, fontStyle = Font.FontStyle.ITALIC),
        alignment = Alignment.CENTER,
    )




     init {
         addComponents(
             startTurnButton, outputLabel
         )
     }
}