package com.turkey.turkeyUtil.registries;

import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.turkey.turkeyUtil.util.CustomEntry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class LightRegistry
{
	public static LightRegistry instance = new LightRegistry();
	
	private List<Entry<FilterBlock, FilterColor>> lightFilters = Lists.newArrayList();
	
	public void addLightFilter(Block block, int meta, FilterColor color)
	{
		FilterBlock fb = new FilterBlock(block, meta);
		lightFilters.add(new CustomEntry<FilterBlock, FilterColor>(fb, color));
	}
	
	public void removeLightFilter(Block block, int meta)
	{
		for(Entry<FilterBlock, FilterColor> entry: lightFilters)
			if(entry.getKey().getBlock().equals(block) && entry.getKey().getMeta() == meta)
				lightFilters.remove(entry);
	}
	
	public FilterColor getFilterColorFromBlock(Block block, int meta)
	{
		for(Entry<FilterBlock, FilterColor> entry: lightFilters)
			if(entry.getKey().getBlock().equals(block) && entry.getKey().getMeta() == meta)
				return entry.getValue();
		return FilterColor.clear;
	}
	
	public boolean isFilterBlock(Block block, int meta)
	{
		for(Entry<FilterBlock, FilterColor> entry: lightFilters)
			if(entry.getKey().getBlock().equals(block) && entry.getKey().getMeta() == meta)
				return true;
		return false;
	}
	
	
	public void loadDefaultFilters()
	{
		this.addLightFilter(Blocks.stained_glass, 0, FilterColor.white);
		this.addLightFilter(Blocks.stained_glass, 1, FilterColor.orange);
		this.addLightFilter(Blocks.stained_glass, 2, FilterColor.magenta);
		this.addLightFilter(Blocks.stained_glass, 3, FilterColor.light_blue);
		this.addLightFilter(Blocks.stained_glass, 4, FilterColor.yellow);
		this.addLightFilter(Blocks.stained_glass, 5, FilterColor.lime);
		this.addLightFilter(Blocks.stained_glass, 6, FilterColor.pink);
		this.addLightFilter(Blocks.stained_glass, 7, FilterColor.gray);
		this.addLightFilter(Blocks.stained_glass, 8, FilterColor.silver);
		this.addLightFilter(Blocks.stained_glass, 9, FilterColor.cyan);
		this.addLightFilter(Blocks.stained_glass, 10, FilterColor.purple);
		this.addLightFilter(Blocks.stained_glass, 11, FilterColor.blue);
		this.addLightFilter(Blocks.stained_glass, 12, FilterColor.brown);
		this.addLightFilter(Blocks.stained_glass, 13, FilterColor.green);
		this.addLightFilter(Blocks.stained_glass, 14, FilterColor.red);
		this.addLightFilter(Blocks.stained_glass, 15, FilterColor.black);
		this.addLightFilter(Blocks.glass, 0, FilterColor.clear);
		this.addLightFilter(Blocks.air, 0, FilterColor.clear);
	}
	
	
	
	public enum FilterColor
	{
		black("Black", 15),
		blue("Blue", 11),
		brown("Brown", 12),
		cyan("Cyan", 9),
		gray("Gray", 7),
		green("Green", 13),
		light_blue("Light Blue", 3),
		lime("Lime", 5),
		clear("Clear", 16),
		magenta("Magenta", 2),
		orange("Orange", 1),
		pink("Pink", 6),
		purple("Purple", 10),
		red("Red", 14),
		silver("Silver", 8),
		white("White", 0),
		yellow("Yellow", 4);
		
		private String name;
		private int meta;
		
		FilterColor(String name, int meta)
		{
			this.name = name;
			this.meta = meta;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public int getMeta()
		{
			return this.meta;
		}
	}
	
	public class FilterBlock
	{
		private Block block;
		private int meta;
		
		public FilterBlock(Block block, int meta)
		{
			this.block = block;
			this.meta = meta;
		}

		public Block getBlock()
		{
			return block;
		}

		public int getMeta()
		{
			return meta;
		}	
	}
}