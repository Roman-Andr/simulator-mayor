package me.slavita.construction.city

enum class CitySamples(
    val id: Int,
    val title: String,
    val price: Long,
) {
    TOWN(
        1,
        "Микрорайон",
        100
    ),
    VILLAGE(
        2,
        "Деревня",
        200
    )
}
