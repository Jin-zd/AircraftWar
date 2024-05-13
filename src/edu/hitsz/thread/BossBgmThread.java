package edu.hitsz.thread;

import edu.hitsz.application.Game;

public class BossBgmThread extends MusicThread{
    private final Game game;

    public BossBgmThread(String filename, Game game) {
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
