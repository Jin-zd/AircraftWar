package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.props.BaseProp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ElitePlusEnemy extends AbstractAircraft {

    private int shootNum;

    private int power = 10;

    private int direction = 1;


    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootNum;
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
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 3;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            if (i == 0 || i == shootNum - 1) {
                y = y - direction * 10;
            }
            bullet = new EnemyBullet(x + (i * 4 - shootNum + 1) * 10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
