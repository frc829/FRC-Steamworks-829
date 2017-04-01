package org.usfirst.frc.team829.autonomous;

import org.usfirst.frc.team829.robot.Robot;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.NavX;
import org.usfirst.frc.team829.system.Shooter;
import org.usfirst.frc.team829.system.Drive.DIRECTION;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class ShootDriveAuto extends Auto {

	long startTime;
	Alliance alliance;
	DIRECTION direction;
	
	public ShootDriveAuto(Alliance alliance) {
		super("Shoot Drive");
		this.alliance = alliance;
		direction = (alliance.toString().equalsIgnoreCase("red")) ? DIRECTION.RIGHT : DIRECTION.LEFT;
	}
	
	public void execute() {
		
		long now = System.currentTimeMillis();	
		
		System.out.println("Delta Time: " + (now - startTime));
		
		switch(step) {
		case 0:
			startTime = now;
			Drive.precise = 2;
			nextStep();
			break;
		case 1:
			if(now - startTime >= 2000) {
				Drive.setStart();
				Robot.START_ANGLE = NavX.getAngleRotation();
				Drive.precise = 0;
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 2:
			if(Drive.driveToAngle(85, direction)) {
				startTime = now;
				Drive.setStart();
				Robot.START_ANGLE = NavX.getAngleRotation();
				Drive.precise = 1;
				nextStep();
			}
			break;
		case 3:
			if(now - startTime >= 1000) {
				startTime = now;
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 4:
			if(now - startTime >= 2000) {
				startTime = now;
				nextStep();
			} else {
				Drive.setDriveSpeed(0, 0);
			}
			break;
		case 5:
			if(now - startTime >= 1000) {
				startTime = now;
				Drive.setStart();
				Robot.START_ANGLE = NavX.getAngleRotation();
				Drive.precise = 2;
				nextStep();
			} else {
				Drive.driveStraight(-.25, -.25);
			}
			break;
		case 6:
			if(now - startTime >= 1500) {
				startTime = now;
				nextStep();
			} else {
				System.out.println("Turning");
				Drive.turn((direction == DIRECTION.RIGHT) ? DIRECTION.LEFT : DIRECTION.RIGHT, .25);
			}
			break;
		case 7:
			if(now - startTime >= 1000) {
				startTime = now;
				nextStep();
			} else {
				System.out.println("STOP");
				Drive.setDriveSpeed(0, 0);
			}
			break;
		case 8:
			if(Drive.target()) {
				startTime = now;
				nextStep();
			}
			break;
		case 9:
			if(now - startTime >= 500) {
				startTime = now;
				nextStep();
			} else {
				Drive.setDriveSpeed(0, 0);
				Shooter.runShooter();
			}
			break;
		case 10:
			if(startTime - now >= 7000) {
				Shooter.stopCentrifuge();
				Shooter.stopShooter();
				Shooter.stopSingulator();
			} else {
				Shooter.runShooter();
				Shooter.spinCentrifuge();
				Shooter.spinSingulator();
			}
			break;
		}
		
	}
	
}
