package edu.hitsz.Thread;

import edu.hitsz.application.Game;

public class BgmThread extends MusicThread{
    private final Game game;

    public BgmThread(String filename, Game game) {
        super(filename);
        this.game = game;
    }

    @Override
    public void run() {
        while(!game.getGameOverFlag()) {
            super.run();
        }
    }
}
