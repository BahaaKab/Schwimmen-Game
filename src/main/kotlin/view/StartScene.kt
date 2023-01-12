package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class StartScene(rootService: RootService): MenuScene(300, 500, background = ColorVisual(Color.WHITE)), Refreshable {

    private val headLineLabel = Label(
        width = 300, height = 50, posX = 0, posY = 50,
        text = "Schwimmen",
        font = Font(size = 30, fontWeight = Font.FontWeight.BOLD, color = Color(93,142,193))
    )

    private val p1 = Label(
        width = 100, height = 30,
        posX = -10, posY = 125,
        text = "Player 1:",
        font = Font(15)
    )
    private val p2 = Label(
        width = 100, height = 30,
        posX = -10, posY = 170,
        text = "Player 2:",
        font = Font(15)
    )
    private val p3 = Label(
        width = 100, height = 30,
        posX = -10, posY = 215,
        text = "Player 3:",
        font = Font(15)
    )
    private val p4 = Label(
        width = 100, height = 30,
        posX = -10, posY = 260,
        text = "Player 4:",
        font = Font(15)
    )
    private val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 125,
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = ((this.text.isBlank() || p2Input.text.isBlank()) &&
                    (p3Input.text.isNotBlank() || p4Input.text.isNotBlank()))
                    || this.text.isBlank()
                    || p2Input.text.isBlank()
        }
    }

    private val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 170,
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = ((this.text.isBlank() || p1Input.text.isBlank()) &&
                    (p3Input.text.isNotBlank() || p4Input.text.isNotBlank()))
                    || this.text.isBlank()
                    || p1Input.text.isBlank()
        }
    }

    private val p3Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 215,
        prompt = "Optional",
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = (this.text.isBlank() && p4Input.text.isNotBlank())
                    || p1Input.text.isBlank() || p2Input.text.isBlank()

        }
    }

    private val p4Input: TextField = TextField(
        width = 200, height = 35,
        posX = 80, posY = 260,
        prompt = "Optional"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank()
                    || p2Input.text.isBlank()
                    || (this.text.isNotBlank() && p3Input.text.isBlank())
        }
    }

    val exitButton: Button = Button(
        height = 35,
        width = 120,
        posX = 10,
        posY = 330,
        text = "Exit",
        font = Font(color = Color.BLACK, fontStyle = Font.FontStyle.ITALIC),
        visual = ColorVisual (221, 136,136)
    )

    private val startButton = Button(
        width = 120, height = 35,
        posX = 160, posY = 330,
        text = "Start"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        this.isDisabled = true
        this.onMouseClicked = {
            val inputNames:MutableList<String> = mutableListOf()
            inputNames.add(p1Input.text.trim())
            inputNames.add(p2Input.text.trim())
            if(p3Input.text.isNotBlank()){
                inputNames.add(p3Input.text.trim())
                if(p4Input.text.isNotBlank()){
                    inputNames.add(p4Input.text.trim())
                }
            }
            rootService.schwimmenService.startSchwimmen(inputNames)
        }
    }

    init {
        addComponents(
            headLineLabel,
            p1Input, p1, p2, p3, p4,
            p2Input,
            p3Input,
            p4Input,
            startButton,
            exitButton
        )
    }
}