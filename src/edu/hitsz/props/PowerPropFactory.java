package edu.hitsz.props;

public class PowerPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        return new PowerProp(x, y, 0, 5, 20);
    }
}
