package me.slavita.construction.common.utils

fun register(vararg registers: IRegistrable) = registers.forEach { it.register() }