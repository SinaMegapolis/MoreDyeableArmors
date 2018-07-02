package sinamegapolis.moredyeablearmors.util;

import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.registries.IForgeRegistryEntry;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;

import java.util.List;
import java.util.Optional;

/**
 * literally just a copy of @link RecipesArmorDyes but removed leather condition
 */
public class ColorArmorRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem() instanceof ItemArmor)
                {
                    if (!itemstack.isEmpty())
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (!net.minecraftforge.oredict.DyeUtils.isDye(itemstack1))
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
        ItemArmor itemarmor = null;
        int armorColor = 0;
        int dyeColor = 0;
        boolean armorHasColor = false;
        for (int k = 0; k < inv.getSizeInventory(); ++k)
        {
            ItemStack itemstack1 = inv.getStackInSlot(k);

            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem() instanceof ItemArmor)
                {
                    itemarmor = (ItemArmor)itemstack1.getItem();

                    if (!itemstack.isEmpty())
                    {
                        return ItemStack.EMPTY;
                    }

                    itemstack = itemstack1.copy();
                    itemstack.setCount(1);

                    if (itemarmor.hasColor(itemstack1))
                    {
                        armorHasColor =true;
                        armorColor = itemarmor.getColor(itemstack);
                    }
                }
                else
                {
                    if (!net.minecraftforge.oredict.DyeUtils.isDye(itemstack1))
                    {
                        return ItemStack.EMPTY;
                    }
                    Optional<EnumDyeColor> color = DyeUtils.colorFromStack(itemstack1);
                    if(color.isPresent())
                        dyeColor = color.get().getColorValue();
                    else
                        return ItemStack.EMPTY; //should never happen tho
                }
            }
        }

        if (itemarmor == null)
        {
            return ItemStack.EMPTY;
        }
        else
        {
            if(armorHasColor)
                itemarmor.setColor(itemstack, ColorUtils.combineColors(armorColor, dyeColor, 1));
            if(!armorHasColor)
                itemarmor.setColor(itemstack, dyeColor);
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
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

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
