package edu.hitsz.aircraft;

import edu.hitsz.observer.Responser;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.props.*;
import edu.hitsz.strategy.Context;
import edu.hitsz.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject implements Responser {
    protected int shootNum;
    protected int power;
    protected int direction;

    protected Context context = new Context();

    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        }
    }

    public List<BaseProp> getProp() {
        return new ArrayList<>();
    }

    public int getScore(){
        return 0;
    }


    public int getHp() {
        return hp;
    }

    public BaseProp getOneProp(int selectNum, int count) {
        BaseProp prop = null;
        PropFactory propFactory;
        int X;
        int Y = super.getLocationY();
        if (count == 0) {
            X = super.getLocationX();
        } else if (count == 1) {
            X = super.getLocationX() - 40;
        } else {
            X = super.getLocationX() + 40;
        }
        if (selectNum % 10 == 0 || selectNum % 10 == 1) {
            propFactory = new HpPropFactory();
            prop = propFactory.createProp(X, Y);
        } else if (selectNum % 10 == 2 || selectNum % 10 == 3) {
            propFactory = new PowerPropFactory();
            prop = propFactory.createProp(X, Y);
        } else if (selectNum % 10 == 5 || selectNum % 10 == 6) {
            propFactory = new BombPropFactory();
            prop = propFactory.createProp(X, Y);
        } else if (selectNum % 10 == 7 || selectNum % 10 == 8) {
            propFactory = new SuperPowerPropFactory();
            prop = propFactory.createProp(X, Y);
        }
        return prop;
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     *
     * @return 可射击对象需实现，返回子弹
     * 非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();

    public int getShootNum(){
        return shootNum;
    }

    public int getPower(){
        return power;
    }

    public int getDirection(){
        return direction;
    }

    public void changeStrategy(Strategy strategy, int shootNum){
        this.context.setStrategy(strategy);
        this.shootNum = shootNum;
    }

    public List<BaseBullet> executeStrategy(){
        return context.executeStrategy();
    }

}


