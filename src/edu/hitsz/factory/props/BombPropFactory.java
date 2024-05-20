package edu.hitsz.factory.props;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.BombProp;

public class BombPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        return new BombProp(x, y,0, 5);
    }
}
