package sinamegapolis.moredyeablearmors.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import sinamegapolis.moredyeablearmors.texture.layer.LayerArmorDyeableBase;

import java.util.List;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent e) {
        for (RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            try {
                List<LayerRenderer<EntityLivingBase>> layers = (List<LayerRenderer<EntityLivingBase>>) ReflectionHelper.findField(RenderLivingBase.class,"layerRenderers").get(playerRender);
                layers.removeIf(layer -> layer instanceof LayerArmorBase); //because optimization! see LayerArmorDyeableBase#renderArmorLayer
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        RenderPlayer steve =Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
        steve.addLayer(new LayerArmorDyeableBase(steve));
        RenderPlayer alex =Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
        alex.addLayer(new LayerArmorDyeableBase(alex));
        Render<Entity> renderer =  Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityArmorStand.class);

        if (renderer instanceof RenderLivingBase) {

            ((RenderLivingBase<?>) renderer).addLayer(new LayerArmorDyeableBase((RenderLivingBase<?>) renderer));
        }
    }
}
