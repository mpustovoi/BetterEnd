package ru.betterend.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import ru.betterend.blocks.basis.EndPillarBlock;

public class AmaranitaHyphaeBlock extends EndPillarBlock {
	public AmaranitaHyphaeBlock() {
		super(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).materialColor(MaterialColor.LIME));
	}
}
