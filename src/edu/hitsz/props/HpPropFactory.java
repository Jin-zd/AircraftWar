package edu.hitsz.props;

public class HpPropFactory implements PropFactory {
    @Override
    public BaseProp createProp(int x, int y) {
        return new HpProp(x, y, 0, 5, 30);
    }
}
