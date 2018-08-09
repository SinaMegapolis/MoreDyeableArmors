package sinamegapolis.moredyeablearmors.capability;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;
import sinamegapolis.moredyeablearmors.util.Utils;

public class DyeableCapability implements IDyeable{

    private int color = 0;
    //used in the color recipe to see if the recipe matches or not (see ColorArmorRecipe#matches)
    private boolean canGetDyed = !ModConfig.leathericArmor;
    private boolean isRainbow = false;
    private ItemStack armorStack = ItemStack.EMPTY;
    private int ticks = 0;
    public DyeableCapability(){
        //empty constructor because why not
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean isDyeable() {
        return canGetDyed;
    }

    @Override
    public void setDyeable(boolean isDyeable) {
        canGetDyed = isDyeable;
    }

    public boolean isRainbow() {
        return isRainbow;
    }

    public void setRainbow(boolean rainbow) {
        isRainbow = rainbow;
    }

    public ItemStack getArmorStack() {
        return armorStack;
    }

    public void setArmorStack(ItemStack armorStack) {
        this.armorStack = armorStack;
    }

    public void tickPlease(EntityPlayer player){
        if(armorStack.getDisplayName().equalsIgnoreCase("sinamegapolis_")){
            if(getTicks() != 0)
                tick();
            else{
                float[] hsb = Utils.getHSB(this.getColor());
                this.setColor(Utils.getColorFromHSB(hsb[0]+5, hsb[1], hsb[2]));
            }
        }else {
            if (Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS) && this.isRainbow()) {
                //you tell me, how else would i trigger onArmorTick???
                if (PrimitiveMobsItems.CAMOUFLAGE_CHEST != null)
                    PrimitiveMobsItems.CAMOUFLAGE_CHEST.onArmorTick(player.getEntityWorld(), player, armorStack);
            }
        }
    }

    private int getTicks(){
        return ticks;
    }

    private void tick() {
        ticks = (ticks + 1) % ModConfig.easterEggValue;
    }
}
