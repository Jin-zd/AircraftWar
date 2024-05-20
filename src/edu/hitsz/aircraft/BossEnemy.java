package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.props.BaseProp;
import edu.hitsz.strategy.RingShootStrategy;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BossEnemy extends AbstractAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootNum;
        this.direction = 1;
        this.power = 10;
        this.changeStrategy(new RingShootStrategy(this), shootNum);
    }

    public List<BaseProp> getProp() {
        List<BaseProp> prop = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < 3; i++) {
            Random r = new Random();
            int select = r.nextInt();
            BaseProp oneProp = super.getOneProp(select, count);
            if (oneProp != null) {
                prop.add(oneProp);
                count += 1;
            }
        }
        return prop;
    }

    @Override
    public int getScore() {
        return 50;
    }

    @Override
    public List<BaseBullet> shoot() {
        return  this.executeStrategy();
    }

}
