package service


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * A Class to test [PlayerActionService] and all its methods
 */
class TestPlayerActionService {
    private val testRootService = RootService()
    private val players = listOf("Baha", "Amin", "Marco")

    /**
     * to test [knock] method
     */
    @Test
    fun testKnock(){
        testRootService.schwimmenService.startSchwimmen(players)
        testRootService.currentGame?.playerPassCounter= 2
        testRootService.playerActionService.knock()
        val tmp = testRootService.currentGame
        assertEquals(0, tmp?.playerPassCounter)
        assertNotEquals(tmp?.activePlayer, tmp?.endGamePlayer)
    }

    /**
     * to test [changeOneCard] method
     */
    @Test
    fun testChangeOneCard(){
        testRootService.schwimmenService.startSchwimmen(players)
        testRootService.currentGame?.playerPassCounter= 1
        val tmp = testRootService.currentGame
        checkNotNull(tmp) {"no active game"}
        val oldHandCard = tmp.activePlayer.handCards[1]
        val oldTableCard = tmp.openCards[2]
        testRootService.playerActionService.changeOneCard(tmp.players[0].handCards[1] ,tmp.openCards[2])
        assertEquals(0, tmp.playerPassCounter)
        assertEquals(oldHandCard, tmp.openCards[2])
        assertEquals(oldTableCard, tmp.players[0].handCards[1])
        assertEquals(tmp.players[1], tmp.activePlayer)
    }

    /**
     * to test [changeAllCards] method
     */
    @Test
    fun testChangeAllCards(){
        testRootService.schwimmenService.startSchwimmen(players)
        testRootService.currentGame?.playerPassCounter= 1
        val tmp = testRootService.currentGame
        checkNotNull(tmp){"no active game"}
        val oldHandCards = tmp.activePlayer.handCards.toMutableList()
        val oldTableCards = tmp.openCards.toMutableList()
        testRootService.playerActionService.changeAllCards()
        assertEquals(0, tmp.playerPassCounter)
        assertEquals(oldHandCards, tmp.openCards)
        assertEquals(oldTableCards, tmp.players[0].handCards)
        assertEquals(tmp.players[1], tmp.activePlayer)
    }

    /**
     * to test [pass] method
     */
    @Test
    fun testPass(){
        testRootService.schwimmenService.startSchwimmen(players)
        testRootService.currentGame?.playerPassCounter= 0
        val tmp = testRootService.currentGame
        checkNotNull(tmp){"no active game"}
        val oldOpenCards = tmp.openCards.toMutableList()
        val firstThreeCards = tmp.cards.take(3)
        println(firstThreeCards)
        assertEquals(tmp.players[0], tmp.activePlayer)
        testRootService.playerActionService.pass()
        assertEquals(tmp.players[1], tmp.activePlayer)
        testRootService.playerActionService.pass()
        assertEquals(20, tmp.cards.size)
        assertEquals(tmp.players[2], tmp.activePlayer)
        assertEquals(oldOpenCards, tmp.openCards)
        testRootService.playerActionService.pass()
        assertEquals(17, tmp.cards.size)
        assertEquals(tmp.players[0], tmp.activePlayer)
        assertNotEquals(oldOpenCards, tmp.openCards)
        assertEquals(firstThreeCards, tmp.openCards)
    }
}