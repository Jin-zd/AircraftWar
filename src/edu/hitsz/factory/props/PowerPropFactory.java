package edu.hitsz.factory.props;

import edu.hitsz.props.BaseProp;
import edu.hitsz.props.PowerProp;

public class PowerPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        return new PowerProp(x, y, 0, 5);
    }
}
