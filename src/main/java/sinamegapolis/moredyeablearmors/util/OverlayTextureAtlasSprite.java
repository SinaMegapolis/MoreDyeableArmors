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
    protected OverlayTextureAtlasSprite(String spriteName) {
        super(spriteName);
    }

    public OverlayTextureAtlasSprite(String spriteName, BufferedImage image) {
        super(spriteName);
        this.image = image;
    }

    @Override
    public void loadSpriteFrames(IResource resource, int mipmaplevels) throws IOException {
        super.loadSpriteFrames(new ImageInputStream(resource,image), mipmaplevels);
    }

    @Override
    public String getIconName() {
        ResourceLocation loc = new ResourceLocation(super.getIconName());
        return MoreDyeableArmors.MODID + ":" + "items/" + loc.getResourcePath();
    }
}
