package sinamegapolis.moredyeablearmors;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sinamegapolis.moredyeablearmors.proxy.CommonProxy;

@Mod(modid = MoreDyeableArmors.MODID, name = MoreDyeableArmors.NAME, version = MoreDyeableArmors.VERSION)
public class MoreDyeableArmors
{
    public static final String MODID = "moredyeablearmors";
    public static final String NAME = "More Dyeable Armors";
    public static final String VERSION = "1.12.2-1.0.0";

    @Mod.Instance
    public static MoreDyeableArmors INSTANCE;

    @SidedProxy(clientSide = "sinamegapolis.moredyeablearmors.proxy.ClientProxy", serverSide = "sinamegapolis.moredyeablearmors.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        PROXY.preInit(e);
        PROXY.registerRenderers();
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        PROXY.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        PROXY.postInit(e);
    }
}
