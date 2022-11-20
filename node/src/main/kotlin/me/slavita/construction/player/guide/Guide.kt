package me.slavita.construction.player.guide

class Guide {
    private var state: IGuideStep = InitialStep()

    fun changeState(state: IGuideStep) {
        this.state = state
        this.state.action()
    }
}