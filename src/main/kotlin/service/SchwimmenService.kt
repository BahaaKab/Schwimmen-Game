package service

import entity.*

/**
 * Service Layer of the game [Schwimmen], It contains a bunch of Methods that
 * don't have direct impact on players actions but on game in general.
 * @property rootService connects the service layer with Entity layer
 */
class SchwimmenService(private val rootService: RootService): AbstractRefreshingService() {
    /**
     * Checks if the game is over, which has 2 options, and if any is met,
     * the game will end. The fist option contains of 2 conditions, first
     * condition is that number of cards left in stack is less than 3,
     * the second condition is that [playerPassCounter] should be equals to
     * size of players. Second option is that the [activePlayer] is the same
     * as [endGamePlayer].
     * @return Boolean
     * @throws IllegalStateException if the game is not active
     */
    fun checkForEnd(): Boolean {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }
        return if (game.isEndGamePlayerInitialised()) {
            (game.cards.size < 3 && game.playerPassCounter >= game.players.size) ||
                    (game.activePlayer == game.endGamePlayer)
        } else{
            (game.cards.size < 3 && game.playerPassCounter >= game.players.size)
        }
    }

    /**
     * creates a player using given name
     */
    private fun createPlayer(name: String) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game is currently running." }
        val player= Player(name)
        game.players.add(player)

    }


    /**
     * Starts the game by assigning [currentGame] to [Schwimmen] which makes it active.
     * The method also adds players to the list of [players] and generates 32 [cards]
     * and gives 3 cards to every Player and puts three cards on the table. At the end
     * it assigns [activePlayer] to the first player in the list
     *@throws IllegalArgumentException if there is no enough players
     *@throws IllegalArgumentException if there is no enough cards
     */
    fun startSchwimmen( players: List<String>) {

        rootService.currentGame = Schwimmen(rootService= rootService)
        val game = rootService.currentGame
        for ( player in players) {
            createPlayer(player)
        }
        require(game!!.players.size in 2..4){"No enough Players or too many players, required between 2 and 4"}
        game.cards = defaultRandomCardList().toMutableList()

        for(player in game.players){
            require ( 3 in 1..(game.cards.size)) { "can't draw 3 cards from $game.cards" }
            player.handCards = extractThreeCards().toMutableList()

        }
        require ( 3 in 1..(game.cards.size)) { "can't draw 3 cards from $game.cards" }
        game.openCards = extractThreeCards() as ArrayList<Card>
        game.activePlayer= game.players[0]
        onAllRefreshables { refreshAfterStartSchwimmen() }
    }
    /**
     * Checks if the game ended, if not then it assigns the turn to the next player in the list.
     * if the [activePlayer] is the last player in the list of [players], assigns it to the first player in list,
     * after the assignment it checks one more time if the new [activePlayer] has knocked before, if yes, it ends
     * the game.
     */
    fun nextTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game currently running."}
        game.hasEnded= checkForEnd()
        if (!game.hasEnded){
            if (game.activePlayer == game.players.last()){
                game.activePlayer= game.players[0]
            }
            else {
                val tmp= game.players.indexOf(game.activePlayer)
                game.activePlayer= game.players[tmp+1]
            }
            game.hasEnded = checkForEnd()
            if (game.hasEnded) {
                onAllRefreshables { refreshAfterEndGame() }
            }
        }
        else{
            onAllRefreshables { refreshAfterEndGame() }
        }

    }

    /**
     * it calculates the winner or the list of winners by using [calculatePoints] method from Entity
     * Layer for every player and getting the max of the points.
     */
    fun calculateWinner(): List<Player> {
        val game = rootService.currentGame
        checkNotNull(game) { "No game currently running."}
        val listOfPlayersPoints: MutableList<Double> = ArrayList()
        for (player in game.players) {
            listOfPlayersPoints.add(player.calculatePoints())
        }
        val maxPoints = listOfPlayersPoints.maxOrNull()
        val indexOfWinningPlayers = listOfPlayersPoints.mapIndexed{
                index: Int, elem: Double -> if (elem == maxPoints) game.players[index] else null }.filterNotNull()

        return indexOfWinningPlayers
    }

    /**
     * generates 32 Cards with values from 7 to [ACE] and the four Symbols(Suit) and shuffle
     * the cards at the end
     */
    private fun defaultRandomCardList() = List(32) { index ->
        Card(
            CardValue.values()[(index % 8) + 5],
            CardSuit.values()[index / 8],
        )
    }.shuffled()

    /**
     * it extracts three cards from the deck [cards]
     */
    private fun extractThreeCards(): List<Card> {
        val game = rootService.currentGame
        require ( 3 in 1..(game!!.cards.size)) { "can't draw 3 cards from $game.cards" }
        return List(3) { game.cards.removeFirst() }
    }

    /**
     * throws all [openCards] and replace them with the first three cards from the deck
     */
    fun swapOpenCards() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game currently running."}
        for (card in game.openCards.indices){
            game.openCards[card] = game.cards.first()
            game.cards.removeFirst()
        }
    }
}