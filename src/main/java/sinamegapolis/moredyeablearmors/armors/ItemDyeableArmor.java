package sinamegapolis.moredyeablearmors.armors;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.StringUtils;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;
import sinamegapolis.moredyeablearmors.util.Integrations;

import javax.annotation.Nullable;

/**
 * This is just a version of ItemArmor that allows every armor to be colored
 */
public class ItemDyeableArmor extends ItemArmor implements IHasModel{

    private static final String colorTag = "color";
    private static final String displayTag = "display";
    private boolean isLeatheric = false;
    // used for integration with Primitive Mobs Camouflage_dye
    private static final String rainbowTag = "israinbow";

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
        if(Loader.isModLoaded(Integrations.modId_primitiveMobs) && this.isRainbow(itemStack)){
            if(PrimitiveMobsItems.CAMOUFLAGE_CHEST!=null)
                PrimitiveMobsItems.CAMOUFLAGE_CHEST.onArmorTick(world, player, itemStack);
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
}
