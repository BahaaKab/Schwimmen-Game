package entity

/**
* Entity to represent a player in the game "Schwimmen". The player has a
 * @param [name] attribute.
 * @param [handCards], which is an array that holds
* the three cards of the player.
*/
class Player( val name: String){

    var handCards: MutableList<Card> = ArrayList(3)

    /**
     * Method to throw Exception if there is no name
     */
//    fun createPlayer(name: String){
//        if (name == "") {
//        return
//        }
//    }

    /**
     * Method to calculate Points for every player. Can be used after the game is done to determine the winner.
     * The method separate the [handCards] due to their [CardSuit] and determine the score of each group, then
     * takes the max of them, it also compares the *enumValue* of the three Cards.
     * @return the calculation in [Double]
     */
    fun calculatePoints(): Double {

        var clubsPoints = 0.0
        var spadesPoints = 0.0
        var heartsPoints = 0.0
        var diamondsPoints = 0.0

        for (card in handCards) {
            when(card.suit){
                CardSuit.CLUBS -> clubsPoints += card.convertPoints()
                CardSuit.SPADES -> spadesPoints += card.convertPoints()
                CardSuit.HEARTS -> heartsPoints += card.convertPoints()
                CardSuit.DIAMONDS -> diamondsPoints += card.convertPoints()
            }
        }

        val points = maxOf(clubsPoints, heartsPoints, spadesPoints, diamondsPoints)

        return if (handCards[0].value == handCards[1].value &&
            (handCards[1].value == handCards[2].value)
        ) {
            30.5
        } else {
            points
        }
    }



}