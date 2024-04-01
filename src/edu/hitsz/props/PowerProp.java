package edu.hitsz.props;

public class PowerProp extends BaseProp {
    private final int powerUp;

    public PowerProp(int locationX, int locationY, int speedX, int speedY, int powerUp) {
        super(locationX, locationY, speedX, speedY);
        this.powerUp = powerUp;
    }

    public int getPowerUp() {
        return powerUp;
    }
}
