package server.testbundle

import de.luckymcdev.foundryengine.core.common.Common
import de.luckymcdev.foundryengine.core.common.script.BundleEntrypoint

class ServerEntrypoint implements BundleEntrypoint {

	@Override
	void onLoad() {
		Common.LOGGER.error("Test Load Server")
	}

	@Override
	void onUnload() {
	}
}
