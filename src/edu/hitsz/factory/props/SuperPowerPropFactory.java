package edu.hitsz.factory.props;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.SuperPowerProp;

public class SuperPowerPropFactory implements PropFactory{
    @Override
    public BaseProp createProp(int x, int y) {
        return new SuperPowerProp(x, y, 0, 5);
    }
}
