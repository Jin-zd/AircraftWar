package edu.hitsz.thread;

import edu.hitsz.game.GameTemplate;

public class BgmThread extends MusicThread{
    private final GameTemplate game;

    public BgmThread(String filename, GameTemplate game) {
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
