package com.example

import io.github.luckymcdev.foundryengine.common.registry.builder.ItemBuilder
import io.github.luckymcdev.foundryengine.common.util.Freezable
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.registries.RegisterEvent

class ExampleBundleEventHandler {
    public static Freezable<Item> EXAMPLE_ITEM

    @SubscribeEvent
    static void onRegisterEvent(RegisterEvent event) {
        event.register(Registries.ITEM, helper -> {
            def item = new ItemBuilder(Identifier.fromNamespaceAndPath(ExampleBundle.BUNDLEID, "example_item"))
                    .fireResistant()
                    .component(DataComponents.RARITY, Rarity.EPIC)

            EXAMPLE_ITEM.setValue(item.register(helper))
            EXAMPLE_ITEM.freeze()
        })
    }
}
