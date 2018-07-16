package sinamegapolis.moredyeablearmors.armors;

import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.client.gui.GuiScreen;
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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;
import sinamegapolis.moredyeablearmors.util.Utils;

/**
 * This is just a version of ItemArmor that allows every armor to be colored
 */
public class ItemDyeableArmor extends ItemArmor implements IHasModel{

    // used for integration with Primitive Mobs Camouflage_dye
    public static final String RAINBOW_TAG = "isRainbow";
    public static final String COLOR_TAG = "color";
    public static final String DISPLAY_TAG = "display";
    public static final String TICKS_TAG = "ticks";

    public ItemDyeableArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(MoreDyeableArmors.MODID, name);
        setUnlocalizedName(MoreDyeableArmors.MODID + "." + name);
        setCreativeTab(CreativeTabs.COMBAT);
        ModRegistry.ITEMS.add(this);
    }

    @Override
    public int getColor(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();

        if (tag != null)
        {
            NBTTagCompound displayTag = tag.getCompoundTag(DISPLAY_TAG);

            if (displayTag != null && displayTag.hasKey(COLOR_TAG, Constants.NBT.TAG_INT))
            {
                return displayTag.getInteger(COLOR_TAG);
            }
        }

        return getDefaultColor();
    }

    @Override
    public boolean hasOverlay(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        NBTTagCompound tag = stack.getOrCreateSubCompound(DISPLAY_TAG);
        return tag.hasKey(COLOR_TAG, Constants.NBT.TAG_INT);
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubCompound(DISPLAY_TAG).setInteger(COLOR_TAG, color);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }

    public int getDefaultColor(){
        if(ModConfig.leathericArmor)
            return 10511680;
        if(this.getArmorMaterial() == ArmorMaterial.GOLD)
            return 15396429;
        if(this.getArmorMaterial() == ArmorMaterial.DIAMOND)
            return 3402699;
        return 0xFFFFFF;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if(stack.getDisplayName().equalsIgnoreCase("_sinamegapolis")){
            if(getTicks(stack) != 0) tick(stack);
            else {
                this.setColor(stack, Utils.addHueDegreesToColor(this.getColor(stack), 5));
            }
        }else {
            if (Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS) && this.isRainbow(stack)) {
                if (PrimitiveMobsItems.CAMOUFLAGE_CHEST != null)
                    PrimitiveMobsItems.CAMOUFLAGE_CHEST.onArmorTick(world, player, stack);
            }
        }
    }

    public int getTicks(ItemStack stack) {
    	return stack.getOrCreateSubCompound(DISPLAY_TAG).getInteger(TICKS_TAG);
    }

    public void tick(ItemStack stack) {
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
    	return MoreDyeableArmors.MODID + ":textures/armor/" + (ModConfig.leathericArmor ? "leather_" : "") + this.getArmorMaterial().getName() + (slot == EntityEquipmentSlot.LEGS ? "_legs" : "") + ("overlay".equals(type) ? "_overlay" : "") + ".png";
    }
}
