package me.slavita.construction.utils.extensions

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

object KotlinExtensions {
    inline fun <reified T, Y> Collection<T>.listOfField(property: KProperty1<T, Y?>):List<Y> {
        val response = arrayListOf<Y>()
        this.forEach { p: T ->
            response.add(property.get(p) as Y)
        }
        return response
    }
}