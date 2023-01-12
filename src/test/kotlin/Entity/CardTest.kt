import entity.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test cases for [Card]
 */
class CardTest {
   //generate a card
   private val card = Card(CardValue.QUEEN, CardSuit.HEARTS)


   @Test
   fun testCard(){

      //Test: if the Suit of Card is correct
      assertTrue(card.suit == CardSuit.HEARTS)
      //Test: if the value of Card is not correct
      assertEquals(false,card.value == CardValue.KING)

   }
}