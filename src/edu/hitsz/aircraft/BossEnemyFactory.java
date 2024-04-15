package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createAircraft(int shootNum) {
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                100,
                5,
                0,
                150,
                shootNum
        );
    }
}
