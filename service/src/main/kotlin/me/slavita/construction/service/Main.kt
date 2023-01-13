package me.slavita.construction.service

import com.google.gson.Gson
import com.mongodb.Block
import com.mongodb.async.SingleResultCallback
import com.mongodb.client.model.*
import com.mongodb.client.result.UpdateResult
import me.func.serviceapi.mongo.MongoAdapter
import me.func.serviceapi.runListener
import me.slavita.construction.protocol.GetUserPackage
import me.slavita.construction.protocol.SaveUserPackage
import org.bson.BSON
import org.bson.BasicBSONObject
import org.bson.BsonDocument
import org.bson.BsonValue
import org.bson.Document
import org.bson.conversions.Bson
import ru.cristalix.core.GlobalSerializers
import ru.cristalix.core.microservice.MicroServicePlatform
import ru.cristalix.core.microservice.MicroserviceBootstrap
import ru.cristalix.core.network.ISocketClient
import java.util.ArrayList

fun main() {
    MicroserviceBootstrap.bootstrap(MicroServicePlatform(2))
    val socketClient = ISocketClient.get()

    val db = MongoAdapter(
        "mongodb://${System.getenv("MONGO_LOGIN")}:${System.getenv("MONGO_PASSWORD")}@${System.getenv("MONGO_HOST")}",
        System.getenv("MONGO_DB"),
        System.getenv("MONGO_COLLECTION")
    )

    socketClient.apply {
        capabilities(GetUserPackage::class)
        capabilities(SaveUserPackage::class)

        runListener<GetUserPackage> { realm, pckg ->
            println("GetUserPackage: ${realm.realmName}")
            pckg.run {
                db.collection.find(Document().append("_id", uuid)).forEach ({
                    data = it.getString("data")
                    forward(realm, this)
                }) { _, _ -> }
            }
        }

        runListener<SaveUserPackage> { realm, pckg ->
            println("SaveUserPackage: ${realm.realmName}")
            pckg.run {
                db.collection.updateOne(
                    Document().append("_id", uuid),
                    Document().append("\$set", Document().append("data", data)),
                    UpdateOptions().upsert(true)
                ) { _, _ -> }
            }
        }
    }
}
