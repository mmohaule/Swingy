package com.mmohaule.swingy.Model;

/**
 * Ninja
 */
public class Ninja extends GameCharacter {

    public Ninja(String name) {
        this.setAttack(40.0f);
        this.setDefense(10.0f);
        this.setHealth(90.0f);
        this.setLevel(1);
        this.setHitPoints(90.0f);
        this.setExperience(0.0f);
        this.setHeroType("Ninja");
        this.setName(name);
    }
}