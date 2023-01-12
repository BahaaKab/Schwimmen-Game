import entity.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Test cases for [Player]
 */
class PlayerTest {
    //generates a player
    private val player1= Player ("Amin")

    @Test
    fun testName() {

        val card1 = Card(CardValue.TEN, CardSuit.CLUBS)
        val card2 = Card(CardValue.SEVEN, CardSuit.HEARTS)
        val card3 = Card(CardValue.NINE, CardSuit.SPADES)

        val handTests = arrayListOf(card1, card2, card3)
        val player2 : Player = Player("dumb").apply{handCards= handTests }

        println(player1.name)

        /**
         * test player name
         */
        assertEquals(player1.name=="Amin", true)

        /**
         * test player2 hand and name
         */
        assertEquals(player2.handCards, handTests )
        assertEquals(player2.name, "dumb")

        /**
         * @throws IllegalArgumentException()
         */

        //assertEquals(IllegalArgumentException(), testPlayer.createPlayer(""))
    }


}