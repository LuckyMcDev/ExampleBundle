package com.example

import de.luckymcdev.foundryengine.api.builder.item.ItemBuilder
import de.luckymcdev.foundryengine.api.event.RegistryEvent
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.neoforged.bus.api.SubscribeEvent

class ExampleBundleEventHandler {
    private static ItemBuilder EXAMPLE_ITEM_BUILDER = ItemBuilder.create(ExampleBundle.id("example_item"))
            .properties( p -> {
                p.fireResistant()
                p.rarity(Rarity.EPIC)
                p.repairable(Items.ACACIA_DOOR)
                p.stacksTo(10)
            })

    @SubscribeEvent
    static void onRegisterEvent(RegistryEvent event) {
        event.items(EXAMPLE_ITEM_BUILDER)
    }
}
