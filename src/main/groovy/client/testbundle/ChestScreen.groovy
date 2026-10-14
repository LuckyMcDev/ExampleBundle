package client.testbundle

import de.luckymcdev.foundryengine.client.ui.screen.EngineContainerScreen
import de.luckymcdev.foundryengine.common.builder.menu.EngineMenu
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.player.Inventory

class ChestScreen extends EngineContainerScreen<EngineMenu> {
	private static final Identifier TEXTURE =
			Identifier.fromNamespaceAndPath("minecraft", "textures/gui/container/generic_54.png")

	ChestScreen(EngineMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title)
		this.@imageHeight = 114 + menu.rows * 18
		this.@inventoryLabelY = this.@imageHeight - 94
	}

	@Override
	void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a)
		int x = (this.width - this.@imageWidth) / 2
		int y = (this.height - this.@imageHeight) / 2
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F,
				this.@imageWidth, menu.rows * 18 + 17, 256, 256)
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y + menu.rows * 18 + 17,
				0.0F, 126.0F, this.@imageWidth, 96, 256, 256)
	}
}