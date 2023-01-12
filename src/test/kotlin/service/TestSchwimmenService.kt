package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * A class to test the [SchwimmenService] from service layer and all the methods
 */
class TestSchwimmenService {
    private val testRootService = RootService()
    private val players = listOf("Baha", "Amin", "Marco")
//    private val playersNotEnough = listOf("Nana")
//    private val playersTooMany = listOf("Nana", "gaga", "mama", "haha", "jaja")

    /**
     * to test [StartSchwimmen] method
     */
    @Test
    fun testStartSchwimmen(){

        testRootService.schwimmenService.startSchwimmen(players)
        assertNotNull(testRootService.currentGame)
        assertEquals(20, testRootService.currentGame?.cards?.size)
        assertEquals(3, testRootService.currentGame?.players?.size)
        assertEquals(3, testRootService.currentGame?.players?.get(0)?.handCards?.size)
        assertEquals("Amin", testRootService.currentGame?.players?.get(1)?.name)
        assertEquals(3, testRootService.currentGame?.openCards?.size)
        assertEquals(testRootService.currentGame?.players?.get(0), testRootService.currentGame?.activePlayer)
    }

    /**
     * to test when quantity of players not between 2 and 4, should return [IllegalArgumentException]
     */
    /*@Test
    fun testSchwimmenNoEnoughPlayers(){
        testRootService.schwimmenService.startSchwimmen(playersNotEnough)
        testRootService.schwimmenService.startSchwimmen(playersTooMany)
    }*/

    /**
     * to test [swapOpenCards] method
     */
    @Test
    fun testSwapOpenCards(){
        testRootService.schwimmenService.startSchwimmen(players)
        val testCardsBefore = testRootService.currentGame?.cards
        val firstThreeCardsBeforeSwipe = testCardsBefore?.take(3)
        assertEquals(20, testCardsBefore?.size)
        testRootService.schwimmenService.swapOpenCards()
        val testCardsAfter = testRootService.currentGame?.cards
        val getOpenCards = testRootService.currentGame?.openCards
        assertEquals(17, testCardsAfter?.size)
        assertEquals(getOpenCards, firstThreeCardsBeforeSwipe)
    }

    /**
     * to test the [nextTurn] when the game ends
     */
    @Test
    fun testNextTurnWhenHasEnded(){
        testRootService.schwimmenService.startSchwimmen(players)
        testRootService.currentGame?.cards?.clear()
        testRootService.currentGame?.playerPassCounter = 3
        testRootService.schwimmenService.nextTurn()
        assertEquals(true, testRootService.currentGame?.hasEnded)
    }

    /**
     * to test [nextTurn] when the game is still active
     */
    @Test
    fun testNextTurnWhenHasNotEnded(){
        testRootService.schwimmenService.startSchwimmen(players)
        testRootService.schwimmenService.nextTurn()
        assertEquals(testRootService.currentGame?.players?.get(1), testRootService.currentGame?.activePlayer)
        //Test if the activePlayer is the last one in players list
        testRootService.currentGame?.activePlayer = testRootService.currentGame?.players?.last()!!
        testRootService.schwimmenService.nextTurn()
        assertEquals(testRootService.currentGame?.players?.get(0), testRootService.currentGame?.activePlayer)
    }

    /**
     * test [calculateWinner] Method to get the winner out of three players
     */
    @Test
    fun testCalculateWinner(){
        testRootService.schwimmenService.startSchwimmen(players)
        val tmp = testRootService.currentGame
        checkNotNull(tmp) {"no active game"}
        tmp.players[0].handCards[0] = Card(CardValue.TEN, CardSuit.CLUBS)
        tmp.players[0].handCards[1] = Card(CardValue.SEVEN, CardSuit.HEARTS)
        tmp.players[0].handCards[2] = Card(CardValue.NINE, CardSuit.SPADES)
        tmp.players[1].handCards[0] = Card(CardValue.NINE, CardSuit.HEARTS)
        tmp.players[1].handCards[1] = Card(CardValue.NINE, CardSuit.CLUBS)
        tmp.players[1].handCards[2] = Card(CardValue.NINE, CardSuit.SPADES)
        tmp.players[2].handCards[0] = Card(CardValue.SEVEN, CardSuit.CLUBS)
        tmp.players[2].handCards[1] = Card(CardValue.SEVEN, CardSuit.HEARTS)
        tmp.players[2].handCards[2] = Card(CardValue.SEVEN, CardSuit.SPADES)
        val tmp1 = testRootService.schwimmenService.calculateWinner()
        assertEquals( listOf(tmp.players[1], tmp.players[2]) , tmp1)
        assertTrue(tmp1.size == 1 || tmp1.size == 2 || tmp1.size == 3)
        tmp1.forEach{println(it.name)}
    }

    /**
     * to test the Method calculatePoints in EntitySchicht ( the method is used in calculateWinner)
     */
    @Test
    fun testCalculatePoints(){
        val card1 = Card(CardValue.TEN, CardSuit.CLUBS)
        val card2 = Card(CardValue.SEVEN, CardSuit.HEARTS)
        val card3 = Card(CardValue.NINE, CardSuit.SPADES)
        val card4 = Card(CardValue.QUEEN, CardSuit.CLUBS)
        val card5 = Card(CardValue.ACE, CardSuit.CLUBS)
        val card6 = Card(CardValue.NINE, CardSuit.CLUBS)
        val card7 = Card(CardValue.SEVEN, CardSuit.CLUBS)
        val card8 = Card(CardValue.SEVEN, CardSuit.HEARTS)
        val card9 = Card(CardValue.SEVEN, CardSuit.SPADES)

        val handTests1 = arrayListOf(card1, card2, card3)
        val handTests2 = arrayListOf(card4, card5, card6)
        val handTests3 = arrayListOf(card7, card8, card9)

        val player1 : Player = Player("dumb").apply{handCards= handTests1 }
        val player2 : Player = Player("dumb").apply{handCards= handTests2 }
        val player3 : Player = Player("dumb").apply{handCards= handTests3 }

        assertEquals(10.0, player1.calculatePoints())
        assertEquals(30.0, player2.calculatePoints())
        assertEquals(30.5, player3.calculatePoints())
    }
}