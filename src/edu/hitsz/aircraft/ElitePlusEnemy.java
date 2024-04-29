package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.props.BaseProp;
import edu.hitsz.strategy.Context;
import edu.hitsz.strategy.ScatterShootStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ElitePlusEnemy extends AbstractAircraft {

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootNum;
        this.direction = 1;
        this.power = 10;
        this.changeStrategy(new ScatterShootStrategy(this), shootNum);
    }

    public List<BaseProp> getProp() {
        List<BaseProp> prop = new ArrayList<>();
        Random r = new Random();
        int select = r.nextInt();
        BaseProp oneProp = super.getOneProp(select, 0);
        if (oneProp != null) {
            prop.add(oneProp);
        }
        return prop;
    }

    @Override
    public int getScore() {
        return 30;
    }

    @Override
    public List<BaseBullet> shoot() {
        return this.executeStrategy();
    }
}
