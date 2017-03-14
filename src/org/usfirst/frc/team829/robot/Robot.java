package org.usfirst.frc.team829.robot;

import org.usfirst.frc.team829.controller.LogitechController;
import org.usfirst.frc.team829.logging.DashboardLogging;
import org.usfirst.frc.team829.logging.FileLogging;
import org.usfirst.frc.team829.system.Climb;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.Gear;
import org.usfirst.frc.team829.system.NavX;
import org.usfirst.frc.team829.system.RobotMap;
import org.usfirst.frc.team829.system.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	LogitechController driver, operator;
	int step = 0;
	
	public void robotInit() {
		RobotMap.setup();
		driver = new LogitechController(0);
		operator = new LogitechController(1);
		FileLogging.clear();
		NavX.resetAngle();
		NavX.resetDisplacement();
		RobotMap.webCam.startCapture();
	}
	
	public void autonomousInit() {
	}
	
	public void autonomousPeriodic() {
		if(step == 0) {
			Drive.driveDistance(Variables.UNITS_PER_FEET, .5, .5);
			step++;
		}
	}
	
	public void teleopPeriodic() {
		
		// Log
		DashboardLogging.displayInformation();
		FileLogging.writeInformation();
		
		// Place Gear
		if(operator.getRightJoyButton()) {
			placeGear();
		}
		
		// NavX Update
		if(NavX.getAngleRotation() >= 360) {
			NavX.resetAngle();
		}
		
		// Climb
		if(driver.getLeftBumper() && driver.getRightBumper()) {
			Climb.climbUp();
		} else {
			Climb.stopClimb();
		}
		
		// Drive
		Drive.setDriveSpeed(-driver.getAxisValue("lefty"), -driver.getAxisValue("righty"));
		
		if(driver.getStartButton()) {
			Drive.togglePrecise();
		}
		
		// Gear
		if(operator.getSelectButton()) {
			Gear.pivotUp();
		} else if(operator.getStartButton()) {
			Gear.pivotDown();
		} else {
			Gear.stopPivot();;
		}
		
		if(operator.getAButton()) {
			Gear.grabGear();
		} else if(operator.getBButton()) {
			Gear.releaseGear();
		} else {
			Gear.stopGear();
		}
		
		// Shooter
		if(operator.getLeftBumper() && operator.getRightBumper()) {
			Shooter.runShooter();
		} else {
			Shooter.stopShooter();
		}
		
		if(operator.getAxisValue("lefttrigger") == 1 && operator.getAxisValue("righttrigger") == 1) {
			Shooter.spinSingulator();
		} else {
			Shooter.stopSingulator();
		}
		
		if(operator.getXButton()) {
			Shooter.spinCentrifuge();
		} else {
			Shooter.stopCentrifuge();
		}
		
		if(driver.getSelectButton()) {
			Shooter.toggleShooterLight();
		}
		Shooter.updateLight();
		
	}
	
	public void testInit() {
	}
	
	public void testPeriodic() {
	}
	
	public void disabledInit() {
		RobotMap.webCam.stopCapture();
	}
	
	// Place a gear
	public void placeGear() {
		
		boolean functionRunning = true;
		int step = 0;
		long startTime = System.currentTimeMillis();
		
		while(functionRunning) {
			switch(step) {
			case 0:
				startTime = System.currentTimeMillis();
				step++;
				break;
			case 1:
				if(System.currentTimeMillis() - startTime >= 500) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.pivotDown();
					Gear.grabGear();
					Drive.setDriveSpeed(-.500, -.500);
				}
				break;
			case 2:
				if(System.currentTimeMillis() - startTime >= 500) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.stopPivot();
					Gear.grabGear();
					Drive.setDriveSpeed(-.500, -.500);
				}
				break;
			case 3:
				Drive.setDriveSpeed(0.000, 0.000);
				Gear.stopGear();
				functionRunning = false;
				break;
			}
		}
		
	}
	
}
