package com.team.building.automation.room;
import java.util.Random;

/**
 * A simulator for environment
 *
 */
public class Environnment {
	public final static Environnment INSTANCE = new Environnment();
	private Random random = new Random();
	private final int lowerBound = 2;
	private final int upperBound = 45;

	private Environnment() {
		// hide constructor from other class to prevent its instantiation
	}
	public int getCurrentTempreature() {
		return lowerBound + random.nextInt(upperBound - lowerBound);
	}
}
