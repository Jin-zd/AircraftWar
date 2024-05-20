package edu.hitsz.props;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.Responser;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends BaseProp {
    private boolean bomb;
    private final List<AbstractFlyingObject> responserList;

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.bomb = false;
        this.responserList = new ArrayList<>();
    }

    public void bomb(){
        bomb = true;
    }

    public void registerAllEnemies(List<AbstractAircraft> allEnemies){
        responserList.addAll(allEnemies);
    }

    public void registerAllBullets(List<BaseBullet> allBullets){
        responserList.addAll(allBullets);
    }

    public void notifyResponse(){
        for(Responser responser : responserList){
            responser.response();
        }
    }

    public void checkIsBomb(){
        if (bomb) {
            notifyResponse();
        }
    }

}
