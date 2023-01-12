package Entity

import entity.*
import org.junit.jupiter.api.Test
import service.RootService
import kotlin.test.assertEquals


class SchwimmenTest {
    private val rootService = RootService()
    private val testSchwimmen = Schwimmen(1,false,arrayListOf(),rootService)
    private val player1 = Player("Wael")
    private val player2 = Player("Baha")
    private val player3=Player("Ahmed")



    @Test
    fun testPlayerCount(){
        assertEquals(testSchwimmen.playerPassCounter,1)
        testSchwimmen.resetPassCounter()
        assertEquals(testSchwimmen.playerPassCounter,0)
        testSchwimmen.incrementPassCount()
        testSchwimmen.incrementPassCount()
        assertEquals(2, testSchwimmen.playerPassCounter)

    }
    @Test
    fun testHasEnded(){
        assertEquals(testSchwimmen.hasEnded,false)
        testSchwimmen.endGame()
        assertEquals(testSchwimmen.hasEnded, true)
    }
    @Test
    fun testActivePlayer(){

        testSchwimmen.players.add(player1)
        testSchwimmen.players.add(player2)
        testSchwimmen.players.add(player3)

        testSchwimmen.activePlayer=player2

        testSchwimmen.endGamePlayer= player3

        assertEquals(player2, testSchwimmen.activePlayer)
        assertEquals(player3, testSchwimmen.endGamePlayer)
    }
}