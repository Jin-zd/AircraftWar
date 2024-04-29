package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShootStrategy implements Strategy {
    private final AbstractAircraft aircraft;

    public ScatterShootStrategy(AbstractAircraft aircraft) {
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
            if (i == 0 || i == aircraft.getShootNum() - 1) {
                y = y - aircraft.getDirection() * 10;
            }
            int v = i * 2 - aircraft.getShootNum() + 1;
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x + v * 10, y, speedX + v, speedY, aircraft.getPower());
            } else {
                bullet = new EnemyBullet(x + v * 10, y, speedX + v, speedY, aircraft.getPower());
            }
            res.add(bullet);
        }
        return res;
    }
}
