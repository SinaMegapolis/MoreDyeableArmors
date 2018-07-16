package sinamegapolis.moredyeablearmors.util;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.registries.IForgeRegistryEntry;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;

public class ColorArmorRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem() instanceof ItemDyeableArmor)
                {
                    if (!itemstack.isEmpty())
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (!DyeUtils.isDye(itemstack1))
                    {
                        return false;
                    }

                    list.add(itemstack1);
                }
            }
        }

        return !itemstack.isEmpty() && !list.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemDyeableArmor itemarmor = null;
        int oldColor = -1;
        int newColor = -1;
        
        for (int k = 0; k < inv.getSizeInventory(); ++k)
        {
            ItemStack stack = inv.getStackInSlot(k);

            if (!stack.isEmpty())
            {
                if (stack.getItem() instanceof ItemDyeableArmor)
                {
                    itemarmor = (ItemDyeableArmor) stack.getItem();

                    if (!itemstack.isEmpty())
                    {
                        return ItemStack.EMPTY;
                    }

                    itemstack = stack.copy();
                    itemstack.setCount(1);

                    if (itemarmor.hasColor(stack))
                    {
                        oldColor = itemarmor.getColor(itemstack);
                    }
                    break;
                }
            }
        }

        for (int k = 0; k < inv.getSizeInventory(); ++k)
        {
            ItemStack stack = inv.getStackInSlot(k);

            if (!stack.isEmpty())
            {
            	if (!DyeUtils.isDye(stack)) continue;
            	int color = Utils.getColorFromColor(DyeUtils.colorFromStack(stack).get());
                   	if(newColor == -1) newColor = color;
                   	else newColor = Utils.combineColors(newColor, color, 1);
            }
        }

        if (itemarmor == null || newColor == -1) return ItemStack.EMPTY;
        else
        {
            if(oldColor == -1 || oldColor == itemarmor.getDefaultColor()) itemarmor.setColor(itemstack, newColor);
            else itemarmor.setColor(itemstack, Utils.combineColors(oldColor, newColor, 1));
            return itemstack;
        }
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= 2;
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
