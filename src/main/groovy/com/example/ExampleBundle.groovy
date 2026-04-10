package com.example

import de.luckymcdev.foundryengine.api.event.BundleEvents
import de.luckymcdev.foundryengine.api.event.ClientEvents
import de.luckymcdev.foundryengine.common.bundle.config.BundleConfig
import de.luckymcdev.foundryengine.common.bundle.config.BundleConfigSpec
import de.luckymcdev.foundryengine.common.script.BundleEntrypoint
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.neoforged.bus.api.IEventBus

class ExampleBundle extends BundleEntrypoint {

    ExampleBundle(IEventBus eventBus, BundleConfig bundleConfig) {
        super(eventBus, bundleConfig)
    }


    /**
     * Method called when the bundle is loaded.
     */
    @Override
    void onLoad() {
    }

    /**
     * Method called when the bundle is unloaded. You NEED to unregister any event handlers here.
     */
    @Override
    void onUnload() {
    }

}