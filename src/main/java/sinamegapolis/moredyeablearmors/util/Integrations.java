package sinamegapolis.moredyeablearmors.util;

import knightminer.inspirations.library.InspirationsRegistry;
import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import net.minecraft.item.ItemStack;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;

public class Integrations {
    public static final String modId_inspirations = "inspirations";
    public static final String modId_primitiveMobs = "primitivemobs";
    public static boolean integrateWithInspirations(){
        try{
            InspirationsRegistry.addCauldronRecipe(new ICauldronRecipe() {
                @Override
                public boolean matches(ItemStack stack, boolean boiling, int level, CauldronState state) {
                    return stack.getItem() instanceof ItemDyeableArmor && !boiling;
                }

                @Override
                public ItemStack getResult(ItemStack stack, boolean boiling, int level, ICauldronRecipe.CauldronState state) {
                    if (stack.getItem() instanceof ItemDyeableArmor && !boiling) {
                        if(state.getColor()!=-1)
                            ((ItemDyeableArmor) stack.getItem()).setColor(stack, state.getColor());
                        else
                            ((ItemDyeableArmor) stack.getItem()).setColor(stack, 10511680);
                    }
                    return stack;
                }

                @Override
                public int getLevel(int level) {
                    return --level;
                }
            });
            return true;
        }catch(Exception e){
            MoreDyeableArmors.logger.error(e.getMessage());
            return false;
        }
    }
}
