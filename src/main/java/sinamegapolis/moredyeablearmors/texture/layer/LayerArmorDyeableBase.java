package sinamegapolis.moredyeablearmors.texture.layer;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.capability.DyeableCapability;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.init.ModRegistry;
import sinamegapolis.moredyeablearmors.texture.ArmorTextureHandler;
import sinamegapolis.moredyeablearmors.util.Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class LayerArmorDyeableBase extends LayerBipedArmor {

    private RenderLivingBase<?> renderer;
    private float alpha = 1.0F;
    private float colorR = 1.0F;
    private float colorG = 1.0F;
    private float colorB = 1.0F;
    private boolean skipRenderGlint;
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.<String, ResourceLocation>newHashMap();

    public LayerArmorDyeableBase(RenderLivingBase<?> rendererIn) {
        super(rendererIn);
        this.renderer=rendererIn;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
    }

    private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
    {
        ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

        if (itemstack.getItem() instanceof ItemArmor && itemstack.getCapability(Capabilities.DYEABLE, null).getColor()!=0)
        {
            ItemArmor itemarmor = (ItemArmor)itemstack.getItem();

            if (itemarmor.getEquipmentSlot() == slotIn)
            {
                ModelBiped t = this.getModelFromSlot(slotIn);
                t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
                t.setModelAttributes(this.renderer.getMainModel());
                t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.setModelSlotVisible(t, slotIn);
                this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, null));

                {
                    if (itemstack.getCapability(Capabilities.DYEABLE,null ).getColor()!=0) // Allow this for anything, not only cloth
                    {
                        int i = itemstack.getCapability(Capabilities.DYEABLE,null ).getColor();
                        float f = (float)(i >> 16 & 255) / 255.0F;
                        float f1 = (float)(i >> 8 & 255) / 255.0F;
                        float f2 = (float)(i & 255) / 255.0F;
                        GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
                        t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    }
                    // Non-colored
                    //TODO: fix this silly if if else if pif system that causes white armors bug
                    if(false) {
                        if (ModConfig.leathericArmor || slotIn != EntityEquipmentSlot.HEAD || itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.CHAIN) {
                            this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, "overlay"));
                            GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
                            t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                        }
                    }
                    if (!this.skipRenderGlint && itemstack.hasEffect())
                    {
                        renderEnchantedGlint(this.renderer, entityLivingBaseIn, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                    }
                }
            }
        }else super.doRenderLayer(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type) {
        int color = stack.getCapability(Capabilities.DYEABLE, null).getColor();
        if(color!=0){
            ResourceLocation originalLoc = getOriginalArmorResource(entity, stack, slot, null);
            IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
            TextureManager texManager = Minecraft.getMinecraft().getTextureManager();
            BufferedImage overlay;
            BufferedImage layer;
            ResourceLocation resultLoc = null;
            try {
                int defaultColor = ModRegistry.getItemTextureHandler(((ItemArmor)stack.getItem()).getArmorMaterial().getName()).getColor();
                if (isLegSlot(slot)) {
                    layer = Utils.whitify(TextureUtil.readBufferedImage(manager.getResource(ModConfig.leathericArmor ? new ResourceLocation("minecraft","textures/models/armor/leather_layer_2.png") : originalLoc).getInputStream()));
                    overlay = Utils.changeColor(ArmorTextureHandler.generateLayer2Overlay(layer), defaultColor);
                }else{
                    layer = Utils.whitify(TextureUtil.readBufferedImage(manager.getResource(ModConfig.leathericArmor ? new ResourceLocation("minecraft","textures/models/armor/leather_layer_1.png") : originalLoc).getInputStream()));
                    overlay = Utils.changeColor(ArmorTextureHandler.generateLayer1Overlay(layer), defaultColor);
                }

                if(type==null)
                    resultLoc = texManager.getDynamicTextureLocation("generatedDyeableArmorLayer", new DynamicTexture(layer));
                else if(type.equals("overlay"))
                    resultLoc = texManager.getDynamicTextureLocation("generatedDyeableArmorLayerOverlay", new DynamicTexture(overlay));

                if(resultLoc!=null)
                    return resultLoc;
            }catch(IOException e){
                MoreDyeableArmors.LOGGER.error("Armor texture handler can't initialize itself :ohno:");
                MoreDyeableArmors.LOGGER.error("Report this error to creator (SinaMegapolis) on github https://bit.ly/2muE81e");
                MoreDyeableArmors.LOGGER.error(e.getMessage());
            }
        }
        return super.getArmorResource(entity, stack, slot, type);
    }

    //copy pasted from LayerArmorBase to avoid recursive call
    private ResourceLocation getOriginalArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type)
    {
        ItemArmor item = (ItemArmor)stack.getItem();
        String texture = item.getArmorMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1)
        {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (isLegSlot(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }

        return resourcelocation;
    }
    private boolean isLegSlot(EntityEquipmentSlot slotIn)
    {
        return slotIn == EntityEquipmentSlot.LEGS;
    }
}
