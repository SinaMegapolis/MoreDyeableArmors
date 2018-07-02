package sinamegapolis.moredyeablearmors.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.init.IHasModel;
import sinamegapolis.moredyeablearmors.init.ModRegistry;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID, value = Side.CLIENT)
public class ClientRegistry {
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent e) {
        for(net.minecraft.block.Block b  : ModRegistry.BLOCKS) if(b instanceof IHasModel) ((IHasModel) b).registerModels();
        for(Item i: ModRegistry.ITEMS) if(i instanceof IHasModel) ((IHasModel) i).registerModels();
    }
}
