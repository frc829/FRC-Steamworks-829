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
		
		System.out.println("Step: " + step);
		switch(step) {
		case 0:
			System.out.println("Drive straight");
			Drive.precise = false;
			if(!calibrate) {
				Drive.setStart();
				calibrate = true;
				startTime = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - startTime >= 2829) {
				startTime = System.currentTimeMillis();
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 1:
			System.out.println("Turn to hit hopper");
			if(Drive.driveToAngle(80, (alliance.toString().equalsIgnoreCase("blue") ? DIRECTION.RIGHT : DIRECTION.LEFT))) {
				startTime = System.currentTimeMillis();
				Drive.setStart();
				nextStep();
			}
			break;
		case 2:
			System.out.println("Drive straight");
			if(System.currentTimeMillis() - startTime >= 1000) {
				startTime = System.currentTimeMillis();
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 3:
			System.out.println("Stop and start shooter");
			if(System.currentTimeMillis() - startTime >= 750) {
				startTime = System.currentTimeMillis();
				Drive.setStart();
				nextStep();
			} else {
				Drive.setDriveSpeed(0, 0);
				Shooter.runShooter();
			}
			break;
		case 4:
			System.out.println("Turn to shoot");
			if(Drive.driveToAngle(30, (alliance.toString().equalsIgnoreCase("blue") ? DIRECTION.LEFT : DIRECTION.RIGHT))) {
				startTime = System.currentTimeMillis();
				nextStep();
			}
			break;
		case 5:
			System.out.println("Shoot");
			if(System.currentTimeMillis() - startTime >= 4000) {
				startTime = System.currentTimeMillis();
				nextStep();
			} else {
				Drive.setDriveSpeed(0, 0);
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
