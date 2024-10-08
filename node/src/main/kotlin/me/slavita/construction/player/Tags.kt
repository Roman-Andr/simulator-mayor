package me.slavita.construction.player

import me.slavita.construction.utils.colored

enum class Tags(
    val tag: String,
    val price: Long,
    val donate: Boolean = false,
) {
    NONE("", 0),

    BUILDER(
        "Строитель".colored(
            """
        #fb0fed
        #f40dfb
        #de0bfc
        #c809fc
        #b208fd
        #9b06fd
        #8404fe
        #6c02fe
        #5400ff
            """.trimIndent().split("\n")
        ),
        1000
    ),
    MAYOR(
        "Мэр".colored(
            """
        #0ffb7e
        #08fdc0
        #00f6ff
            """.trimIndent().split("\n")
        ),
        2000
    ),
    BANKER(
        "Банкир".colored(
            """
        #fdff29
        #ffed21
        #ffd819
        #ffc210
        #ffaa08
        #ff9000
            """.trimIndent().split("\n")
        ),
        3000
    ),
    LAWYER(
        "Юрист".colored(
            """
        #29ff79
        #1fff32
        #44ff15
        #82ff0a
        #c6ff00
            """.trimIndent().split("\n")
        ),
        4000
    ),
    BUSINESSMAN(
        "Бизнесмен".colored(
            """
        #fafc1b
        #fcee18
        #fdde14
        #fdcd11
        #fdbc0e
        #feaa0a
        #fe9807
        #ff8503
        #ff7200
            """.trimIndent().split("\n")
        ),
        5000
    ),
    DEVELOPER(
        "Застройщик".colored(
            """
        #fc651b
        #fc7718
        #fd8a15
        #fd9e12
        #fdb20f
        #fec70c
        #fedc09
        #fef206
        #f4ff03
        #deff00
            """.trimIndent().split("\n")
        ),
        6000
    ),
    PIONEER(
        "Пионер".colored(
            """
        #4b6cb7
        #3f5ea3
        #34508d
        #2a4376
        #21355f
        #182848
            """.trimIndent().split("\n")
        ),
        6000
    ),
    COINER(
        "Монетчик".colored(
            """
        #ffc837
        #ffbf30
        #ffb62a
        #ffac23
        #ffa21c
        #ff9715
        #ff8c0f
        #ff8008
            """.trimIndent().split("\n")
        ),
        7000
    ),
    COLLECTOR(
        "Собиратель".colored(
            """
        #c21500
        #c92500
        #d03500
        #d64700
        #dd5900
        #e46d00
        #eb8100
        #f19700
        #f8ad00
        #ffc500
            """.trimIndent().split("\n")
        ),
        8000
    ),
    MASON(
        "Каменьшик".colored(
            """
        #292929
        #343434
        #3f3f3f
        #4a4a4a
        #555555
        #5f5f5f
        #6a6a6a
        #757575
        #808080
            """.trimIndent().split("\n")
        ),
        9000
    ),
    WEIGHT(
        "Авторитет".colored(
            """
        #7545ff
        #5e40fa
        #473bf5
        #363bf0
        #3248ea
        #2e55e3
        #2b62dc
        #2a6ed3
        #2e79c5
            """.trimIndent().split("\n")
        ),
        10000
    ),
    DODGER(
        "Хитрец".colored(
            """
        #ff456d
        #f73c8b
        #ee35a9
        #e42fc6
        #d22ad9
        #a02ec5
            """.trimIndent().split("\n")
        ),
        11000
    ),
    HACKER(
        "Хакер".colored(
            """
        #0a670e
        #0ba10d
        #10dd0a
        #34fa28
        #6dff5e
            """.trimIndent().split("\n")
        ),
        12000
    ),
    HUCKSTER(
        "Барыга".colored(
            """
        #0a670e
        #0b950e
        #0dc40b
        #12f609
        #40fb32
        #6dff5e
            """.trimIndent().split("\n")
        ),
        13000
    ),
    GRABBER(
        "Хапуга".colored(
            """
        #0a670e
        #0b950e
        #0dc40b
        #12f609
        #40fb32
        #6dff5e
            """.trimIndent().split("\n")
        ),
        14000
    ),
    TREE(
        "Дерево".colored(
            """
        #0a670e
        #0b950e
        #0dc40b
        #12f609
        #40fb32
        #6dff5e
            """.trimIndent().split("\n")
        ),
        15000
    ),
    BRICK(
        "Кирпич".colored(
            """
        #0a670e
        #0b950e
        #0dc40b
        #12f609
        #40fb32
        #6dff5e
            """.trimIndent().split("\n")
        ),
        16000,
        true
    ),
    TRADER(
        "Торговец".colored(
            """
        #1d976c
        #1fb47a
        #1fd285
        #2de48e
        #45eb96
        #5ef0a0
        #78f5ac
        #93f9b9
            """.trimIndent().split("\n")
        ),
        17000
    ),
    BUSY(
        "Занятой".colored(
            """
        #f2709c
        #f47090
        #f77084
        #f97177
        #fb7871
        #fd8671
        #ff9472
            """.trimIndent().split("\n")
        ),
        18000
    ),
}
// TODO: Хамелеон, Негодяй
