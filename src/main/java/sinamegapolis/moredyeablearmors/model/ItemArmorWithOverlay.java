package sinamegapolis.moredyeablearmors.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.BakedModelWrapper;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.init.ModRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArmorWithOverlay extends BakedModelWrapper<IBakedModel> {
    private IBakedModel armor;
    private String armorName;
    private ImmutableList<BakedQuad> gurls = null;
    private ItemStack stack;
    private boolean shouldRenderOverlay = false;
    public ItemArmorWithOverlay(IBakedModel armor, String armorName){
        super(armor);
        this.armor = armor;
        this.armorName = armorName;
    }
    public ItemArmorWithOverlay(IBakedModel armor, String armorName, boolean shouldRenderOverlay){
        super(armor);
        this.armor = armor;
        this.armorName = armorName;
        this.shouldRenderOverlay = shouldRenderOverlay;
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
                TextureAtlasSprite sprite = ModRegistry.getItemTextureHandler(armorName).getBootsOverlay();
                //TODO: design a way to merge texture atlas sprites with each other
                gurl.addAll(armor.getQuads(state, side, rand));
                if(shouldRenderOverlay) {
                    for (BakedQuad quad : armor.getQuads(state, side, rand)) {
                        gurl.add(new BakedQuadRetextured(quad, sprite));
                    }
                }
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

    @Override
    public ItemOverrideList getOverrides() {
        return new ItemOverrideList(super.getOverrides().getOverrides()){
            @Override
            public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                if(stack.getCapability(Capabilities.DYEABLE, null).getColor()!=250) {
                    return new ItemArmorWithOverlay(((ItemArmorWithOverlay)originalModel).getArmor(),((ItemArmorWithOverlay)originalModel).getArmorName(),true);
                }
                return super.handleItemState(originalModel, stack, world, entity);
            }
        };
    }
}
