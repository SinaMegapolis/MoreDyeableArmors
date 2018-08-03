package sinamegapolis.moredyeablearmors.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.StringUtils;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.util.OverlayTextureAtlasSprite;
import sinamegapolis.moredyeablearmors.util.Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ItemArmorOverlayTextureHandler {
    TextureAtlasSprite helmetOverlay;
    TextureAtlasSprite leggingsOverlay;
    TextureAtlasSprite bootsOverlay;
    private String armorName;
    private IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
    private BufferedImage helmetOverlayImage;
    private BufferedImage leggingsOverlayImage;
    private BufferedImage bootsOverlayImage;
    //don't get confused by these "normal" names, these images will get whitified
    private TextureAtlasSprite helmet;
    private TextureAtlasSprite chestplate;
    private TextureAtlasSprite leggings;
    private TextureAtlasSprite boots;

    private int color;
    {
        try {
            helmetOverlayImage = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation(MoreDyeableArmors.MODID,"textures/items/leather_helmet_overlay.png")).getInputStream());
            leggingsOverlayImage = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation(MoreDyeableArmors.MODID,"textures/items/leather_leggings_overlay.png")).getInputStream());
            bootsOverlayImage = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation(MoreDyeableArmors.MODID,"textures/items/leather_boots_overlay.png")).getInputStream());
        } catch (IOException e) {
            MoreDyeableArmors.LOGGER.error("Item Armor Overlay Texture handler can't initialize itself :ohno:");
            MoreDyeableArmors.LOGGER.error("Report this error to creator (SinaMegapolis) on github https://bit.ly/2muE81e");
            e.printStackTrace();
        }
    }

    public ItemArmorOverlayTextureHandler(int color, TextureMap map, String armorName, String modId){
        this.color=color;
        this.armorName = armorName;
        helmet = new OverlayTextureAtlasSprite(armorName+"_helmet",ModConfig.leathericArmor ? Utils.getImageFromResourceLoc(new ResourceLocation("minecraft","textures/items/leather_helmet.png")) : Utils.whitify(Utils.getImageFromResourceLoc(new ResourceLocation(modId,"textures/items/"+armorName+"_helmet.png"))));
        map.setTextureEntry(helmet);
        chestplate = new OverlayTextureAtlasSprite(armorName+"_chestplate",ModConfig.leathericArmor ? Utils.getImageFromResourceLoc(new ResourceLocation("minecraft","textures/items/leather_chestplate.png")) : Utils.whitify(Utils.getImageFromResourceLoc(new ResourceLocation(modId,"textures/items/"+armorName+"_chestplate.png"))) );
        map.setTextureEntry(chestplate);
        leggings = new OverlayTextureAtlasSprite(armorName+"_leggings",ModConfig.leathericArmor ? Utils.getImageFromResourceLoc(new ResourceLocation("minecraft","textures/items/leather_leggings.png")) : Utils.whitify(Utils.getImageFromResourceLoc(new ResourceLocation(modId,"textures/items/"+armorName+"_leggings.png"))));
        map.setTextureEntry(leggings);
        boots = new OverlayTextureAtlasSprite(armorName+"_boots", ModConfig.leathericArmor ? Utils.getImageFromResourceLoc(new ResourceLocation("minecraft","textures/items/leather_boots.png")) : Utils.whitify(Utils.getImageFromResourceLoc(new ResourceLocation(modId,"textures/items/"+armorName+"_boots.png"))));
        map.setTextureEntry(boots);
        helmetOverlay = new OverlayTextureAtlasSprite(armorName+"_helmet_overlay",Utils.turnIntoGoodTexture(helmetOverlayImage, color));
        map.setTextureEntry(helmetOverlay);
        leggingsOverlay = new OverlayTextureAtlasSprite(armorName+"_leggings_overlay", Utils.turnIntoGoodTexture(leggingsOverlayImage, color));
        map.setTextureEntry(leggingsOverlay);
        bootsOverlay = new OverlayTextureAtlasSprite(armorName+"_boots_overlay", Utils.turnIntoGoodTexture(bootsOverlayImage, color));
        map.setTextureEntry(bootsOverlay);
    }

    public TextureAtlasSprite getBootsOverlay() {
        return bootsOverlay;
    }

    public TextureAtlasSprite getHelmetOverlay() {
        return helmetOverlay;
    }

    public TextureAtlasSprite getLeggingsOverlay() {
        return leggingsOverlay;
    }

    public TextureAtlasSprite getHelmet() {
        return helmet;
    }

    public TextureAtlasSprite getChestplate() {
        return chestplate;
    }

    public TextureAtlasSprite getLeggings() {
        return leggings;
    }

    public TextureAtlasSprite getBoots() {
        return boots;
    }

    public String getArmorName() {
        return armorName;
    }

    public int getColor() {
        return color;
    }
}
