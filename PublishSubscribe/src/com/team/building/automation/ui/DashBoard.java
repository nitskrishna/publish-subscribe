package com.team.building.automation.ui;

import com.team.building.automation.room.Sensor;
import com.team.building.automation.room.SensorSubscriber;

public class DashBoard implements SensorSubscriber{
	private Sensor sensor;
	private volatile int currentTempreature;
	private Thread mainUIThread;
	public DashBoard(Sensor sensor) {
		this.sensor = sensor;
		mainUIThread = new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("DashBoard is switched on!!");
					sensor.switchOn();
					while(!isInterrupted()) {
						displayCurrentTemp();
						sleep(1000);
					}
				}catch(InterruptedException exp) {
					sensor.switchOff();
					System.out.println("DashBoard is exiting...");
				}
			}
		};
	}
	@Override
	public void notifyForCOV(int presentValue) {
		currentTempreature = presentValue;
	}
	public void init() {
		if(mainUIThread != null && mainUIThread.isAlive()) {
			System.out.println("DashBoard is already started!!");
			return;
		}
		mainUIThread.start();
	}
	public void exit() {
		if(mainUIThread != null && mainUIThread.isAlive()) {
			mainUIThread.interrupt();
			return;
		}
		System.out.println("DashBoard is not started yet");
	}
	private void displayCurrentTemp() {
		System.out.println("Current Temp is "+ currentTempreature);
	}
}
