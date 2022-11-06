package me.slavita.construction.structure.tools

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveProgress
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.element.Banner
import me.func.protocol.data.element.MotionType
import me.func.protocol.math.Position
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.banner.BannerInfo
import me.slavita.construction.banner.BannerUtil
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.ui.Animations
import me.slavita.construction.utils.extensions.BannersExtensions.hide
import me.slavita.construction.utils.extensions.BannersExtensions.show
import me.slavita.construction.utils.extensions.BlocksExtensions.unaryMinus
import me.slavita.construction.utils.extensions.BlocksExtensions.withOffset
import org.bukkit.ChatColor
import org.bukkit.block.BlockFace

class StructureVisual(val structure: BuildingStructure) {

	private var floorBanner: Banner? = null
	private var infoBanners: Pair<Banner, Banner>? = null
	private var progressWorld: ReactiveProgress? = null
	private var marker: Marker? = null
	private val owner = structure.owner
	private val progressBar = StructureProgressBar(owner.player, structure.structure.blocksCount)

	fun start() {
		val center = structure.box.center
		floorBanner = BannerUtil.createFloorBanner(
			center.clone().apply {
				y = structure.allocation.y - 22.49
				z = structure.allocation.z
			}, GlowColor.BLUE
		)

		infoBanners = BannerUtil.createDual(
			BannerInfo(
				structure.box.bottomInfo.withOffset(-structure.box.min).withOffset(structure.allocation),
				BlockFace.NORTH,
				structure.getBannerInfo(),
				102,
				80,
				Tricolor(0, 0, 0),
				0.65
			)
		)

		progressWorld = ReactiveProgress.builder()
			.position(Position.BOTTOM)
			.offsetX(structure.box.bottomInfo.withOffset(-structure.box.min).withOffset(structure.allocation).x)
			.offsetY(structure.allocation.y + 6.0)
			.offsetZ(structure.box.bottomInfo.withOffset(-structure.box.min).withOffset(structure.allocation).z)
			.hideOnTab(false)
			.color(GlowColor.GREEN)
			.scale(2.5)
			.build()

		marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
		Banners.show(owner.player, infoBanners!!)

		update()
		hide()
	}

	fun update() {
		progressBar.update(structure.blocksPlaced)
		progressWorld!!.apply {
			progress = structure.blocksPlaced.toDouble() / structure.structure.blocksCount.toDouble()
			text =
				"${ChatColor.WHITE}Поставлено блоков: ${ChatColor.WHITE}${structure.blocksPlaced} ${ChatColor.WHITE}из ${ChatColor.AQUA}${structure.structure.blocksCount}"
		}
		infoBanners!!.toList().forEach { it ->
			Banners.content(owner.player, it, structure.getBannerInfo().joinToString("\n") { it.first })
		}
	}

	fun show() {
		Banners.hide(owner.player, floorBanner!!)
		Anime.removeMarker(owner.player, marker!!)
		progressWorld!!.send(owner.player)
		progressBar.show()
		update()
	}

	fun hide() {
		Banners.show(owner.player, floorBanner!!)
		Anime.marker(owner.player, marker!!)
		progressWorld!!.delete(setOf(owner.player))
		progressBar.hide()
	}

	fun delete() {
		Banners.hide(owner.player, floorBanner!!)
		Banners.hide(owner.player, infoBanners!!)
		Anime.removeMarker(owner.player, marker!!)
		progressWorld!!.delete(setOf(owner.player))
		progressBar.hide()
	}

	fun finishShow() {
		Banners.show(owner.player, floorBanner!!.apply { color = GlowColor.GREEN })
	}

	fun hideFinish() {
		Banners.hide(owner.player, floorBanner!!)
	}
}