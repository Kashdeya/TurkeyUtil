package com.turkey.turkeyUtil.mobs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoxel extends RenderLiving
{
    private static final ResourceLocation wolfTextures = new ResourceLocation("turkeyutil:textures/entity/voxel.png");
    private static final ResourceLocation tamedWolfTextures = new ResourceLocation("turkeyutil:textures/entity/voxel.png");

    public RenderVoxel(ModelBase p_i1269_1_, float p_i1269_3_)
    {
        super(p_i1269_1_, p_i1269_3_);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityVoxel p_77044_1_, float p_77044_2_)
    {
        return p_77044_1_.getTailRotation();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityVoxel p_110775_1_)
    {
        return p_110775_1_.isTamed() ? tamedWolfTextures : wolfTextures;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_)
    {
        return this.handleRotationFloat((EntityVoxel)p_77044_1_, p_77044_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityVoxel)p_110775_1_);
    }
}