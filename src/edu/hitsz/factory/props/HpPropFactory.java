package edu.hitsz.factory.props;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.HpProp;

public class HpPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        return new HpProp(x, y, 0, 5, 30);
    }
}
