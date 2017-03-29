package org.usfirst.frc.team829.robot;

import org.usfirst.frc.team829.autonomous.Auto;
import org.usfirst.frc.team829.autonomous.CenterGearAuto;
import org.usfirst.frc.team829.autonomous.DoNothingAuto;
import org.usfirst.frc.team829.autonomous.DriveForwardAuto;
import org.usfirst.frc.team829.autonomous.ShootDriveAuto;
import org.usfirst.frc.team829.autonomous.ShootGearAuto;
import org.usfirst.frc.team829.autonomous.SideGearAuto;
import org.usfirst.frc.team829.controller.LogitechController;
import org.usfirst.frc.team829.logging.DashboardLogging;
import org.usfirst.frc.team829.logging.FileLogging;
import org.usfirst.frc.team829.system.Climb;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.Drive.DIRECTION;
import org.usfirst.frc.team829.system.Gear;
import org.usfirst.frc.team829.system.RobotMap;
import org.usfirst.frc.team829.system.Shooter;
import org.usfirst.frc.team829.vision.Pixy;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// Controllers for the drivers
	LogitechController driver, operator;
	
	// Starting angle
	public static double START_ANGLE;
	
	// Pixy camera
	Pixy pixy;
	
	// Auto Chooser
	SendableChooser<Auto> autoChooser = new SendableChooser<Auto>();
	
	public void robotInit() {
		
		// Setup Robot systems
		RobotMap.setup();
		
		// Create controllers
		driver = new LogitechController(0);
		operator = new LogitechController(1);
		
		// Clear file for logging
		FileLogging.clear();
		
		// SmartDashboard
		RobotMap.webCam.startCapture();
		DashboardLogging.displayInformation();
		addAutos();
		
		// Reset NavX
		RobotMap.navX.reset();
		RobotMap.navX.zeroYaw();
		RobotMap.navX.resetDisplacement();
		
		RobotMap.shooterLight.set(Relay.Value.kForward);
		
		// Set starting angle
		START_ANGLE = RobotMap.navX.getAngle();
		
		// Start Pixy
		pixy = new Pixy();
		
	}
	
	public void addAutos() {
		
		// Add and display autos
		autoChooser.addDefault("Place Center Gear", new CenterGearAuto());
		autoChooser.addObject("Shoot Drive Blue", new ShootDriveAuto(Alliance.Blue));
		autoChooser.addObject("Shoot Drive Red", new ShootDriveAuto(Alliance.Red));
		autoChooser.addObject("Shoot Gear Blue", new ShootGearAuto(Alliance.Blue));
		autoChooser.addObject("Shoot Gear Red", new ShootGearAuto(Alliance.Red));
		autoChooser.addObject("Place Side Gear Left", new SideGearAuto(DIRECTION.LEFT));
		autoChooser.addObject("Place Side Gear Right", new SideGearAuto(DIRECTION.RIGHT));
		autoChooser.addObject("Drive Forward", new DriveForwardAuto());
		autoChooser.addObject("Do Nothing", new DoNothingAuto());
		SmartDashboard.putData("Auto Chooser", autoChooser);
		
	}
	
	public void autonomousInit() {
		
		// SmartDashboard
		SmartDashboard.putData("Auto Chooser", autoChooser);
		DashboardLogging.displayInformation();
		
		// Reset again to be safe
		RobotMap.navX.reset();
		RobotMap.navX.zeroYaw();
		RobotMap.navX.resetDisplacement();
		
	}
	
	public void autonomousPeriodic() {
		
		// SmartDashboard
		SmartDashboard.putData("Auto Chooser", autoChooser);
		DashboardLogging.displayInformation();
		
		// Run selected Auto
		autoChooser.getSelected().execute();
		
	}
	
	public void teleopPeriodic() {
		
		// Display Pixy object
		try {
			if(pixy.getPacket().getWidth() != 0 && pixy.getPacket().getHeight() != 0) {
				System.out.println(pixy.getPacket().getRect().toString());
			}
		} catch(Exception e) {
			
		}
		
		// Log
		DashboardLogging.displayInformation();
		FileLogging.writeInformation();
		
		// Place Gear
		if(operator.getRightJoyButton()) {
			placeGear();
		}
		
		// Climb
		Climb.setClimbSpeed(-Math.abs(operator.getAxisValue("lefty")));
		
		// Drive
		double left = -driver.getAxisValue("lefty"), right = -driver.getAxisValue("righty");
		Drive.setDriveSpeed(left, right);
		
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
	public static void placeGear() {
		
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
				if(System.currentTimeMillis() - startTime >= 350) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.pivotDown();
					Gear.grabGear();
					Drive.setDriveSpeed(-.350, -.350);
				}
				break;
			case 2:
				if(System.currentTimeMillis() - startTime >= 250) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.stopPivot();
					Gear.grabGear();
					Drive.setDriveSpeed(-.350, -.350);
				}
				break;
			case 3:
				if(System.currentTimeMillis() - startTime >= 500) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.pivotUp();
				}
				break;
			case 4:
				Drive.setDriveSpeed(0.000, 0.000);
				Gear.stopGear();
				Gear.stopPivot();
				functionRunning = false;
				break;
			}
		}
		
	}
	
	public static void placeGearAuto() {
		
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
				if(System.currentTimeMillis() - startTime >= 350) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.pivotDown();
					Gear.setGearRollerSpeed(0.25);
					Drive.setDriveSpeed(-.350, -.350);
				}
				break;
			case 2:
				if(System.currentTimeMillis() - startTime >= 250) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.stopPivot();
					Gear.setGearRollerSpeed(0.25);
					Drive.setDriveSpeed(-.350, -.350);
				}
				break;
			case 3:
				if(System.currentTimeMillis() - startTime >= 500) {
					startTime = System.currentTimeMillis();
					step++;
				} else {
					Gear.pivotUp();
				}
				break;
			case 4:
				Drive.setDriveSpeed(0.000, 0.000);
				Gear.stopGear();
				Gear.stopPivot();
				functionRunning = false;
				break;
			}
		}
		
	}

	
}
