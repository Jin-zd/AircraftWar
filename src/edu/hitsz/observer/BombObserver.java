package edu.hitsz.observer;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.props.BombProp;

import java.util.ArrayList;
import java.util.List;

public class BombObserver {
    private final List<Responser> responserList;

    public BombObserver(){
        responserList = new ArrayList<>();
    }

    public void registerResponser(Responser responser){
        responserList.add(responser);
    }

    public void registerAllBullet(List<BaseBullet> allBullets){
        responserList.addAll(allBullets);
    }

    public void removeResponser(Responser responser){
        responserList.remove(responser);
    }

    public void notifyResponsers(){
        for(Responser responser : responserList){
            responser.response();
        }
    }
    
    public void checkIsBomb(BombProp bombProp){
        if (bombProp.isBomb()) {
            notifyResponsers();
        }
    }
}
