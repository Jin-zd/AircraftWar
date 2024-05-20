package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.props.BaseProp;
import edu.hitsz.strategy.StraightShootStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EliteEnemy extends AbstractAircraft {
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootNum;
        this.direction = 1;
        this.power = 10;
        this.changeStrategy(new StraightShootStrategy(this), shootNum);
    }

    @Override
    public List<BaseProp> getProp() {
        List<BaseProp> prop = new ArrayList<>();
        int select = new Random().nextInt();
        BaseProp oneProp = super.getOneProp(select, 0);
        if (oneProp != null) {
            prop.add(oneProp);
        }
        return prop;
    }

    @Override
    public int getScore(){
        return 20;
    }

    @Override
    public List<BaseBullet> shoot() {
        return this.executeStrategy();
    }

    @Override
    public void response() {
        this.vanish();
    }
}
