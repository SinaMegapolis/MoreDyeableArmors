package sinamegapolis.moredyeablearmors.util;

import knightminer.inspirations.library.InspirationsRegistry;
import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.config.ModConfig;

public class IntegrateInspirations {
	
    public static boolean tryLoading(){
        try{
            InspirationsRegistry.addCauldronRecipe(new ICauldronRecipe() {
                @Override
                public boolean matches(ItemStack stack, boolean boiling, int level, CauldronState state) {
                    return stack.getItem() instanceof ItemArmor && !boiling && level>0;
                }

                @Override
                public ItemStack getResult(ItemStack stack, boolean boiling, int level, ICauldronRecipe.CauldronState state) {
                    ItemStack stackarmor = ItemStack.EMPTY;
                    if (stack.getItem() instanceof ItemArmor && !boiling) {
                        //just in case
                        stackarmor = stack.copy();
                        if(state.getColor()!=-1)
                            stackarmor.getCapability(Capabilities.DYEABLE, null).setColor(state.getColor());
                        else {
                            stackarmor.getCapability(Capabilities.DYEABLE, null).setColor(0);
                            if(ModConfig.leathericArmor)
                                stackarmor.getCapability(Capabilities.DYEABLE, null).setDyeable(true);
                        }
                    }
                    return stackarmor;
                }

                @Override
                public int getLevel(int level) {
                    return --level;
                }
            });
            return true;
        }catch(Exception e){
            MoreDyeableArmors.LOGGER.error(e.getMessage());
            return false;
        }
    }

}
