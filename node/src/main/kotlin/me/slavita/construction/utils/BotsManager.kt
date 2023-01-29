package me.slavita.construction.utils

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.petersamokhin.vksdk.core.client.VkApiClient
import com.petersamokhin.vksdk.core.model.VkSettings
import com.petersamokhin.vksdk.http.VkOkHttpClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.slavita.construction.prepare.IRegistrable
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

@OptIn(ExperimentalCoroutinesApi::class)
object BotsManager : IRegistrable {

    val tg = bot {
        token = System.getenv("TG_TOKEN")
        dispatch {}
    }

    val ds = JDABuilder
        .createDefault(System.getenv("DISCORD_BOT_TOKEN"))
        .setActivity(Activity.playing("Cristalix"))
        .build()

    val vk = VkApiClient(
        218445690,
        System.getenv("VK_BOT_TOKEN"),
        VkApiClient.Type.Community,
        VkSettings(VkOkHttpClient())
    )

    override fun register() {
        tg.startPolling()
        runBlocking {
            delay(1000)
            logVk("Initialized")
            logDs("Initialized")
            logTg("Initialized")
        }
    }
}
