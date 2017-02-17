package straywolfe.cookingwithtfc.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelGourd extends ModelBase
{
	public ModelRenderer gourd;
	
	public ModelGourd(int xoffset, int yoffset, int width, int height)
	{
		this.textureWidth = width;
        this.textureHeight = height;
        this.gourd = new ModelRenderer(this, xoffset, yoffset);
        this.gourd.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, 0.0F);
        this.gourd.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.gourd.render(f5);
    }
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.gourd.rotateAngleY = f3 / (180F / (float)Math.PI);
		this.gourd.rotateAngleX = f4 / (180F / (float)Math.PI);
    }
}
