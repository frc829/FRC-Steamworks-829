package org.usfirst.frc.team829.autonomous;

import org.usfirst.frc.team829.robot.Robot;
import org.usfirst.frc.team829.robot.Variables;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.Drive.DIRECTION;

public class SideGearAuto extends Auto {

	boolean calibrate = false;
	long startTime;
	
	public SideGearAuto() {
		super("Place Side Gear");
	}
	
	public void execute() {
		
		System.out.println(name);
		
		switch(step) {
		case 0:
			Drive.precise = false;
			if(!calibrate) {
				Drive.setStart();
				calibrate = true;
				startTime = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - startTime >= 3000) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 1:
			if(Drive.driveToAngle(30, DIRECTION.RIGHT)) {
				nextStep();
				Drive.setStart();
				startTime = System.currentTimeMillis();
			}
			break;
		case 2:
			if(System.currentTimeMillis() - startTime >= 3000) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 3:
			Drive.setDriveSpeed(0, 0);
			System.out.println("At position");
			Robot.placeGear();
			nextStep();
			break;
		}
		
	}
	
}
