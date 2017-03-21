package org.usfirst.frc.team829.autonomous;

import org.usfirst.frc.team829.robot.Robot;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.Drive.DIRECTION;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class SideGearAuto extends Auto {

	Alliance alliance;
	boolean calibrate = false;
	long startTime;
	
	public SideGearAuto(Alliance alliance) {
		super("Place Side Gear " + alliance.toString());
		this.alliance = alliance;
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
			if(System.currentTimeMillis() - startTime >= 1900) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 1:
			if(Drive.driveToAngle(30, (alliance.toString().equalsIgnoreCase("blue")) ? DIRECTION.RIGHT : DIRECTION.LEFT)) {
				nextStep();
				Drive.setStart();
				startTime = System.currentTimeMillis();
			}
			break;
		case 2:
			if(System.currentTimeMillis() - startTime >= 1500) {
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
