package server.testbundle

import de.luckymcdev.foundryengine.common.Common
import de.luckymcdev.foundryengine.common.script.BundleEntrypoint

class ServerEntrypoint implements BundleEntrypoint {

	@Override
	void onLoad() {
		Common.LOGGER.error("Test Load Server")
	}

	@Override
	void onUnload() {
	}
}
