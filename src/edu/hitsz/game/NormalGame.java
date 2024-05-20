package edu.hitsz.game;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.aircraft.AircraftFactory;
import edu.hitsz.factory.aircraft.BossEnemyFactory;

import java.util.Random;

public class NormalGame extends GameTemplate {


    public NormalGame(){
        super();
        enemyMaxNumber = 7;
        increaseNum = 10;
    }


    @Override
    protected void enemyGenerator() {
        Random r = new Random();
        int selectNum = r.nextInt();
        AircraftFactory aircraftFactory = null;

        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (score % 500 <= 20 && score >= 100 && enemyAircrafts.stream().filter(x -> x instanceof BossEnemy).toList().isEmpty()) {
                aircraftFactory = new BossEnemyFactory();
                enemyAircrafts.add(aircraftFactory.createAircraft(20, bossHp));
                isBoss = true;
                bossBgmOn();
            }
            noBossEnemyGenerator(selectNum, aircraftFactory);
        }
    }

    @Override
    protected void increaseDifficulty() {
        enemyMaxNumber += 1;
        halfNum -= 3;
        mobHp = (int)(mobHp * 1.1);
        eliteHp = (int)(eliteHp * 1.1);
        elitePlusHp = (int)(elitePlusHp * 1.1);
        double eliteProbability = (modNum - halfNum) / (double)modNum;
        rate *= 1.1;
        if (aircraftCycleDuration - 20 > 200) {
            aircraftCycleDuration -= 20;
        } else {
            aircraftCycleDuration = 200;
        }
        double aircraftCycle = (double) aircraftCycleDuration / timeInterval;
        System.out.printf("难度增加！敌机生命累积提升倍率：%.3f；精英机生成概率：%.2f；敌机生成周期：%.2f。\n",rate, eliteProbability, aircraftCycle);
    }
}
