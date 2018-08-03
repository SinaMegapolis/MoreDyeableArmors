package sinamegapolis.moredyeablearmors.util;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class OverlayTextureAtlasSprite extends TextureAtlasSprite {
    private BufferedImage image;
    private boolean isArmorTexture=false;
    private String modId;
    protected OverlayTextureAtlasSprite(String spriteName) {
        super(spriteName);
    }

    public OverlayTextureAtlasSprite(String spriteName, BufferedImage image) {
        super(spriteName);
        this.image = image;
    }

    public OverlayTextureAtlasSprite(String spriteName, BufferedImage image, boolean isArmorTexture, String modId) {
        super(spriteName);
        this.image = image;
        this.isArmorTexture = isArmorTexture;
        this.modId = modId;
    }

    @Override
    public void loadSpriteFrames(IResource resource, int mipmaplevels) throws IOException {
        super.loadSpriteFrames(new ImageInputStream(resource,image), mipmaplevels);
    }

    @Override
    public String getIconName() {
        ResourceLocation loc = new ResourceLocation(super.getIconName());
        if(isArmorTexture)
            return  modId + ":armor/" + loc.getResourcePath();
        return MoreDyeableArmors.MODID + ":" + "items/" + loc.getResourcePath();
    }
}
