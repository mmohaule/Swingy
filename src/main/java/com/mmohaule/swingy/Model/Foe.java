package com.mmohaule.swingy.Model;

/**
 * Foe
 */
public class Foe extends GameCharacter{

    public Foe() {
        this.setAttack(20.0f);
        this.setDefense(20.0f);
        this.setHealth(10.0f);
        this.setLevel(1);
        this.setHitPoints(50.0f);
        this.setExperience(0.0f);
        this.setName("Enemy");
        this.setHeroType("Foe");
    }
    
}