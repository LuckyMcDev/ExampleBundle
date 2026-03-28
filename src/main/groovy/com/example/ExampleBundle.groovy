package com.example

import de.luckymcdev.foundryengine.common.script.BundleEntrypoint
import net.minecraft.resources.Identifier
import net.neoforged.bus.api.IEventBus


class ExampleBundle extends BundleEntrypoint {
    static final String BUNDLEID = "examplebundle"

    ExampleBundle(IEventBus bundleBus, IEventBus eventBus) {
        super(bundleBus, eventBus)
    }

    @Override
    void onLoad() {
        eventBus.register(ExampleGameEventHandler)
        bundleBus.register(ExampleBundleEventHandler)
    }

    @Override
    void onUnload() {
        eventBus.unregister(ExampleGameEventHandler)
        bundleBus.unregister(ExampleBundleEventHandler)
    }

    static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(BUNDLEID, path)
    }
}