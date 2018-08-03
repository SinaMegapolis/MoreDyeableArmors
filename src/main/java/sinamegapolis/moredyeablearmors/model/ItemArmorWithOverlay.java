package sinamegapolis.moredyeablearmors.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.BakedModelWrapper;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.init.ModRegistry;
import sinamegapolis.moredyeablearmors.texture.ItemArmorOverlayTextureHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArmorWithOverlay extends BakedModelWrapper<IBakedModel> {
    private IBakedModel armor;
    private String armorName;
    private ImmutableList<BakedQuad> gurls = null;
    private EntityEquipmentSlot slot;
    private boolean shouldRenderOverlay = false;
    private ItemArmor.ArmorMaterial armorMaterial;
    public ItemArmorWithOverlay(IBakedModel armor, String armorName, EntityEquipmentSlot slot, ItemArmor.ArmorMaterial armorMaterial){
        super(armor);
        this.armor = armor;
        this.armorName = armorName;
        this.slot = slot;
        this.armorMaterial = armorMaterial;
    }
    public ItemArmorWithOverlay(IBakedModel armor, String armorName, boolean shouldRenderOverlay, EntityEquipmentSlot slot, ItemArmor.ArmorMaterial armorMaterial){
        super(armor);
        this.armor = armor;
        this.armorName = armorName;
        this.shouldRenderOverlay = shouldRenderOverlay;
        this.slot = slot;
        this.armorMaterial = armorMaterial;
    }
    @Override
    public Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return Pair.of( this,  super.handlePerspective(cameraTransformType).getRight());
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if(side==null){
            if(gurls==null){
                ImmutableList.Builder<BakedQuad> gurl = ImmutableList.builder();
                TextureAtlasSprite spriteOverlay = null;
                TextureAtlasSprite spriteNormal = null;
                spriteNormal = setTASesAccordingToSlot(spriteNormal, spriteOverlay)[0];
                spriteOverlay = setTASesAccordingToSlot(spriteNormal, spriteOverlay)[1];
                //TODO: design a way to merge texture atlas sprites with each other
                if(shouldRenderOverlay) {
                    for (BakedQuad quad : armor.getQuads(state, side, rand)) {
                        gurl.add(new BakedQuadRetextured(quad,spriteNormal));
                        if(slot!=EntityEquipmentSlot.CHEST && (slot!=EntityEquipmentSlot.HEAD || armorMaterial!= ItemArmor.ArmorMaterial.CHAIN || ModConfig.leathericArmor)) {
                            gurl.add(new BakedQuadRetextured(new BakedQuad(quad.getVertexData(), 1, quad.getFace(), quad.getSprite(), quad.shouldApplyDiffuseLighting(), quad.getFormat()), spriteOverlay));
                        }
                    }
                }else gurl.addAll(armor.getQuads(state, side, rand));
                gurls = gurl.build();
            }
            return gurls;
        }
        return ImmutableList.of();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return armor.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return armor.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return armor.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return armor.getParticleTexture();
    }

    public String getArmorName() {
        return armorName;
    }

    public IBakedModel getArmor() {
        return armor;
    }

    public EntityEquipmentSlot getSlot() {
        return slot;
    }

    public ItemArmor.ArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return new ItemOverrideList(super.getOverrides().getOverrides()){
            @Override
            public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                if(stack.hasCapability(Capabilities.DYEABLE, null)) {
                    int color = stack.getCapability(Capabilities.DYEABLE, null).getColor();
                    if (color != 0) {
                        ItemArmorWithOverlay overlay = (ItemArmorWithOverlay) originalModel;
                        return new ItemArmorWithOverlay(overlay.getArmor(), overlay.getArmorName(), true, overlay.getSlot(), overlay.getArmorMaterial());
                    }
                }
                return super.handleItemState(originalModel, stack, world, entity);
            }
        };
    }

    private TextureAtlasSprite[] setTASesAccordingToSlot(TextureAtlasSprite normalSprite, TextureAtlasSprite overlaySprite){
        ItemArmorOverlayTextureHandler handler = ModRegistry.getItemTextureHandler(armorName);
        switch (slot){
            case FEET:
                normalSprite = handler.getBoots();
                break;
            case LEGS:
                normalSprite = handler.getLeggings();
                break;
            case CHEST:
                normalSprite = handler.getChestplate();
                break;
            case HEAD:
                normalSprite = handler.getHelmet();
                break;
        }
        if(shouldRenderOverlay){
            switch (slot){
                case HEAD:
                    overlaySprite = handler.getHelmetOverlay();
                    break;
                case LEGS:
                    overlaySprite = handler.getLeggingsOverlay();
                    break;
                case FEET:
                    overlaySprite = handler.getBootsOverlay();
                    break;
            }
        }
        return new TextureAtlasSprite[]{normalSprite, overlaySprite};
    }
}
