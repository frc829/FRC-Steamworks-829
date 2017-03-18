package org.usfirst.frc.team829.autonomous;

import org.usfirst.frc.team829.robot.Variables;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.Drive.DIRECTION;
import org.usfirst.frc.team829.system.Shooter;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class ShootAuto extends Auto {

	boolean calibrate = false;
	long startTime;
	Alliance alliance;
	
	public ShootAuto(Alliance alliance) {
		super("Shoot " + alliance.toString());
		this.alliance = alliance;
	}
	
	public void execute() {
		
		System.out.println(name);
		double dist = Variables.UNITS_PER_FEET;
		
		switch(step) {
		case 0:
			Drive.precise = false;
			if(!calibrate) {
				Drive.setStart();
				calibrate = true;
				startTime = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - startTime >= 2829) {
				nextStep();
				startTime = System.currentTimeMillis();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 1:
			if(Drive.driveToAngle(90, (alliance.toString().equalsIgnoreCase("blue") ? DIRECTION.RIGHT : DIRECTION.LEFT))) {
				nextStep();
				startTime = System.currentTimeMillis();
				Drive.setStart();
			}
			break;
		case 2:
			if(System.currentTimeMillis() - startTime >= 500) {
				nextStep();
				startTime = System.currentTimeMillis();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 3:
			if(System.currentTimeMillis() - startTime >= 750) {
				nextStep();
				startTime = System.currentTimeMillis();
			} else {
				Drive.setDriveSpeed(0, 0);
				Shooter.runShooter();
			}
			break;
		case 4:
			if(Drive.driveToAngle(120, (alliance.toString().equalsIgnoreCase("blue") ? DIRECTION.LEFT : DIRECTION.RIGHT))) {
				nextStep();
				startTime = System.currentTimeMillis();
			}
			break;
		case 5:
			if(System.currentTimeMillis() - startTime >= 4000) {
				nextStep();
			} else {
				Shooter.runShooter();
				Shooter.spinCentrifuge();
				Shooter.spinSingulator();
			}
			break;
		case 6:
			Shooter.stopCentrifuge();
			Shooter.stopShooter();
			Shooter.stopSingulator();
			Drive.setDriveSpeed(0, 0);
			break;
		}
		
	}
	
}
