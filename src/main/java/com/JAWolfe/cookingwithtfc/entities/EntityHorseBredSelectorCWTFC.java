package com.JAWolfe.cookingwithtfc.entities;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

final class EntityHorseBredSelectorCWTFC implements IEntitySelector
{
    /**
     * Return whether the specified entity is applicable to this filter.
     */
    @Override
	public boolean isEntityApplicable(Entity par1Entity)
    {
        return par1Entity instanceof EntityTransformHorseTFC && ((EntityTransformHorseTFC)par1Entity).func_110205_ce();
    }
}
