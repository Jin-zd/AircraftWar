package edu.hitsz.factory.props;

import edu.hitsz.props.BaseProp;

public interface PropFactory {
    BaseProp createProp(int x, int y);
}
