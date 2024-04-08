package edu.hitsz.props;

public class BombPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        return new BombProp(x, y,0, 5);
    }
}
