package sinamegapolis.moredyeablearmors.util;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * this is wrote by WynPrice and given to me to send halp
 */
public class ImageInputStream implements IResource {

    private final IResource iResource;
    private final InputStream overrideInputStream;

    public ImageInputStream(IResource iResource, BufferedImage image) {
        this.iResource = iResource;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            this.overrideInputStream = new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return this.iResource.getResourceLocation();
    }

    @Override
    public InputStream getInputStream() {
        return this.overrideInputStream;
    }

    @Override
    public boolean hasMetadata() {
        return this.iResource.hasMetadata();
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getMetadata(String sectionName) {
        return this.iResource.getMetadata(sectionName);
    }

    @Override
    public String getResourcePackName() {
        return this.iResource.getResourcePackName();
    }

    @Override
    public void close() throws IOException {
        this.overrideInputStream.close();
    }
}
