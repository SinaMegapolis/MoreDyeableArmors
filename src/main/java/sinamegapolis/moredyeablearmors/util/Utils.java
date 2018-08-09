package sinamegapolis.moredyeablearmors.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.EnumMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.capability.IDyeable;
import sinamegapolis.moredyeablearmors.config.ModConfig;

public class Utils {
    public static int combineColors(int color1, int color2, int scale) {
        if(scale == 0) {
            return color1;
        }

        int[] argbColor1 = getARGBArrayFromInt(color1);
        int[] argbColor2 = getARGBArrayFromInt(color2);

        for(int i = 0; i < scale; i++) {
            argbColor1[0] = (int) Math.sqrt(argbColor1[0] * argbColor2[0]);
            argbColor1[1] = (int) Math.sqrt(argbColor1[1] * argbColor2[1]);
            argbColor1[2] = (int) Math.sqrt(argbColor1[2] * argbColor2[2]);
            argbColor1[3] = (int) Math.sqrt(argbColor1[3] * argbColor2[3]);
        }
        return getIntFromARGBArray(argbColor1);
    }
    public static int[] getARGBArrayFromInt(int color){
        return new int[]{
                color >> 24 & 0xFF,
                color >> 16 & 0xFF,
                color >> 8 & 0xFF,
                color & 0xFF};
    }
    //TODO: make saturation and brightness value make more sense
    public static float[] getHSB(int color){
        float[] hsb = new float[3];
        int[] argb = getARGBArrayFromInt(color);
        Color.RGBtoHSB(argb[1], argb[2], argb[3], hsb);
        hsb[0] = hsb[0]*360.0f;
        hsb[1] = hsb[1]*100.0f;
        hsb[2] = hsb[2]*100.0f;
        return hsb;
    }

    public static int getColorFromHSB(float h, float s, float b){
        float h1 = h/360.0f;
        float s1 = s/100.0f;
        float b1 = b/100.0f;
        return Color.getHSBColor(h1, s1, b1).getRGB();
    }

    public static int getIntFromARGBArray(int[] argb){
        return argb[0] << 24 | argb[1] << 16 | argb[2] << 8 | argb[3];
    }
    
    static EnumMap<EnumDyeColor, Integer> colors = new EnumMap<>(EnumDyeColor.class);
    static {
		for(EnumDyeColor e : EnumDyeColor.values())
			colors.put(e, ReflectionHelper.getPrivateValue(EnumDyeColor.class, e, "field_193351_w", "colorValue"));
    }
    
    public static int getColorFromColor(EnumDyeColor color) {
    	return colors.get(color);
    }

    public static BufferedImage whitify(BufferedImage coloredImage){
        for (int i = 0; i < coloredImage.getWidth(); i++) {
            for (int j = 0; j < coloredImage.getHeight(); j++) {
                int rgb = coloredImage.getRGB(i, j);
                if(getARGBArrayFromInt(rgb)[0]>0){
                    float[] hsb = getHSB(rgb);
                    coloredImage.setRGB(i, j, getColorFromHSB(hsb[0], 0, hsb[2]));
                }
            }
        }
        return coloredImage;
    }
    public static BufferedImage changeColor(BufferedImage coloredImage,int color){
        for (int i = 0; i < coloredImage.getWidth(); i++) {
            for (int j = 0; j < coloredImage.getHeight(); j++) {
                int rgb = coloredImage.getRGB(i, j);
                if(getARGBArrayFromInt(rgb)[0]>0){
                    float[] hsb = getHSB(rgb);
                    float[] colorHSB = getHSB(color);
                    int[] argb = getARGBArrayFromInt(color);
                    coloredImage.setRGB(i, j, getColorFromHSB(colorHSB[0],argb[1]==argb[2]&&argb[1]==argb[3] ? 0 : hsb[1], hsb[2]+(colorHSB[2]/2f)));
                }
            }
        }
        return coloredImage;
    }

    //used for texture overlay
    //TODO: merge this with whitify
    public static BufferedImage turnIntoGoodTexture(BufferedImage whiteImage, int color){
        for (int i = 0; i < whiteImage.getWidth(); i++) {
            for (int j = 0; j < whiteImage.getHeight(); j++) {
                int rgb = whiteImage.getRGB(i, j);
                int[] argb = getARGBArrayFromInt(rgb);
                if(getARGBArrayFromInt(rgb)[0]>0){
                    float[] hsb = getHSB(rgb);
                    float[] colorhsb = getHSB(color);
                    int goodColor;
                    if(hsb[2]<33) {
                        if (argb[1] == argb[2] && argb[1] == argb[3])
                            goodColor = getColorFromHSB(colorhsb[0], 0, hsb[2]);
                        else goodColor = getColorFromHSB(colorhsb[0], colorhsb[1], hsb[2]);
                    }else{
                        if (argb[1] == argb[2] && argb[1] == argb[3])
                            goodColor = getColorFromHSB(colorhsb[0], 0, getHSB(Utils.combineColors(rgb,color,2))[2]);
                        else goodColor = getColorFromHSB(colorhsb[0], colorhsb[1], getHSB(Utils.combineColors(rgb,color,2))[2]);
                    }
                    whiteImage.setRGB(i, j, goodColor);
                }
            }
        }
        return whiteImage;
    }

    public static BufferedImage turnIntoGoodTexture(BufferedImage whiteImage, BufferedImage whiteImage2, int color){
        for (int i = 0; i < whiteImage2.getWidth(); i++) {
            for (int j = 0; j < whiteImage2.getHeight(); j++) {
                int rgb = whiteImage2.getRGB(i, j);
                int rgb2 = whiteImage.getRGB(i, j);
                int[] argb = getARGBArrayFromInt(color);
                if(getARGBArrayFromInt(rgb)[0]>0){
                    float[] hsb2 = getHSB(rgb2);
                    float[] colorhsb = getHSB(color);
                    int goodColor;
                    if(Math.floor(hsb2[2])<55) {
                        if (argb[1] == argb[2] && argb[1] == argb[3])
                            goodColor = getColorFromHSB(colorhsb[0], 0, hsb2[2]);
                        else goodColor = getColorFromHSB(colorhsb[0], colorhsb[1], hsb2[2]);
                    }else{
                        if (argb[1] == argb[2] && argb[1] == argb[3])
                            goodColor = getColorFromHSB(colorhsb[0], 0, getHSB(Utils.combineColors(rgb2,color,2))[2]);
                        else goodColor = getColorFromHSB(colorhsb[0], colorhsb[1], getHSB(Utils.combineColors(rgb2,color,2))[2]);
                    }
                    whiteImage2.setRGB(i, j, goodColor);
                }
            }
        }
        return whiteImage2;
    }

    public static BufferedImage getImageFromResourceLoc(ResourceLocation imageLocation){
        try {
            return TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(imageLocation).getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * this method is taken from stackoverflow https://bit.ly/2uzFw7j
     * deep copies an BufferedImage
     * @return a deep copy of the given BufferedImage
     *
     * its used in texture stuff
     */
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(0, 0, bi.getWidth(), bi.getHeight());
    }

    /**
     * Erases every pixel of the base image that is not colored in the pattern image
     * @return The result image (returns null if the images don't have the same size)
     */
    public static BufferedImage cutImage(BufferedImage base, BufferedImage pattern){
        if(base.getHeight()!=pattern.getHeight() || base.getWidth()!=pattern.getWidth())
            return null;
        for (int i = 0; i < base.getHeight(); i++) {
            for (int j = 0; j < base.getWidth(); j++) {
                int color = pattern.getRGB(j, i);
                if(!isTransparent(color))
                    base.setRGB(j, i, color);
                else
                    base.setRGB(j, i, getIntFromARGBArray(new int[]{0, 0, 0, 0}));
            }
        }
        return base;
    }

    private static boolean isTransparent(int color){
        return getARGBArrayFromInt(color)[0] == 0;
    }
}
