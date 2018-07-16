package sinamegapolis.moredyeablearmors.util;

import knightminer.inspirations.library.InspirationsRegistry;
import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import net.minecraft.item.ItemStack;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.config.ModConfig;

public class IntegrateInspirations {
	
    public static boolean tryLoading(){
        try{
            InspirationsRegistry.addCauldronRecipe(new ICauldronRecipe() {
                @Override
                public boolean matches(ItemStack stack, boolean boiling, int level, CauldronState state) {
                    return stack.getItem() instanceof ItemDyeableArmor && !boiling && level>0;
                }

                @Override
                public ItemStack getResult(ItemStack stack, boolean boiling, int level, ICauldronRecipe.CauldronState state) {
                    ItemStack stackarmor = ItemStack.EMPTY;
                    if (stack.getItem() instanceof ItemDyeableArmor && !boiling) {
                        //just in case
                        stackarmor = stack.copy();
                        ItemDyeableArmor itemarmor = (ItemDyeableArmor) stackarmor.getItem();
                        if(state.getColor()!=-1)
                            itemarmor.setColor(stackarmor, state.getColor());
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
