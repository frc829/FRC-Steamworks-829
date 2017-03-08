package org.usfirst.frc.team829.robot;

import org.usfirst.frc.team829.controller.LogitechController;
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
	}
	
	public void autonomousInit() {
	}
	
	public void autonomousPeriodic() {
	}
	
	public void teleopPeriodic() {
		
		// NavX Update
		if(NavX.getAngleRotation() >= 360)
			NavX.resetAngle();
		
		// Climb
		if(driver.getLeftBumper() && driver.getRightBumper())
			Climb.climbUp();
		else
			Climb.setClimbSpeed(0);
		
		// Drive
		Drive.setDriveSpeed(-driver.getAxisValue("lefty"), -driver.getAxisValue("righty"));
		
		if(driver.getStartButton())
			Drive.togglePrecise();
		
		// Gear
		if(operator.getStartButton())
			Gear.pivotUp();
		else if(operator.getSelectButton())
			Gear.pivotDown();
		else
			Gear.setGearPivotSpeed(0);
		
		if(operator.getAButton())
			Gear.grabGear();
		else if(operator.getBButton())
			Gear.releaseGear();
		
		// Shooter
		if(operator.getLeftBumper() && operator.getRightBumper())
			Shooter.runShooter();
		else
			Shooter.setShooterVoltage(0);
		
		if(operator.getAxisValue("lefttrigger") == 1 && operator.getAxisValue("righttrigger") == 1)
			Shooter.spinSingulator();
		else
			Shooter.setShooterSingulatorSpeed(0);
		
		if(operator.getXButton())
			Shooter.spinCentrifuge();
		else
			Shooter.setShooterCentrifugeSpeed(0);
		
		if(driver.getSelectButton())
			Shooter.toggleShooterLight();
		Shooter.updateLight();
		
	}
	
	public void testInit() {
	}
	
	public void testPeriodic() {
	}
	
	public void disabledInit() {
	}
	
}
