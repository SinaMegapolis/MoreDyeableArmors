package sinamegapolis.moredyeablearmors.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ItemTextureQuadConverter;
import scala.tools.cmd.gen.AnyVals;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.util.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ArmorTextureHandler {
    private static final IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
    private static final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
    private static BufferedImage overlayLayer1 = null;
    private static BufferedImage overlayLayer2 = null;

    static {
        try {
            overlayLayer1 = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation("minecraft","textures/models/armor/leather_layer_1_overlay.png")).getInputStream());
            overlayLayer2 = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation("minecraft","textures/models/armor/leather_layer_2_overlay.png")).getInputStream());
        } catch (IOException e) {
            MoreDyeableArmors.LOGGER.error("Armor texture handler can't initialize itself :ohno:");
            MoreDyeableArmors.LOGGER.error("Report this error to creator (SinaMegapolis) on github https://bit.ly/2muE81e");
            MoreDyeableArmors.LOGGER.error(e.getMessage());
        }
    }

    public static BufferedImage generateLayer2Overlay(BufferedImage layer2){
        if(overlayLayer2==null)
            return null;
        BufferedImage overlay = Utils.deepCopy(layer2);
        overlay = Utils.cutImage(overlay, overlayLayer2);
        if(overlay!=null) {
            //makes the leggings overlay
            int c1 = overlay.getRGB(18, 28);
            float[] hsb1 = Utils.getHSB(c1);
            int mainColor = Utils.getColorFromHSB(hsb1[0], hsb1[1] - 1, hsb1[2] + 6);
            overlay.setRGB(5, 24, mainColor);
            overlay.setRGB(6, 24, mainColor);
            overlay.setRGB(6, 23, mainColor);
            float[] hsb2 = Utils.getHSB(mainColor);
            int secondColor = Utils.getColorFromHSB(hsb2[0], hsb2[1] + 1, hsb2[2] + 2);
            overlay.setRGB(5, 23, secondColor);
            float[] hsb3 = Utils.getHSB(secondColor);
            int thirdColor = Utils.getColorFromHSB(hsb3[0], hsb3[1], hsb3[2] - 11);
            overlay.setRGB(5, 22, thirdColor);
            overlay.setRGB(6, 22, thirdColor);
            overlay.setRGB(5, 25, thirdColor);
            overlay.setRGB(6, 25, thirdColor);
            overlay.setRGB(4, 23, thirdColor);
            overlay.setRGB(4, 24, thirdColor);
            overlay.setRGB(7, 23, thirdColor);
            overlay.setRGB(7, 24, thirdColor);
        }
        return overlay;
    }

    public static BufferedImage generateLayer1Overlay(BufferedImage layer1){
        if(overlayLayer1==null)
            return null;
        BufferedImage overlay = Utils.deepCopy(layer1);
        overlay = Utils.cutImage(overlay, overlayLayer1);
        if(overlay!=null){
            //part 1: boots floor texture
            int c1 = overlay.getRGB(10, 18);
            float[] hsb1 = Utils.getHSB(c1);
            int firstC = Utils.getColorFromHSB(hsb1[0], hsb1[1]+1, hsb1[2]-9);
            overlay.setRGB(8 , 16, firstC);
            overlay.setRGB(8 , 17, firstC);
            overlay.setRGB(8 , 18, firstC);
            overlay.setRGB(8 , 19, firstC);
            overlay.setRGB(11 , 16, firstC);
            overlay.setRGB(11 , 17, firstC);
            overlay.setRGB(11 , 18, firstC);
            overlay.setRGB(11 , 19, firstC);

            overlay.setRGB(9, 16, firstC);
            overlay.setRGB(10, 16, firstC);
            overlay.setRGB(9, 19, firstC);
            overlay.setRGB(10, 19, firstC);
            //part 2: extending the helmet overlay by 2 pixels to up
            int[] outline = new int[]{overlay.getRGB(27, 8),overlay.getRGB(28, 8)};
            int[] inline = new int[]{overlay.getRGB(27,9 ),overlay.getRGB(28, 9)};
            overlay.setRGB(27, 6, outline[0]);
            overlay.setRGB(28, 6, outline[1]);

            overlay.setRGB(27, 7, inline[0]);
            overlay.setRGB(27, 8, inline[0]);
            overlay.setRGB(28, 7, inline[1]);
            overlay.setRGB(28, 8, inline[1]);

            //part 3: coloring bottom left part of the helmet overlay (from 28, 10 to 28, 12 + 27,12) so it doesn't look weird
            float[] hsb2 = Utils.getHSB(inline[0]);
            int lastC = Utils.getColorFromHSB(hsb2[0], hsb2[1], hsb2[2]-5);
            for (int i = 10; i < 13; i++) {
                overlay.setRGB(28, i, lastC);
            }
            overlay.setRGB(27, 12, lastC);
        }
        return overlay;
    }


}
