package sinamegapolis.moredyeablearmors.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID, value = Side.CLIENT)
public class ClientRegistry {
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent e) {
        for(Item i: ModRegistry.ITEMS) if(i instanceof IHasModel) ((IHasModel) i).registerModels();
    }
}
