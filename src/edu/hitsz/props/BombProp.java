package edu.hitsz.props;

public class BombProp extends BaseProp {
    private boolean bomb;

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.bomb = false;
    }

    public void Bomb(){
        bomb = true;
    }

    public boolean isBomb(){
        return bomb;
    }
}
