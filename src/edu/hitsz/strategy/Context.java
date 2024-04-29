package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class Context {
    private Strategy strategy;

    public Context() {
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy() {
        return strategy.shootStrategy();
    }
}
