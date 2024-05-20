package edu.hitsz.thread;

import edu.hitsz.game.GameTemplate;

public class BossBgmThread extends MusicThread{
    private final GameTemplate game;

    public BossBgmThread(String filename, GameTemplate game) {
        super(filename);
        this.game = game;
    }

    @Override
    public void run() {
        while(game.getIsBoss() && !game.getGameOverFlag()){
            super.run();
        }
    }
}
