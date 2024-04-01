package edu.hitsz.props;

public class HpProp extends BaseProp{

    private final int hpRecover;

    public HpProp(int locationX, int locationY, int speedX, int speedY, int hpRecover) {
        super(locationX, locationY, speedX, speedY);
        this.hpRecover = hpRecover;
    }

    public int getHpRecover() {
        return hpRecover;
    }
}
