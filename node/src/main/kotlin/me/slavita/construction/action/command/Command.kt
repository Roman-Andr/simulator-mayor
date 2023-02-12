package me.slavita.construction.action.command

interface Command {
    fun tryExecute(ignore: Boolean = false): Long
}
