package sinamegapolis.moredyeablearmors.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IDyeable {

    int getColor();

    void setColor(int color);

    boolean isDyeable();

    void setDyeable(boolean dyeable);

    boolean isRainbow();

    void setRainbow(boolean rainbow);

    ItemStack getArmorStack();

    void setArmorStack(ItemStack armorStack);

    void tickPlease(EntityPlayer player);
}
