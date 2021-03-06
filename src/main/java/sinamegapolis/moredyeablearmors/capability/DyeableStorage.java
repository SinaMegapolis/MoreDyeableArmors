package sinamegapolis.moredyeablearmors.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DyeableStorage implements Capability.IStorage<IDyeable> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IDyeable> capability, IDyeable instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("color", instance.getColor());
        compound.setBoolean("canGetDyed", instance.isDyeable());
        compound.setBoolean("isRainbow", instance.isRainbow());
        return compound;
    }

    @Override
    public void readNBT(Capability<IDyeable> capability, IDyeable instance, EnumFacing side, NBTBase nbt) {
        instance.setColor(((NBTTagCompound)nbt).getInteger("color"));
        instance.setDyeable(((NBTTagCompound)nbt).getBoolean("canGetDyed"));
        instance.setRainbow(((NBTTagCompound)nbt).getBoolean("isRainbow"));
    }
}
