package com.turkey.turkeyUtil.items;

import java.util.List;

import com.theprogrammingturkey.gobblecore.items.BaseItem;
import com.turkey.turkeyUtil.mobs.EntityDuck;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DuckyArmy extends BaseItem
{
	public DuckyArmy()
	{
		super("Call_Of_The_Ducky_Army");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Calls xEvila's Ducky Army to the world!");
		list.add("xEvila's, Member of NoodleCraft");
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(world.isRemote)
			return stack;
		int x = (int) player.posX - 1;
		int z = (int) player.posZ - 1;
		for(int xx = 0; xx < 3; xx++)
		{
			for(int zz = 0; zz < 3; zz++)
			{
				spawnCreature(world, (double) x + xx + 0.5D, (double) player.posY + 2, (double) z + zz + 0.5D);
			}
		}
		--stack.stackSize;
		return stack;
	}

	public static Entity spawnCreature(World world, double x, double y, double z)
	{
		EntityDuck duck = null;

		for(int j = 0; j < 1; ++j)
		{
			duck = new EntityDuck(world);

			if(duck != null && duck instanceof EntityLivingBase)
			{
				duck.setLocationAndAngles((double) x + 0.5D, (double) y - 1.95D, (double) z + 0.5D, 0.0F, 0.0F);
				world.spawnEntityInWorld(duck);
				duck.setCustomNameTag("Ducky Army");

				for(int l = 0; l < 120; ++l)
					world.spawnParticle(EnumParticleTypes.SNOWBALL, (double) x + world.rand.nextDouble(), (double) (y - 2) + world.rand.nextDouble() * 3.9D, (double) z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
		}
		return duck;
	}
}
