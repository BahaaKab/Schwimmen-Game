package service

import entity.Schwimmen
import view.Refreshable

class RootService: AbstractRefreshingService() {

    val schwimmenService = SchwimmenService(this)
    var playerActionService = PlayerActionService(this)
    var currentGame : Schwimmen? = null

    override fun addRefreshable(newRefreshable: Refreshable) {
        schwimmenService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided [newRefreshables] to all services
     * connected to this root service
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }


}