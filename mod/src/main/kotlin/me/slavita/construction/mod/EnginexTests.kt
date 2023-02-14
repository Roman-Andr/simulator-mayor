// package me.slavita.construction.mod
//
// import me.slavita.construction.common.utils.IRegistrable
// import ru.cristalix.enginex.Enginex
// import ru.cristalix.enginex.element.ScrollSelector
// import ru.cristalix.enginex.renderer.OverlayRenderHandler
// import ru.cristalix.enginex.util.Color
// import ru.cristalix.enginex.util.Relative
// import ru.cristalix.enginex.util.V3
//
// object EnginexTests : IRegistrable {
//    override fun register() {
//        val scrollSelector = ScrollSelector(
//            "Январь", "Февраль", "Март",
//            "Апрель", "Май", "Июнь",
//            "Июль", "Август", "Сентябрь",
//            "Октябрь", "Ноябрь", "Декабрь"
//        )
//        scrollSelector.setOrigin(Relative.BOTTOM.V3())
//            .setAlign(Relative.CENTER.V3())
//            .setPos(V3(0.0, -50.0, 0.0)).color = Color.BLUE.clone()
//        OverlayRenderHandler.getOverlay().addChild(scrollSelector)
//    }
// }
