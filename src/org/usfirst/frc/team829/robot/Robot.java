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
	
	public void robotInit() {
		RobotMap.setup();
		driver = new LogitechController(0);
		operator = new LogitechController(1);
		FileLogging.clear();
	}
	
	public void autonomousInit() {
	}
	
	public void autonomousPeriodic() {
	}
	
	public void teleopPeriodic() {
		
		// Log
		DashboardLogging.displayInformation();
		FileLogging.writeInformation();
		
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
		if(operator.getStartButton()) {
			Gear.pivotUp();
			System.out.println("PIVOTING UP");
		} else if(operator.getSelectButton()) {
			Gear.pivotDown();
			System.out.println("PIVOTING DOWN");
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
	}
	
}
