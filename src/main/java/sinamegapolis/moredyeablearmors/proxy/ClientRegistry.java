package sinamegapolis.moredyeablearmors.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID, value = Side.CLIENT)
public class ClientRegistry {
	
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent e) {
        for(Item i: ModRegistry.ITEMS) if(i instanceof IHasModel) ((IHasModel) i).registerModels();
    }
    
    @SubscribeEvent
    public static void colorItemArmors(ColorHandlerEvent.Item event){
    	for(Item i : ModRegistry.ITEMS) {
    		if(i instanceof ItemDyeableArmor) {
    			event.getItemColors().registerItemColorHandler((stack, tint) -> {
    				ItemDyeableArmor armor = (ItemDyeableArmor) stack.getItem();
    	            if(tint==0){
    	                if(armor.hasColor(stack))
    	                    return armor.getColor(stack);
    	                else
    	                    return armor.getDefaultColor();
    	            }
    	            return 0xFFFFFF;
    	        }, i);
    		}
    	}
    }
}
