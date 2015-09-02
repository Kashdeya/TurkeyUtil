package com.turkey.turkeyUtil.util;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.turkey.turkeyUtil.blocks.UtilBlocks;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator 
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		if(UtilSettings.isBlockedWorld(world.getWorldInfo().getWorldName()))
			return;
		generateOre(world, random, chunkX * 16, chunkZ * 16);
	}

	private void generateOre(World world, Random rand, int chunkX, int chunkZ) 
	{
		for(int k = 0; k < 5; k++)
		{
			int firstBlockXCoord = chunkX + rand.nextInt(16);
			int firstBlockYCoord = rand.nextInt(20);
			int firstBlockZCoord = chunkZ + rand.nextInt(16);

			(new WorldGenMinable(UtilBlocks.mysticalOreBlock, 3)).generate(world, rand, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
	}
}