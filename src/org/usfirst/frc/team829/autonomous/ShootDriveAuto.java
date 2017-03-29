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
		
		switch(step) {
		case 0:
			startTime = now;
			nextStep();
			break;
		case 1:
			if(now - startTime >= 1000) {
				startTime = now;
				nextStep();
			} else {
				Shooter.runShooter();
			}
			break;
		case 2:
			if(now - startTime >= 7000) {
				startTime = now;
				nextStep();
			} else {
				Shooter.runShooter();
				Shooter.spinCentrifuge();
				Shooter.spinSingulator();
			}
			break;
		case 3:
			Shooter.stopCentrifuge();
			Shooter.stopShooter();
			Shooter.stopSingulator();
			startTime = now;
			Drive.setStart();
			Drive.precise = 1;
			nextStep();
			break;
		case 4:
			if(Drive.driveToAngle(70, direction)) {
				Drive.setStart();
				startTime = now;
				Drive.precise = 2;
				Robot.START_ANGLE = NavX.getAngleRotation();
				nextStep();
			}
			break;
		case 5:
			if(now - startTime >= 3500) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 6:
			Drive.setDriveSpeed(0, 0);
			break;
		}
		
	}
	
}
