package edu.hitsz.game;

import GUI.CardLayoutGUI;
import GUI.EndMenu;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.aircraft.AircraftFactory;
import edu.hitsz.factory.aircraft.BossEnemyFactory;
import edu.hitsz.factory.aircraft.EliteEnemyFactory;
import edu.hitsz.factory.aircraft.MobEnemyFactory;
import edu.hitsz.props.*;
import edu.hitsz.strategy.RingShootStrategy;
import edu.hitsz.strategy.ScatterShootStrategy;
import edu.hitsz.strategy.StraightShootStrategy;
import edu.hitsz.thread.BgmThread;
import edu.hitsz.thread.BossBgmThread;
import edu.hitsz.thread.MusicThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class GameTemplate extends JPanel {
    private int backGroundTop = 0;

    public static String gameModel = "Easy";
    public static boolean bgmOn = true;

    protected BgmThread bgm = null;
    protected BossBgmThread bossBgm = null;

    protected int modNum;
    protected int bossHp;
    protected int eliteHp;
    protected int elitePlusHp;
    protected int mobHp;

    protected int halfNum;
    protected double rate;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    protected final HeroAircraft heroAircraft;
    protected final List<AbstractAircraft> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<BaseProp> props;


    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    protected int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    protected int cycleTime = 0;
    protected int aircraftCycleDuration = 800;
    protected int aircraftCycleTime = 0;
    protected int shootCycleDuration = 600;
    protected int shootCycleTime = 0;
    protected int cycleCount = 0;
    protected int increaseNum = 5;

    /**
     * 游戏结束标志
     */
    protected boolean gameOverFlag = false;
    protected boolean isBoss = false;

    public boolean getGameOverFlag() {
        return gameOverFlag;
    }

    public boolean getIsBoss() {
        return isBoss;
    }

    protected abstract void enemyGenerator();

    protected abstract void increaseDifficulty();

    protected void noBossEnemyGenerator(int selectNum, AircraftFactory aircraftFactory) {
        if (selectNum % modNum <= halfNum) {
            aircraftFactory = new MobEnemyFactory();
            enemyAircrafts.add(aircraftFactory.createAircraft(0, mobHp));
        } else if (selectNum % modNum > halfNum && selectNum % modNum < modNum - (modNum - halfNum) / 2) {
            aircraftFactory = new EliteEnemyFactory();
            enemyAircrafts.add(aircraftFactory.createAircraft(3, elitePlusHp));
        } else {
            aircraftFactory = new EliteEnemyFactory();
            enemyAircrafts.add(aircraftFactory.createAircraft(1, eliteHp));
        }
    }

    public GameTemplate() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        bossHp = 300;
        eliteHp = 45;
        elitePlusHp = 80;
        mobHp = 25;

        modNum = 1000;
        halfNum = modNum / 2;
        rate = 1;

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    private class WarningPanel extends JPanel {
        private JLabel warningLabel;
        private boolean isVisible = false;

        public WarningPanel() {
            setPreferredSize(new Dimension(800, 50));
            setBackground(Color.RED);
            warningLabel = new JLabel("BOSS IS COMING!", JLabel.CENTER);
            warningLabel.setForeground(Color.WHITE);
            add(warningLabel);
        }

        public void toggleVisibility() {
            isVisible = !isVisible;
            setVisible(isVisible);
        }
    }

    protected void showBossWarning() {

        try {
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void bossBgmOn() {
        if (bgmOn && !gameOverFlag && Objects.isNull(bossBgm)) {
            bossBgm = new BossBgmThread("src/videos/bgm_boss.wav", this);
            bossBgm.start();
        }
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;


            // 周期性执行（控制频率）
            if (aircraftTimeCountAndNewCycleJudge()) {
                // 新敌机产生
                enemyGenerator();
            }

            if (shootTimeCountAndNewCycleJudge()) {
                // 飞机射出子弹
                shootAction();
            }

            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                cycleCount += 1;
                if (cycleCount != 0 && cycleCount % increaseNum == 0) {
                    increaseDifficulty();
                }
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            propActivate();

            checkAircraftIsValid();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            if (bgmOn && !gameOverFlag && Objects.isNull(bgm)) {
                bgm = new BgmThread("src/videos/bgm.wav", this);
                bgm.start();
            }

            if (!Objects.isNull(bossBgm) && !isBoss) {
                bossBgm.closeMusic();
                bossBgm = null;
            }

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                gameOverFlag = true;
                if (bgmOn) {
                    new MusicThread("src/videos/game_over.wav").start();
                    bgm.closeMusic();
                    if (!Objects.isNull(bossBgm)) {
                        bossBgm.closeMusic();
                    }
                }
                // 游戏结束
                try {
                    EndMenu endMenuBottom = new EndMenu(score, true);
                    CardLayoutGUI.cardPanel.add(endMenuBottom.getMainPanel());
                    CardLayoutGUI.cardLayout.next(CardLayoutGUI.cardPanel);

                    EndMenu endMenuTop = new EndMenu(score, false);
                    CardLayoutGUI.cardPanel.add(endMenuTop.getMainPanel());
                    CardLayoutGUI.cardLayout.last(CardLayoutGUI.cardPanel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executorService.shutdown();

                System.out.println("Game Over!");

            }


        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean aircraftTimeCountAndNewCycleJudge() {
        aircraftCycleTime += timeInterval;
        if (aircraftCycleTime >= aircraftCycleDuration && aircraftCycleTime - timeInterval < aircraftCycleTime) {
            // 跨越到新的周期
            aircraftCycleTime %= aircraftCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean shootTimeCountAndNewCycleJudge() {
        shootCycleTime += timeInterval;
        if (shootCycleTime >= shootCycleDuration && shootCycleTime - timeInterval < shootCycleTime) {
            // 跨越到新的周期
            shootCycleTime %= shootCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        /**
         * 周期（ms)
         * 指示子弹的发射、敌机的产生频率
         */
        int cycleDuration = 3000;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
//        if (bgmOn) {
//            new MusicThread("src/videos/bullet.wav").start();
//        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
        for (BaseProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    if (bgmOn) {
                        new MusicThread("src/videos/bullet_hit.wav").start();
                    }
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();

                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }
    }

    private void checkAircraftIsValid() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.notValid()) {
                score += enemyAircraft.getScore();
                if (enemyAircraft instanceof BossEnemy && !Objects.isNull(bossBgm)) {
                    bossBgm.closeMusic();
                    bossBgm = null;
                }
                List<BaseProp> prop = enemyAircraft.getProp();
                if (!prop.isEmpty()) {
                    props.addAll(prop);
                }
            }
        }
    }

    private void propActivate() {
        for (BaseProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                if (bgmOn) {
                    new MusicThread("src/videos/get_supply.wav").start();
                }
                switch (prop) {
                    case HpProp hpProp -> {
                        System.out.println("Hp Prop Activates!");
                        heroAircraft.increaseHp(hpProp.getHpRecover());
                    }
                    case SuperPowerProp superPowerProp -> {
                        System.out.println("Super Power Prop Activates!");
                        Runnable superPropThread = () -> {
                            try {
                                heroAircraft.changeStrategy(new RingShootStrategy(heroAircraft), 20);
                                Thread.sleep(10000);
                                heroAircraft.changeStrategy(new StraightShootStrategy(heroAircraft), 1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        };
                        new Thread(superPropThread).start();
                    }
                    case PowerProp powerProp -> {
                        System.out.println("Power Prop Activates!");
                        Runnable powerPropThread = () -> {
                            try {
                                heroAircraft.changeStrategy(new ScatterShootStrategy(heroAircraft), 3);
                                Thread.sleep(10000);
                                heroAircraft.changeStrategy(new StraightShootStrategy(heroAircraft), 1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        };
                        new Thread(powerPropThread).start();
                    }
                    case BombProp bombProp -> {
                        if (bgmOn) {
                            new MusicThread("src/videos/bomb_explosion.wav").start();
                        }
                        bombProp.registerAllEnemies(enemyAircrafts);
                        bombProp.registerAllBullets(enemyBullets);
                        bombProp.bomb();
                        bombProp.checkIsBomb();
                        System.out.println("Bomb Prop Activates!");
                    }
                    default -> {
                    }
                }
                prop.vanish();
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractAircraft::getIsOutOfBounds);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, props);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);

        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
}
