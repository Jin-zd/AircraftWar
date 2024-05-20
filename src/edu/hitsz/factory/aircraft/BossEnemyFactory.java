package edu.hitsz.factory.aircraft;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createAircraft(int shootNum, int hp) {
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                100,
                5,
                0,
                hp,
                shootNum
        );
    }
}
