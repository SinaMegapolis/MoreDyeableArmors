package sinamegapolis.moredyeablearmors.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import sinamegapolis.moredyeablearmors.texture.layer.LayerArmorDyeableBase;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent e) {
        for (RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            playerRender.addLayer(new LayerArmorDyeableBase(playerRender));
        }
    }
}
