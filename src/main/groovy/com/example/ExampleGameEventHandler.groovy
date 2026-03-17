package com.example

import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent

class ExampleGameEventHandler {

    @SubscribeEvent
    static void onServerTickEvent(ServerTickEvent.Post event) {
        if (event.getServer().tickCount % 20 == 0) {
            event.getServer().getPlayerList().getPlayers().forEach { player ->
                player.sendSystemMessage(Component.literal("test"))
            }
        }
    }
}
