package me.slavita.construction.listener

import me.slavita.construction.prepare.IRegistrable

object PlayerEvents : IRegistrable {
    override fun register() {
        PhysicsDisabler
        OnJoin
        OnLeave
        OnChat
        OnActions
        OnUserLoad
    }
}
