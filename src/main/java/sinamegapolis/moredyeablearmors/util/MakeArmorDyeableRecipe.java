package sinamegapolis.moredyeablearmors.util;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.init.ModRegistry;

import java.util.ArrayList;
import java.util.List;

public class MakeArmorDyeableRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack armor = ItemStack.EMPTY;
        List<Item> ingridients = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack.getCount()==1){
                if(stack.getItem() instanceof ItemArmor) armor = stack;
                else if(stack.getItem()==Items.SLIME_BALL){
                    if(ingridients.contains(Items.SLIME_BALL)) return false;
                    ingridients.add(stack.getItem());
                }
                else if(stack.getItem()==Items.STRING){
                    if(ingridients.contains(Items.STRING)) return false;
                    ingridients.add(stack.getItem());
                }
                else if(stack.getItem()==Items.LEATHER){
                    if(ingridients.contains(Items.LEATHER)) return false;
                    ingridients.add(stack.getItem());
                }
            }
        }
        return !armor.isEmpty() && ingridients.size()==3 && !armor.getCapability(Capabilities.DYEABLE, null).isDyeable();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack armorStack = ItemStack.EMPTY;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if(inv.getStackInSlot(i).getItem() instanceof ItemArmor){
                armorStack = inv.getStackInSlot(i).copy();
            }
        }
        if(((ItemArmor)armorStack.getItem()).getArmorMaterial()== ItemArmor.ArmorMaterial.GOLD) {
            ItemStack stack = new ItemStack(ModRegistry.armorMap.get(armorStack.getItem()),armorStack.getCount());
            stack.setTagCompound(armorStack.getTagCompound());
            armorStack = stack;
        }else armorStack.getCapability(Capabilities.DYEABLE, null).setDyeable(true);
        return armorStack;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= 4;
    }

    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
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

    @Override
    public boolean isDynamic()
    {
        return true;
    }
}
