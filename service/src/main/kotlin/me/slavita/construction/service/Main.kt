package me.slavita.construction.service

import com.mongodb.client.model.*
import com.mongodb.client.model.Projections.include
import me.func.serviceapi.mongo.MongoAdapter
import me.func.serviceapi.runListener
import me.slavita.construction.protocol.*
import org.bson.Document
import ru.cristalix.core.microservice.MicroServicePlatform
import ru.cristalix.core.microservice.MicroserviceBootstrap
import ru.cristalix.core.network.ISocketClient
import kotlin.collections.ArrayList

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
            .sort(Document().append(field, 1))
            .limit(10)
            .forEach({
                response.add(LeaderboardItem(it.getString("_id"), it.getLong(field)))
            }) { _, _ ->
                after(response)
            }
    }

    socketClient.apply {
        capabilities(
            GetUserPackage::class,
            SaveUserPackage::class,
            GetLeaderboardPackage::class,
        )

        runListener<GetUserPackage> { realm, pckg ->
            println("GetUserPackage: ${realm.realmName}")
            pckg.run {
                db.collection.find(Document().append("_id", uuid)).forEach ({
                    data = it.getString("data")
                }) { _, _ ->
                    println("got user")
                    forward(realm, this)
                }
            }
        }

        runListener<GetLeaderboardPackage> { realm, pckg ->
            println("GetLeaderboardPackage: ${realm.realmName}")
            pckg.run {
                var responses = 0
                getTop("experience") {
                    experience = this
                    if (++responses == 2) forward(realm, pckg)
                }
                getTop("projects") {
                    projects = this
                    if (++responses == 2) forward(realm, pckg)
                }
            }
        }

        runListener<SaveUserPackage> { realm, pckg ->
            println("SaveUserPackage: ${realm.realmName}")
            pckg.run {
                db.collection.updateOne(
                    Document().append("_id", uuid),
                    Document().append("\$set",
                        Document()
                            .append("data", data)
                            .append("experience", experience)
                            .append("projects", projects)
                    ),
                    UpdateOptions().upsert(true)
                ) { _, _ ->
                    println("user saved")
                    forward(realm, SaveUserPackage(uuid))
                }
            }
        }
    }
}
