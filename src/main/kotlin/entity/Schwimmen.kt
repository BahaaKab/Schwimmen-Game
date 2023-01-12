package entity

import service.RootService

/**
 * Entity class that represents a game state of "Schwimmen". It's a bunch of
 * @param [players] and
 * @param [playerPassCounter] which is used to track the pass action by the players.
 * @param [hasEnded] which determines whether the game is finished or not
 * @param [history] which gives the last moves of previous players
 * @param [endGamePlayer] the last player before the game ends
 * @param [activePlayer] shows the player who is playing right now
 * @param [openCards] which is an array that holds
 * the three cards that on the table.
 * @param [cards] which is an array that gives the total number of Cards
 */


class Schwimmen(
    var playerPassCounter: Int = 0,
    var hasEnded: Boolean = false,
    var history: List<Schwimmen> = listOf(),
    var rootService: RootService
) {
    var players : MutableList<Player> = arrayListOf()

    lateinit var endGamePlayer: Player

    lateinit var activePlayer: Player

    var openCards = ArrayList<Card>(3)
    var cards: MutableList<Card> = ArrayList(32)

    /**
     * Method to reset the [playerPassCounter]
     */
    fun resetPassCounter() {
        playerPassCounter = 0
    }

    /**
     * Method to increase the [playerPassCounter]
     */
    fun incrementPassCount(){
        playerPassCounter += 1
    }

    /**
     * Method to turn [hasEnded] to true when the game ends
     */
    fun endGame() {
        hasEnded = true
    }

    /**
     * to check if [endGamePlayer] is initialized
     */
    fun isEndGamePlayerInitialised() = ::endGamePlayer.isInitialized




}
