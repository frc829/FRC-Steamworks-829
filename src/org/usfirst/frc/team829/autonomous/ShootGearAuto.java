package org.usfirst.frc.team829.autonomous;

import org.usfirst.frc.team829.robot.Robot;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.Shooter;
import org.usfirst.frc.team829.system.Drive.DIRECTION;
import org.usfirst.frc.team829.system.NavX;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class ShootGearAuto extends Auto {

	long startTime;
	Alliance alliance;
	DIRECTION direction;
	
	public ShootGearAuto(Alliance alliance) {
		super("Shoot Gear " + alliance.toString());
		this.alliance = alliance;
		direction = (alliance.toString().equalsIgnoreCase("red")) ? DIRECTION.LEFT : DIRECTION.RIGHT;
	}
	
	public void execute() {
		
		long now = System.currentTimeMillis();
		
		switch(step) {
		case 0:
			Drive.precise = 2;
			startTime = now;
			Drive.setStart();
			nextStep();
			break;
		case 1:
			if(now - startTime >= 2500) {
				startTime = now;
				Drive.setStart();
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 2:
			if(Drive.driveToAngle(30, direction)) {
				startTime = now;
				nextStep();
			}
			break;
		case 3:
			if(now - startTime >= 2000) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 4:
			Robot.placeGearAuto();
			Drive.setStart();
			Robot.START_ANGLE = NavX.getAngleRotation();
			nextStep();
			break;
		case 5:
			if(Drive.driveToAngle(1, (direction == DIRECTION.RIGHT) ? DIRECTION.LEFT : DIRECTION.RIGHT, true)) {
				startTime = now;
				nextStep();
			}
			break;
		case 6:
			if(now - startTime >= 1000) {
				startTime = now;
				nextStep();
			} else {
				Drive.driveStraight(-.25, -.25);
			}
			nextStep();
			break;
		case 7:
			if(now - startTime >= 1000) {
				startTime = now;
				nextStep();
			} else {
				Drive.setDriveSpeed(0, 0);
				Shooter.runShooter();
			}
			break;
		case 8:
			if(now - startTime >= 7000) {
				startTime = now;
				nextStep();
			} else {
				Shooter.runShooter();
				Shooter.spinCentrifuge();
				Shooter.spinSingulator();
			}
			break;
		case 9:
			Shooter.stopCentrifuge();
			Shooter.stopShooter();
			Shooter.stopSingulator();
			break;
		}
		
	}
	
}
