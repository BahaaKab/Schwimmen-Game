package entity

/**
 * class for the single typ of game elements that the game "Schwimmen" knows: cards.
 *
 * @param value from type [CardValue] to give value of Card
 * @param suit from type [CardSuit] to give value of Card
 *
 */
    class Card ( val value: CardValue, val suit: CardSuit) {


    /**
     * Method to convert Enum values to Double
     */
    fun convertPoints(): Double {
       return when (this.value) {
           CardValue.ACE -> 11.0

           CardValue.KING -> 10.0

           CardValue.QUEEN -> 10.0

           CardValue.JACK -> 10.0

           CardValue.TEN -> 10.0

           CardValue.NINE -> 9.0

           CardValue.EIGHT -> 8.0

           CardValue.SEVEN -> 7.0

           CardValue.SIX -> 6.0

           CardValue.FIVE -> 5.0

           CardValue.FOUR -> 4.0

           CardValue.THREE -> 3.0

           CardValue.TWO -> 2.0
        }


    }
}