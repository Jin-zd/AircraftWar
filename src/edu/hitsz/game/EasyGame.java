package edu.hitsz.game;

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

        noBossEnemyGenerator(selectNum);
    }

    @Override
    protected void increaseDifficulty() {

    }
}
