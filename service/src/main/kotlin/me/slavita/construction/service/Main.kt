package me.slavita.construction.service

import com.mongodb.client.model.Projections.include
import com.mongodb.client.model.UpdateOptions
import me.func.serviceapi.mongo.MongoAdapter
import me.func.serviceapi.runListener
import me.slavita.construction.protocol.*
import org.bson.Document
import ru.cristalix.core.microservice.MicroServicePlatform
import ru.cristalix.core.microservice.MicroserviceBootstrap
import ru.cristalix.core.network.CorePackage
import ru.cristalix.core.network.ISocketClient
import ru.cristalix.core.realm.RealmId
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.reflect.KClass

fun main() {
    MicroserviceBootstrap.bootstrap(MicroServicePlatform(2))
    val socketClient = ISocketClient.get()

    val db = MongoAdapter(
        "mongodb://${System.getenv("MONGO_LOGIN")}:${System.getenv("MONGO_PASSWORD")}@${System.getenv("MONGO_HOST")}",
        System.getenv("MONGO_DB"),
        System.getenv("MONGO_COLLECTION")
    )

    fun getTop(field: String, after: ArrayList<LeaderboardItem>.() -> Unit) {
        val response = arrayListOf<LeaderboardItem>()
        db.collection
            .find()
            .projection(include(field))
            .sort(Document().append(field, -1))
            .limit(10)
            .forEach({
                response.add(LeaderboardItem(it.getString("_id"), it.getLong(field)))
            }) { _, _ ->
                after(response)
            }
    }

    socketClient.apply {
        listener(GetUserPackage::class) {realm ->
            db.collection.find(Document().append("_id", uuid)).forEach({
                data = it.getString("data")
            }) { _, _ ->
                println("got user")
                forward(realm, this)
            }
        }

        listener(GetLeaderboardPackage::class) { realm ->
            getTop(field) {
                top = this
                forward(realm, this@listener)
            }
        }

        listener(SaveUserPackage::class) { realm, ->
            db.collection.updateOne(
                Document().append("_id", uuid),
                Document().append(
                    "\$set",
                    Document()
                        .append("data", data)
                        .append("experience", experience)
                        .append("projects", projects)
                        .append("totalBoosters", totalBoosters)
                        .append("lastIncome", lastIncome)
                        .append("money", money)
                        .append("reputation", reputation)
                ),
                UpdateOptions().upsert(true)
            ) { _, _ ->
                println("user saved")
                forward(realm, SaveUserPackage(uuid))
            }
        }
    }
}
