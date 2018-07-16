package sinamegapolis.moredyeablearmors.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID)
@Config(modid = MoreDyeableArmors.MODID)
public class ModConfig {

    private ModConfig(){}

    @Config.RequiresMcRestart
    @Config.Comment("Change this to false if you don't want armors to look like leather armors.  Client side only.")
    public static boolean leathericArmor = true;

    @Config.Comment("Super duper sekrit easter egg value")
    public static int easterEggValue=1;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
        if(event.getModID().equals(MoreDyeableArmors.MODID)){
            ConfigManager.sync(MoreDyeableArmors.MODID, Config.Type.INSTANCE);
        }
    }
}
