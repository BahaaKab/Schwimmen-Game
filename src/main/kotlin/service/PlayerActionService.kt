package service

import entity.Card

class PlayerActionService(private val rootService: RootService): AbstractRefreshingService() {

    fun knock(){
        val game = rootService.currentGame
        checkNotNull(game)
        game.resetPassCounter()
        rootService.schwimmenService.nextTurn()
        val tmp = game.players.indexOf(game.activePlayer)
        if (tmp == 0){
            game.endGamePlayer=game.players.last()
        }
        else {
            game.endGamePlayer = game.players[tmp - 1]
        }
        onAllRefreshables { refreshAfterNextTurn() }
    }

    fun changeOneCard(playerCard: Card, openCard: Card) {
        val game = rootService.currentGame
        checkNotNull(game)
        game.resetPassCounter()
        var indexOfChosenHandCard: Int? = null
        var indexOfChosenTableCard: Int? = null
        val player = game.activePlayer
        for ((i, card) in player.handCards.withIndex()) {
            if (playerCard == card) {
                indexOfChosenHandCard = i
                break
            }
        }
        checkNotNull(indexOfChosenHandCard){"Card is not in Hand"}
        for ((i, card) in game.openCards.withIndex()) {
            if (openCard == card) {
                indexOfChosenTableCard = i
                break
            }
        }
        checkNotNull(indexOfChosenTableCard){"Card is not on table"}

        player.handCards[indexOfChosenHandCard]=game.openCards[indexOfChosenTableCard]
            .also{ game.openCards[indexOfChosenTableCard]=player.handCards[indexOfChosenHandCard]}

        rootService.schwimmenService.nextTurn()

        onAllRefreshables { refreshAfterNextTurn() }
    }

    fun changeAllCards() {
        val game = rootService.currentGame
        checkNotNull(game)
        game.resetPassCounter()
        val player = game.activePlayer
        for ( i in 0..2 ){
            player.handCards[i]=game.openCards[i]
                .also{ game.openCards[i]=player.handCards[i]}
        }
        rootService.schwimmenService.nextTurn()

        onAllRefreshables { refreshAfterNextTurn() }
    }

    fun pass(){
        val game = rootService.currentGame
        checkNotNull(game)
        game.incrementPassCount()
        if(game.playerPassCounter == game.players.size){
            if(game.cards.size >= 3) {
                rootService.schwimmenService.swapOpenCards()
                game.resetPassCounter()
            }
        }
        rootService.schwimmenService.nextTurn()

        onAllRefreshables { refreshAfterNextTurn() }
    }
}