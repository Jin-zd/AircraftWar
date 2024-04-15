package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory implements AircraftFactory {
    @Override
    public AbstractAircraft createAircraft(int shootNum) {
        if (shootNum == 1) {
            return new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    5,
                    5,
                    60,
                    shootNum
            );
        } else {
            return new ElitePlusEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    5,
                    5,
                    100,
                    shootNum
            );
        }


    }
}
