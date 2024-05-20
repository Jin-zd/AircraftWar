package edu.hitsz.game;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.aircraft.AircraftFactory;
import edu.hitsz.factory.aircraft.BossEnemyFactory;

import java.util.Random;

public class HardGame extends GameTemplate {

    public HardGame() {
        super();
        enemyMaxNumber = 10;
        increaseNum = 7;
    }

    @Override
    protected void enemyGenerator() {
        Random r = new Random();
        int selectNum = r.nextInt();
        AircraftFactory aircraftFactory = null;

        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (score % 300 <= 20 && score >= 100 && enemyAircrafts.stream().filter(x -> x instanceof BossEnemy).toList().isEmpty()) {
                aircraftFactory = new BossEnemyFactory();
                enemyAircrafts.add(aircraftFactory.createAircraft(20, bossHp));
                System.out.println("boss aircraft 生命值：" + bossHp);
                isBoss = true;
                bossBgmOn();
                bossHp += 60;
            }
            noBossEnemyGenerator(selectNum, aircraftFactory);
        }
    }

    @Override
    protected void increaseDifficulty() {
        enemyMaxNumber += 2;
        halfNum -= 5;
        mobHp = (int) (mobHp * 1.5);
        eliteHp = (int) (eliteHp * 1.5);
        elitePlusHp = (int) (elitePlusHp * 1.5);
        double eliteProbability = (modNum - halfNum) / (double) modNum;
        rate *= 1.5;
        if (aircraftCycleDuration - 40 > 200) {
            aircraftCycleDuration -= 40;
        } else {
            aircraftCycleDuration = 200;
        }
        double aircraftCycle = (double) aircraftCycleDuration / timeInterval;
        System.out.printf("难度增加！敌机生命累积提升倍率：%.3f；精英机生成概率：%.2f；敌机生成周期：%.2f。\n", rate, eliteProbability, aircraftCycle);
    }
}
