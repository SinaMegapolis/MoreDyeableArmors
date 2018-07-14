package sinamegapolis.moredyeablearmors.armors;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
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
import org.apache.commons.lang3.StringUtils;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;
import sinamegapolis.moredyeablearmors.util.Integrations;
import sinamegapolis.moredyeablearmors.util.Utils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This is just a version of ItemArmor that allows every armor to be colored
 */
public class ItemDyeableArmor extends ItemArmor implements IHasModel{

    private static final String colorTag = "color";
    private static final String displayTag = "display";
    private boolean isLeatheric = false;
    private int ticks = 0;
    // used for integration with Primitive Mobs Camouflage_dye
    private static final String rainbowTag = "isRainbow";

    public ItemDyeableArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        if(StringUtils.containsIgnoreCase(name, "leatheric"))
            isLeatheric=true;
        ModRegistry.ITEMS.add(this);
    }

    @Override
    public int getColor(ItemStack stack) {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(displayTag);

            if (nbttagcompound1 != null && nbttagcompound1.hasKey(colorTag, 3))
            {
                return nbttagcompound1.getInteger(colorTag);
            }
        }

        return getColorBasedOnType();
    }

    @Override
    public boolean hasOverlay(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        NBTTagCompound nbttagcompound = stack.getOrCreateSubCompound(displayTag);
        return nbttagcompound != null && nbttagcompound.hasKey(colorTag, 3);
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(displayTag);

        if (!nbttagcompound.hasKey(displayTag, 10))
        {
            nbttagcompound.setTag(displayTag, nbttagcompound1);
        }

        nbttagcompound1.setInteger(colorTag, color);
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }

    public int getColorBasedOnType(){
        if(isLeatheric)
            return 10511680;
        if(StringUtils.containsIgnoreCase(getRegistryName().getResourcePath(), "gold"))
            return 15396429;
        if(StringUtils.containsIgnoreCase(getRegistryName().getResourcePath(), "diamond"))
            return 3402699;
        return 0xFFFFFF;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if(itemStack.getTagCompound().getCompoundTag(displayTag).hasKey("Name") && itemStack.getTagCompound().getCompoundTag(displayTag).getString("Name").equalsIgnoreCase("_sinamegapolis")){
            if(ticks < ModConfig.easterEggValue) ++ticks;
            else {
                this.setColor(itemStack, Utils.addHueDegreesToColor(this.getColor(itemStack), 5f));
                ticks=0;
            }
        }else {
            if (Loader.isModLoaded(Integrations.modId_primitiveMobs) && this.isRainbow(itemStack)) {
                if (PrimitiveMobsItems.CAMOUFLAGE_CHEST != null)
                    PrimitiveMobsItems.CAMOUFLAGE_CHEST.onArmorTick(world, player, itemStack);
            }
        }
    }

    public ItemStack setRainbow(boolean rainbow, ItemStack armor){
        NBTTagCompound compound = armor.getTagCompound();
        if (compound == null)
        {
            compound = new NBTTagCompound();
            armor.setTagCompound(compound);
        }

        NBTTagCompound nbttagcompound = compound.getCompoundTag(displayTag);

        if (!compound.hasKey(displayTag, 10))
        {
            compound.setTag(displayTag, nbttagcompound);
        }
        nbttagcompound.setBoolean(rainbowTag, rainbow);
        return armor;
    }

    private boolean isRainbow(ItemStack armor){
        NBTTagCompound nbttagcompound = armor.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(displayTag);

            if (nbttagcompound1 != null && nbttagcompound1.hasKey(rainbowTag, Constants.NBT.TAG_BYTE))
            {
                boolean isRainbow = nbttagcompound1.getBoolean(rainbowTag);
                return isRainbow;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(Loader.isModLoaded(Integrations.modId_primitiveMobs)) {
            if (GuiScreen.isShiftKeyDown()) {
                String isRainbow = String.valueOf(this.isRainbow(stack));
                tooltip.add(I18n.format("texts.tooltip.camouflage")+" "+I18n.format("texts.tooltip.camouflage."+isRainbow));
            }else{
                tooltip.add(I18n.format("texts.tooltip.moreinfo"));
            }
        }
    }
}
