package me.slavita.construction.prepare

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.city.storage.StorageClientData
import me.slavita.construction.common.utils.STORAGE_INIT_CHANNEL
import me.slavita.construction.player.User
import me.slavita.construction.utils.toV3

object StoragePrepare : IPrepare {
    override fun prepare(user: User) {
        ModTransfer()
            .json(
                user.data.blocksStorage.boxes.map {
                    StorageClientData(
                        "Склад",
                        it.value.min.toV3(),
                        it.value.max.toV3()
                    )
                }.toTypedArray()
            )
            .send(STORAGE_INIT_CHANNEL, user.player)
    }
}
