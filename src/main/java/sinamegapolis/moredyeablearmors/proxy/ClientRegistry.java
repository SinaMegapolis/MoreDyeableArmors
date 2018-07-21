package sinamegapolis.moredyeablearmors.proxy;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;
import sun.misc.Cache;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID, value = Side.CLIENT)
public class ClientRegistry {
	
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent e) {
        for(Item i: ModRegistry.ITEMS) if(i instanceof IHasModel) ((IHasModel) i).registerModels();
    }
    
    @SubscribeEvent
    public static void colorItemArmors(ColorHandlerEvent.Item event){
    	for(Item i : ModRegistry.ITEMS) {
    		if(i instanceof ItemArmor) {
    			event.getItemColors().registerItemColorHandler((stack, tint) -> {
    				ItemArmor armor = (ItemArmor) stack.getItem();
    	            if(tint==0){
						if(stack.hasCapability(Capabilities.DYEABLE, null) && stack.getCapability(Capabilities.DYEABLE, null).getColor()!=250)
							return stack.getCapability(Capabilities.DYEABLE, null).getColor();
    	                else if(armor.hasColor(stack))
							return armor.getColor(stack);
    	            }
    	            return 0xFFFFFF;
    	        }, i);
    		}
    	}
		for(ItemArmor armor : ModRegistry.armorList){
            event.getItemColors().registerItemColorHandler((stack, tint) -> {
                if(tint==0){
					if(stack.hasCapability(Capabilities.DYEABLE, null) && stack.getCapability(Capabilities.DYEABLE, null).getColor()!=250)
						return stack.getCapability(Capabilities.DYEABLE, null).getColor();
                }
                return 0xFFFFFF;
            }, armor);
        }
    }
}
