package sinamegapolis.moredyeablearmors.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import sinamegapolis.moredyeablearmors.texture.layer.LayerArmorDyeableBase;

import java.util.List;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent e) {
        for (RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            removeLayer(playerRender);
            playerRender.addLayer(new LayerArmorDyeableBase(playerRender));
        }

        //armor stand support
        addLayerToEntity(EntityArmorStand.class,
                new ModelArmorStandArmor(1.0f),
                new ModelArmorStandArmor(0.5f));

        //zombie villager support
        addLayerToEntity(EntityZombieVillager.class,
                    new ModelZombieVillager(1.0F, 0.0F, true),
                    new ModelZombieVillager(0.5F, 0.0F, true));

        //skeleton support
        addLayerToEntity(EntitySkeleton.class,
                new ModelSkeleton(1.0f,true),
                new ModelSkeleton(0.5f,true));

        //zombie support
        addLayerToEntity(EntityZombie.class,
                new ModelZombie(1.0f, true),
                new ModelZombie(0.5f, true));

        //zombie pigman support
        addLayerToEntity(EntityPigZombie.class,
                new ModelZombie(1.0f, true),
                new ModelZombie(0.5f, true));

    }

    private void addLayerToEntity(Class<? extends EntityLivingBase> clazz, ModelBiped armorModel, ModelBiped leggingsModel){
        Render<Entity> renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(clazz);
        if (renderer instanceof RenderLivingBase) {
            removeLayer((RenderLivingBase<?>) renderer);

            ((RenderLivingBase<?>) renderer).addLayer(new LayerArmorDyeableBase((RenderLivingBase<?>) renderer){
                @Override
                protected void initArmor() {
                    this.modelLeggings = leggingsModel;
                    this.modelArmor = armorModel;
                }
            });
        }
    }

    private void removeLayer(RenderLivingBase<?> renderIn){
        try {
            List<LayerRenderer<EntityLivingBase>> layers = (List<LayerRenderer<EntityLivingBase>>) ReflectionHelper.findField(RenderLivingBase.class,"layerRenderers").get(renderIn);
            layers.removeIf(layer -> layer instanceof LayerArmorBase); //because optimization! see LayerArmorDyeableBase#renderArmorLayer
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
}
