package org.usfirst.frc.team829.robot;

import org.usfirst.frc.team829.autonomous.Auto;
import org.usfirst.frc.team829.autonomous.CenterGearAuto;
import org.usfirst.frc.team829.autonomous.DoNothingAuto;
import org.usfirst.frc.team829.autonomous.ShootAuto;
import org.usfirst.frc.team829.autonomous.SideGearAuto;
import org.usfirst.frc.team829.controller.LogitechController;
import org.usfirst.frc.team829.logging.DashboardLogging;
import org.usfirst.frc.team829.logging.FileLogging;
import org.usfirst.frc.team829.system.Climb;
import org.usfirst.frc.team829.system.Drive;
import org.usfirst.frc.team829.system.Gear;
import org.usfirst.frc.team829.system.NavX;
import org.usfirst.frc.team829.system.RobotMap;
import org.usfirst.frc.team829.system.Shooter;
import org.usfirst.frc.team829.vision.Pixy;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	LogitechController driver, operator;
	NetworkTable t;
	Pixy pixy;
	int step = 0;
	SendableChooser<Auto> autoChooser = new SendableChooser<Auto>();
	public static double START_ANGLE;
	
	public void robotInit() {
		RobotMap.setup();
		t = NetworkTable.getTable("main");
		driver = new LogitechController(0);
		operator = new LogitechController(1);
		FileLogging.clear();
		NavX.resetAngle();
		NavX.resetDisplacement();
		RobotMap.webCam.startCapture();
		DashboardLogging.displayInformation();
		addAutos();
		RobotMap.navX.reset();
		RobotMap.navX.zeroYaw();
		RobotMap.navX.resetDisplacement();
		START_ANGLE = NavX.getAngleRotation();
		pixy = new Pixy();
	}
	
	public void addAutos() {
		autoChooser.addDefault("Place Center Gear", new CenterGearAuto());
		autoChooser.addObject("Shoot Blue", new ShootAuto(Alliance.Blue));
		autoChooser.addObject("Shoot Red", new ShootAuto(Alliance.Red));
		autoChooser.addObject("Place Side Gear", new SideGearAuto(Alliance.Blue));
		autoChooser.addObject("Place Side Gear", new SideGearAuto(Alliance.Red));
		autoChooser.addObject("Do Nothing", new DoNothingAuto());
		SmartDashboard.putData("Auto Chooser", autoChooser);
	}
	
	public void autonomousInit() {
		SmartDashboard.putData("Auto Chooser", autoChooser);
		DashboardLogging.displayInformation();
		RobotMap.navX.reset();
		RobotMap.navX.zeroYaw();
		RobotMap.navX.resetDisplacement();
	}
	
	public void autonomousPeriodic() {
		SmartDashboard.putData("Auto Chooser", autoChooser);
		DashboardLogging.displayInformation();
		autoChooser.getSelected().execute();
	}
	
	public void teleopPeriodic() {
		
		System.out.println(pixy.getPacket().getRect().toString());
		
		// Log
		DashboardLogging.displayInformation();
		FileLogging.writeInformation();
		
		// Place Gear
		if(operator.getRightJoyButton()) {
			placeGear();
		}
		
		// NavX Update
		/*if(NavX.getAngleRotation() >= 360) {
			NavX.resetAngle();
		}*/
		
		// Climb
		Climb.setClimbSpeed(Math.abs(operator.getAxisValue("lefty")));
		
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
	
	// Check if values in range
	public static boolean inRange(double a, double b) {
		double tolerance = 0.05;
		return a - tolerance <= b && a + tolerance >= b;
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
	
}
