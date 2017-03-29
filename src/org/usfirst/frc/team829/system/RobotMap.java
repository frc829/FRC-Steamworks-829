package org.usfirst.frc.team829.system;

import org.usfirst.frc.team829.vision.WebCam;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;

public class RobotMap {
	
	// Numerical values for CAN Talons
	private static final int DRIVE_BACK_LEFT = 10;
	private static final int DRIVE_FRONT_LEFT = 11;
	private static final int DRIVE_BACK_RIGHT = 12;
	private static final int DRIVE_FRONT_RIGHT = 13;
	private static final int GEAR_PIVOT = 16;
	private static final int GEAR_ROLLER = 15;
	private static final int SHOOTER_SHOOTER = 14;
	private static final int SHOOTER_CENTRIFUGE = 17;
	
	// Numerical values for Talons
	private static final int CLIMB_CLIMB_A = 1;
	private static final int CLIMB_CLIMB_B = 2;
	private static final int SHOOTER_SINGULATOR = 0;
	
	// Numerical values for Relays
	private static final int SHOOTER_LIGHT = 0;
	
	// NavX-MXP
	public static AHRS navX;
	
	// Webcam
	public static WebCam webCam;
	
	// Climb
	public static Talon climbClimbA, climbClimbB;
	
	// Drive
	public static CANTalon driveBackLeft, driveFrontLeft, driveBackRight, driveFrontRight;
	
	// Gear
	public static CANTalon gearPivot, gearRoller;
	
	// Shooter
	public static CANTalon shooterShooter, shooterCentrifuge;
	public static Talon shooterSingulator;
	public static Relay shooterLight;
	
	public static void setup() {
		
		navXInit();
		webCamInit();
		climbInit();
		driveInit();
		gearInit();
		shooterInit();
		
	}
	
	public static void navXInit() {
		
		try {
			navX = new AHRS(SerialPort.Port.kUSB);
			navX.zeroYaw();
		} catch(Exception e) {
			DriverStation.reportError("NavX Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void webCamInit() {
		
		try {
			webCam = new WebCam();
		} catch(Exception e) {
			DriverStation.reportError("WebCam Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void climbInit() {
		
		try {
			climbClimbA = new Talon(CLIMB_CLIMB_A);
			climbClimbB = new Talon(CLIMB_CLIMB_B);
		} catch(Exception e) {
			DriverStation.reportError("Climb Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void driveInit() {
		
		try {
			driveBackLeft = new CANTalon(DRIVE_BACK_LEFT);
			driveFrontLeft = new CANTalon(DRIVE_FRONT_LEFT);
			driveBackRight = new CANTalon(DRIVE_BACK_RIGHT);
			driveFrontRight = new CANTalon(DRIVE_FRONT_RIGHT);
			Drive.preciseTime = System.currentTimeMillis();
			driveBackLeft.setInverted(true);
		} catch(Exception e) {
			DriverStation.reportError("Drive Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void gearInit() {
		
		try {
			gearPivot = new CANTalon(GEAR_PIVOT);
			gearPivot.setInverted(true);
			gearRoller = new CANTalon(GEAR_ROLLER);
		} catch(Exception e) {
			DriverStation.reportError("Gear Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void shooterInit() {
		
		try {
			shooterCentrifuge = new CANTalon(SHOOTER_CENTRIFUGE);
			shooterShooter = new CANTalon(SHOOTER_SHOOTER);
			shooterShooter.setInverted(true);
			shooterSingulator = new Talon(SHOOTER_SINGULATOR);
			shooterSingulator.setInverted(true);
			shooterLight = new Relay(SHOOTER_LIGHT);
			shooterShooter.changeControlMode(TalonControlMode.Voltage);
			Shooter.lightTime = System.currentTimeMillis();
		} catch(Exception e) {
			DriverStation.reportError("Shooter Error: " + e.getMessage(), true);
		}
		
	}
	
}
