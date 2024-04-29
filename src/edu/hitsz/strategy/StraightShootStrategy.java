package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StraightShootStrategy implements  Strategy{
    private final AbstractAircraft aircraft;

    public StraightShootStrategy(AbstractAircraft aircraft){
        this.aircraft = aircraft;
    }

    @Override
    public List<BaseBullet> shootStrategy() {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;
        BaseBullet bullet;
        for (int i = 0; i < aircraft.getShootNum(); i++) {
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x + (i * 2 - aircraft.getShootNum() + 1) * 10, y, speedX, speedY, aircraft.getPower());
            } else {
                bullet = new EnemyBullet(x + (i * 2 - aircraft.getShootNum() + 1) * 10, y, speedX, speedY, aircraft.getPower());
            }
            res.add(bullet);
        }
        return res;
    }
}
