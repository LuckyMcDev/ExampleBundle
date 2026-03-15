package com.example

import io.github.luckymcdev.foundryengine.common.script.BundleEntrypoint
import net.neoforged.bus.api.IEventBus


class ExampleBundle extends BundleEntrypoint {
    static final String BUNDLEID = "examplebundle"

    ExampleBundle(IEventBus bundleBus, IEventBus eventBus) {
        super(bundleBus, eventBus)
    }

    @Override
    void onLoad() {
        //eventBus.register(ExampleGameEventHandler)
        bundleBus.register(ExampleBundleEventHandler)
    }

    @Override
    void onUnload() {
        //eventBus.unregister(ExampleGameEventHandler)
        bundleBus.unregister(ExampleBundleEventHandler)
    }
}