package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

class SchwimmenApplication : BoardGameApplication("Schwimmen"), Refreshable {

    private val rootService: RootService = RootService()

    private val startScene = StartScene(rootService).apply {
        this.exitButton.onMouseClicked = {
        exit()
        }
    }

    private val gameScene = GameScene(rootService)

    private val endScene = EndScene(rootService).apply {
        this.quitButton.onMouseClicked = {
              exit()
        }
        this.newGameButton.onMouseClicked = {
            showMenuScene(startScene)
        }
    }

    private val switchScene = SwitchScene(rootService).apply {
        this.startTurnButton.onMouseClicked = {
            hideMenuScene()
        }
    }
   init {
       rootService.addRefreshables(this, startScene
           , gameScene, endScene, switchScene)

       this.showGameScene(gameScene)
       this.showMenuScene(startScene, 0)

       show()
    }

    override fun refreshAfterStartSchwimmen() {
        this.hideMenuScene()
    }

    override fun refreshAfterNextTurn() {
        if (rootService.schwimmenService.checkForEnd()){
            this.showMenuScene(endScene)
        }
        else{
            this.showMenuScene(switchScene)
        }
    }

    override fun refreshAfterEndGame() {
        this.showMenuScene(endScene)
    }

}


