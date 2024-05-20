package edu.hitsz.factory.aircraft;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory implements AircraftFactory {
    @Override
    public AbstractAircraft createAircraft(int shootNum, int hp) {
        if (shootNum == 1) {
            return new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    5,
                    5,
                    hp,
                    shootNum
            );
        } else {
            return new ElitePlusEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    5,
                    5,
                    hp,
                    shootNum
            );
        }


    }
}
