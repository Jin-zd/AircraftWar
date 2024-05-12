package edu.hitsz.props;

public class SuperPowerPropFactory implements PropFactory{
    @Override
    public BaseProp createProp(int x, int y) {
        return new SuperPowerProp(x, y, 0, 5, 0);
    }
}
