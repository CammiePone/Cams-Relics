package com.camellias.relics.init;

import java.util.ArrayList;
import java.util.List;

import com.camellias.relics.common.blocks.BlockPestal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
	//-----Block list-----//
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block PEDESTAL = new BlockPestal("relic_pedestal", Material.ROCK);
}
