package me.slavita.construction.worker

enum class WorkerState(
    val title: String,
) {
    FREE("Выбрать"),
    SELECTED("Выбран"),
    BUSY("Занят");
}
