package com.example

import io.github.luckymcdev.foundryengine.common.script.BundleEntrypoint
import net.neoforged.bus.api.IEventBus


class ExampleBundle extends BundleEntrypoint {

    protected ExampleBundle(IEventBus bundleBus, IEventBus eventBus) {
        super(bundleBus, eventBus)
    }

    @Override
    void onLoad() {

    }

    @Override
    void onUnload() {

    }
}