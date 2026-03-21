package com.example

import io.github.luckymcdev.foundryengine.api.builder.item.ItemBuilder
import io.github.luckymcdev.foundryengine.common.util.Freezable
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.registries.RegisterEvent

class ExampleBundleEventHandler {
    public static Item EXAMPLE_ITEM;

    @SubscribeEvent
    static void onRegisterEvent(RegisterEvent event) {
        event.register(Registries.ITEM, helper -> {
            def item = ItemBuilder.create(ExampleBundle.id("example_item"))
                    .properties( p -> {
                        p.fireResistant()
                        p.rarity(Rarity.EPIC)
                        p.repairable(Items.ACACIA_DOOR)
                        p.stacksTo(10)
                    })
            EXAMPLE_ITEM = item.get()
            item.register(helper)
        })
    }
}
