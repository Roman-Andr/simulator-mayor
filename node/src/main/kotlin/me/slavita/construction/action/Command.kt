package me.slavita.construction.action

interface Command {
    fun tryExecute(ignore: Boolean = false): Long
}
