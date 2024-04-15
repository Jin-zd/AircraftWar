package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class HeroAircraftTest {
    private HeroAircraft heroAircraft;

    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- HeroAircraft Function Test ---**");
    }

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @Test
    void getHeroAircraft() {
        HeroAircraft hero1 = HeroAircraft.getHeroAircraft();
        assertNotNull(hero1);
        assertInstanceOf(HeroAircraft.class, hero1);
        HeroAircraft hero2 = HeroAircraft.getHeroAircraft();
        assertEquals(hero1, hero2);
    }

    @Test
    void shoot() {
        List<BaseBullet> bullets = heroAircraft.shoot();
        assertNotNull(bullets);
        assertEquals(1, bullets.size());
        BaseBullet bullet = bullets.getFirst();
        assertInstanceOf(HeroBullet.class, bullet);
    }

    @Test
    void increaseHp() {
        heroAircraft.increaseHp(10);
        assertEquals(1000, heroAircraft.getHp());
        heroAircraft.decreaseHp(80);
        heroAircraft.increaseHp(50);
        assertEquals(970, heroAircraft.getHp());
    }
}