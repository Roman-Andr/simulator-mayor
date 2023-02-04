package me.slavita.construction.service.user

import com.mongodb.client.model.UpdateOptions
import me.func.serviceapi.mongo.MongoAdapter
import me.slavita.construction.common.utils.register
import me.slavita.construction.protocol.GetLeaderboardPackage
import me.slavita.construction.protocol.GetUserPackage
import me.slavita.construction.protocol.SaveUserPackage
import me.slavita.construction.protocol.UserSavedPackage
import org.bson.Document
import ru.cristalix.core.microservice.MicroServicePlatform
import ru.cristalix.core.microservice.MicroserviceBootstrap
import ru.cristalix.core.network.ISocketClient

fun main() {
    register(LoggerBot)

    MicroserviceBootstrap.bootstrap(MicroServicePlatform(2))
    val socketClient = ISocketClient.get()

    val db = MongoAdapter(
        "mongodb://${System.getenv("MONGO_LOGIN")}:${System.getenv("MONGO_PASSWORD")}@${System.getenv("MONGO_HOST")}",
        System.getenv("MONGO_DB"),
        System.getenv("MONGO_COLLECTION")
    )

    socketClient.run {
        capabilities(UserSavedPackage::class.java)

        listener<GetUserPackage> { realm ->
            db.collection.find(Document().append("_id", uuid)).forEach({
                data = it.getString("data")
            }) { _, _ ->
                log("got user")
                forward(realm, this)
            }
        }

        listener<GetLeaderboardPackage> { realm ->
            db.getTop(field) {
                top = this
                forward(realm, this@listener)
            }
        }

        listener<SaveUserPackage> { realm ->
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
                forward(realm, UserSavedPackage())
            }
        }
    }
}
