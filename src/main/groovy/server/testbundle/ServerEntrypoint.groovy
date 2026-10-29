package server.testbundle

import de.luckymcdev.foundryengine.Common
import de.luckymcdev.foundryengine.script.common.BundleEntrypoint

class ServerEntrypoint implements BundleEntrypoint {

	@Override
	void onLoad() {
		Common.LOGGER.error("Test Load Server")
	}

	@Override
	void onUnload() {
	}
}
