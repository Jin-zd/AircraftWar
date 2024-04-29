package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class RingShootStrategy implements Strategy {
    private final AbstractAircraft aircraft;

    public RingShootStrategy(AbstractAircraft aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public List<BaseBullet> shootStrategy() {
    List<BaseBullet> res = new LinkedList<>();
    int x = aircraft.getLocationX();
    int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
    int speedX = Math.abs(aircraft.getDirection() * 5);
    int speedY = Math.abs(aircraft.getSpeedY() * aircraft.getDirection() * 2);
    BaseBullet bullet;
    double angleStep = 2 * Math.PI / aircraft.getShootNum();
    for (int i = 0; i < aircraft.getShootNum(); i++) {
        double angle = i * angleStep;
        int bulletX = (int) (Math.cos(angle) * 100) + x;
        int bulletY = (int) (Math.sin(angle) * 100) + y;
        double bulletSpeedX = Math.cos(angle) * speedX - Math.sin(angle) * speedY;
        double bulletSpeedY = Math.sin(angle) * speedX + Math.cos(angle) * speedY;
        if (aircraft instanceof HeroAircraft) {
            bullet = new HeroBullet(bulletX, bulletY, (int) bulletSpeedX, (int) bulletSpeedY, aircraft.getPower());
        } else {
            bullet = new EnemyBullet(bulletX, bulletY, (int) bulletSpeedX, (int) bulletSpeedY, aircraft.getPower());
        }
        res.add(bullet);
    }
    return res;
    }
}
