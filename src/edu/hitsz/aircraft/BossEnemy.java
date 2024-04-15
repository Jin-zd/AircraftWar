package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.props.BaseProp;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BossEnemy extends AbstractAircraft {
    private final int shootNum;

    private int power = 10;

    private int direction = 1;


    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootNum;
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
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 3;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            bullet = new EnemyBullet((int) (Math.cos(2 * Math.PI * i / 20) * 150 + x), (int) (Math.sin(2 * Math.PI * i / 20) * 150 + y), speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
