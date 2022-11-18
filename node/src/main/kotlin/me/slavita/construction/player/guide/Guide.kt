package me.slavita.construction.player.guide

class Guide {
    private var state: IGuideStage = InitialStage()

    fun changeState(state: IGuideStage) {
        this.state = state
        this.state.action()
    }
}