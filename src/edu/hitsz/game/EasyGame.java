package edu.hitsz.game;

import edu.hitsz.factory.aircraft.AircraftFactory;
import edu.hitsz.factory.aircraft.EliteEnemyFactory;
import edu.hitsz.factory.aircraft.MobEnemyFactory;

import java.util.Random;

public class EasyGame extends GameTemplate{

    public EasyGame(){
        super();
        this.enemyMaxNumber = 5;
    }

    @Override
    protected void enemyGenerator() {
        Random r = new Random();
        int selectNum = r.nextInt();
        AircraftFactory aircraftFactory = null;

        noBossEnemyGenerator(selectNum, aircraftFactory);
    }

    @Override
    protected void increaseDifficulty() {

    }
}
