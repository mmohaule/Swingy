package com.mmohaule.swingy.Model;


import java.util.Set;
import java.util.logging.Level;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mmohaule.swingy.Model.artifact.*;

/**
 * GameCharacters
 */
import javax.validation.Validator;

public class GameCharacter {

    private float health;
    private float attack;
    private float defense;
    private float hitPoints;
	private float experience;
	private int id;
    private int level;
    private String heroType;
	private int x, y;
	private Weapon weapon;
	private Helm helm;
	private Armor armor;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    public GameCharacter() {
        
	}
	

	public void validateHero() throws Exception {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<GameCharacter>> constraintViolations = validator.validate(this);
        if (constraintViolations.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Hero validation error(s): ");
            stringBuilder.append(constraintViolations.size());
            stringBuilder.append("\n");
            for (ConstraintViolation<GameCharacter> cv : constraintViolations) {
                stringBuilder.append("property: [");
                stringBuilder.append(cv.getPropertyPath());
                stringBuilder.append("], value: [");
                stringBuilder.append(cv.getInvalidValue());
                stringBuilder.append("], message: [");
                stringBuilder.append(cv.getMessage());
                stringBuilder.append("]\n");
            }
			throw new Exception(stringBuilder.toString());
		}
    }
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the armor
	 */
	public Armor getArmor() {
		return armor;
	}


	/**
	 * @param armor the armor to set
	 */
	public void setArmor(Armor armor) {
		this.armor = armor;
	}


	/**
	 * @return the helm
	 */
	public Helm getHelm() {
		return helm;
	}


	/**
	 * @param helm the helm to set
	 */
	public void setHelm(Helm helm) {
		this.helm = helm;
	}


	/**
	 * @return the weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}


	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}


	/**
	 * @return the defense
	 */
	public float getDefense() {
		return defense;
	}


	/**
	 * @param defense the defense to set
	 */
	public void setDefense(float defense) {
		this.defense = defense;
	}


	private void updateMap() {
        //updateHeroPosition();
    }

    public void setPosition(int x, int y) {
        this.x += x;
        this.y += y;
        updateMap();
    }

    /**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	public String attack(GameCharacter enemy) {

		String message;

		float damageInHP = (this.getLevel() * this.getAttack());

		message = (this.getName() + " attacks " + enemy.getName() + "  with " + damageInHP + " damage");

		enemy.setHitPoints(enemy.getHitPoints() - damageInHP);
		return message;

    }
    /**
	 * @return the heroType
	 */
	public String getHeroType() {
		return heroType;
	}

	/**
	 * @param heroType the heroType to set
	 */
	public void setHeroType(String heroType) {
		this.heroType = heroType;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the experience
	 */
	public float getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(float experience) {
		this.experience = experience;
	}

	/**
	 * @return the hitPoints
	 */
	public float getHitPoints() {
		return hitPoints;
	}

	/**
	 * @param hitPoints the hitPoints to set
	 */
	public void setHitPoints(float hitPoints) {
		this.hitPoints = hitPoints;
	}

	/**
     * @param attack the attack to set
     */
    public void setAttack(float attack) {
        this.attack = attack;
    }

    /**
     * @return the attack
     */
    public float getAttack() {
        return attack;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * @return the health
     */
    public float getHealth() {
        return health;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Class: ").append(heroType).append("\n");
        sb.append("Level: ").append(level).append("\n");
        sb.append("XP: ").append(experience).append("\n");
        sb.append("Attack: ").append(attack).append("\n");
        sb.append("Defense: ").append(defense).append("\n");
        sb.append("HP: ").append(hitPoints).append("\n");

        sb.append("Weapon: ");
        if (weapon != null)
            sb.append(weapon.getName()).append(" (attack +").append(weapon.getPoints()).append(")\n");
        else
            sb.append(" no weapon\n");

        sb.append("Helm: ");
        if (helm != null)
            sb.append(helm.getName()).append(" (hp +").append(helm.getPoints()).append(")\n");
        else
            sb.append(" no helmet\n");

        sb.append("Armor: ");
        if (armor != null)
            sb.append(armor.getName()).append(" (defense +").append(armor.getPoints()).append(")\n");
        else
            sb.append(" no armor\n");
        return sb.toString();
    }
}