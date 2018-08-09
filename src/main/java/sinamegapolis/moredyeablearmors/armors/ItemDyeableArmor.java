package sinamegapolis.moredyeablearmors.armors;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;
import sinamegapolis.moredyeablearmors.util.Utils;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

/**
 * This is just a version of ItemArmor that allows every armor to be colored
 */
public class ItemDyeableArmor extends ItemArmor implements IHasModel{

    // used for integration with Primitive Mobs' Camouflage dye and also for super sekrit easter egg
    private static final String RAINBOW_TAG = "isRainbow";
    private static final String DISPLAY_TAG = "display";
    private static final String TICKS_TAG = "ticks";

    public ItemDyeableArmor(ArmorMaterial material, EntityEquipmentSlot slot, ResourceLocation registryName) {
        super(material, 0, slot);
        setRegistryName(registryName);
        setUnlocalizedName(getNameBasedOnSlot(slot)+"Gold");
        setCreativeTab(CreativeTabs.COMBAT);
        ModRegistry.ITEMS.add(this);
        this.setMaxDamage(material.getDurability(slot));
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomMeshDefinition(this,(ItemStack stack)->{
            ItemDyeableArmor itemarmor = (ItemDyeableArmor) stack.getItem();
            if(ModConfig.leathericArmor)
                return new ModelResourceLocation(new ResourceLocation(MoreDyeableArmors.MODID,getRegistryName().getResourcePath()+"_leatheric"), "inventory");
            return (this.getColor(stack)==-1) &&
                    !ModConfig.leathericArmor &&
                    (itemarmor.getArmorMaterial()==ArmorMaterial.GOLD || itemarmor.getArmorMaterial()==ArmorMaterial.DIAMOND)
            ? new ModelResourceLocation(new ResourceLocation(MoreDyeableArmors.MODID,getRegistryName().getResourcePath()+"_normal"), "inventory")
                    : new ModelResourceLocation(getRegistryName(), "inventory");
        } );
        ModelBakery.registerItemVariants(this,
                new ModelResourceLocation(new ResourceLocation(MoreDyeableArmors.MODID,getRegistryName().getResourcePath()+"_normal"), "inventory"),
                new ModelResourceLocation(getRegistryName(), "inventory"),
                new ModelResourceLocation(new ResourceLocation(MoreDyeableArmors.MODID,getRegistryName().getResourcePath()+"_leatheric"),"inventory"));
    }

    @Override
    public int getColor(ItemStack stack) {
        if(stack.getCapability(Capabilities.DYEABLE, null).getColor()!=0)
            return stack.getCapability(Capabilities.DYEABLE, null).getColor();

        return getDefaultColor();
    }

    @Override
    public boolean hasOverlay(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        return stack.hasCapability(Capabilities.DYEABLE, null);
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getCapability(Capabilities.DYEABLE, null).setColor(color);
    }

    private int getDefaultColor(){
        if(ModConfig.leathericArmor)
            return 10511680;
        return -1;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if(stack.getDisplayName().equalsIgnoreCase("_sinamegapolis")){
            if(getTicks(stack) != 0){
                tick(stack);
            }
            else {
                float[] hsb = Utils.getHSB(this.getColor(stack));
                this.setColor(stack, Utils.getColorFromHSB(hsb[0]+5, hsb[1], hsb[2]));
                stack.damageItem(1, player);
            }
        }else {
            if (Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS) && this.isRainbow(stack)) {
                if (PrimitiveMobsItems.CAMOUFLAGE_CHEST != null)
                    PrimitiveMobsItems.CAMOUFLAGE_CHEST.onArmorTick(world, player, stack);
            }
        }
    }

    private int getTicks(ItemStack stack) {
    	return stack.getOrCreateSubCompound(DISPLAY_TAG).getInteger(TICKS_TAG);
    }

    private void tick(ItemStack stack) {
    	stack.getOrCreateSubCompound(DISPLAY_TAG).setInteger(TICKS_TAG, (getTicks(stack) + 1) % ModConfig.easterEggValue);
    }

    public ItemStack setRainbow(boolean rainbow, ItemStack armor){
        armor.getOrCreateSubCompound(DISPLAY_TAG).setBoolean(RAINBOW_TAG, rainbow);
        return armor;
    }

    private boolean isRainbow(ItemStack armor){
    	return armor.getOrCreateSubCompound(DISPLAY_TAG).getBoolean(RAINBOW_TAG);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS)) {
            if (GuiScreen.isShiftKeyDown()) {
                tooltip.add(I18n.format("texts.moredyeablearmors.tooltip.camouflage") + " " + I18n.format("texts.moredyeablearmors.tooltip.camouflage." + isRainbow(stack)));
            }else{
                tooltip.add(I18n.format("texts.moredyeablearmors.tooltip.moreinfo"));
            }
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if((!this.hasColor(stack) || this.getColor(stack)==-1) &&  !"overlay".equals(type))
            return "minecraft:textures/models/armor/"+(ModConfig.leathericArmor? "leather" :this.getArmorMaterial())+"_layer_"+(slot== LEGS?"2":"1")+".png";
    	return MoreDyeableArmors.MODID + ":textures/armor/" + (ModConfig.leathericArmor ? "leather_" : "") + this.getArmorMaterial().getName() + (slot == LEGS ? "_legs" : "") + ("overlay".equals(type) ? "_overlay" : "") + ".png";
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if(stack.getDisplayName().equalsIgnoreCase("_sinamegapolis"))
            return 360;
        return super.getMaxDamage(stack);
    }

    private String getNameBasedOnSlot(EntityEquipmentSlot slot){
        String result;
        switch(slot){
            case HEAD:
                result = "helmet";
                break;
            case CHEST:
                result = "chestplate";
                break;
            case LEGS:
                result = "leggings";
                break;
            case FEET:
                result = "boots";
                break;
                default:
                    result = "";
                    break;
        }
        return result;
    }
}
