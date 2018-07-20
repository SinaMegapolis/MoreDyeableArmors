package sinamegapolis.moredyeablearmors.capability;

public class DyeableCapability implements IDyeable{

    private int color = 250;
    private int defaultColro;
    public DyeableCapability(int defaultColor){
        //empty constructor because why not
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }
}
