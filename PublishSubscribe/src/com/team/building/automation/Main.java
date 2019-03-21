package com.team.building.automation;

import com.team.building.automation.room.Sensor;
import com.team.building.automation.ui.DashBoard;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Sensor sensor = new Sensor();
		DashBoard dashBoard = new DashBoard(sensor);
		sensor.subscribeToCOV(dashBoard);
		dashBoard.init();
		Thread.sleep(10000);
		dashBoard.exit();
	}
}
