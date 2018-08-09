package sinamegapolis.moredyeablearmors.recipes;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.IForgeRegistryEntry;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;

import java.util.ArrayList;
import java.util.List;

public class CamouflageDyeArmorRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if(!Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS))
            return false;
        List<Item> ingridients = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i).copy();
            if((stack.getItem() instanceof ItemArmor && stack.hasCapability(Capabilities.DYEABLE, null)) || stack.getItem() == PrimitiveMobsItems.CAMOUFLAGE_DYE)
                ingridients.add(stack.getItem());
        }
        return ingridients.size()==2 && ingridients.contains(PrimitiveMobsItems.CAMOUFLAGE_DYE);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stack = inv.getStackInSlot(slot).copy();
            if(stack.getItem() instanceof ItemArmor){
                stack.getCapability(Capabilities.DYEABLE, null).setRainbow(true);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonnulllist;
    }
}
