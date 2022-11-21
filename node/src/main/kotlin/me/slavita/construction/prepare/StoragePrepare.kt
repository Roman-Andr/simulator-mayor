package me.slavita.construction.prepare

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.player.User
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.storage.StorageClientData
import me.slavita.construction.utils.extensions.BlocksExtensions.toV3

object StoragePrepare : IPrepare {
    override fun prepare(user: User) {
        ModTransfer()
            .json(user.blocksStorage.boxes.map { StorageClientData(it.value.min.toV3(), it.value.max.toV3()) }.toTypedArray())
            .send("storage:initialize", user.player)
    }
}