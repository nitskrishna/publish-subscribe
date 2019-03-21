package com.team.building.automation.room;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
	private volatile int presentValue;
	private Thread sensorThread;
	private List<SensorSubscriber> sensorSubscribers;


	public Sensor() {
		sensorSubscribers = new ArrayList<>();
		sensorThread = new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("Sensor is switched on!!");
					while (!isInterrupted()) {
						senseEnvTemp();
						sleep(2000);
					}
				} catch (InterruptedException e) {
					System.out.println("Sensor has been switched-off");
				}
			}
		};
	}
	public int getPresentValue() {
		return presentValue;
	}
	public void switchOn() {
		if(sensorThread != null && sensorThread.isAlive()) {
			System.out.println("Sensor is already switched on");
			return;
		}
		sensorThread.start();
	}
	
	public void switchOff() {
		if(sensorThread != null && sensorThread.isAlive()) {
			sensorThread.interrupt();
			return;
		}
		System.out.println("Sensor is already switched off");
	}

	private void senseEnvTemp() {
		Environnment env = Environnment.INSTANCE;
		int oldTempreature = presentValue;
		int newTempreature = env.getCurrentTempreature();

		this.presentValue = newTempreature;
		if (oldTempreature != newTempreature) {
			notifyForCOV();
		}
	}
	public void subscribeToCOV(SensorSubscriber sensorSubscriber) {
		sensorSubscribers.add(sensorSubscriber);
	}
	public void removeCOVSubscriver(SensorSubscriber sensorSubscriber) {
		sensorSubscribers.remove(sensorSubscriber);
	}
	private void notifyForCOV() {
		for(SensorSubscriber subscriber : sensorSubscribers) {
			subscriber.notifyForCOV(presentValue);
		}
	}
}
