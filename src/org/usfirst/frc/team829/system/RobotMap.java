package org.usfirst.frc.team829.system;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;

public class RobotMap {
	
	// Numerical values for CAN Talons
	private static final int DRIVE_BACK_LEFT = 10;
	private static final int DRIVE_FRONT_LEFT = 11;
	private static final int DRIVE_BACK_RIGHT = 12;
	private static final int DRIVE_FRONT_RIGHT = 13;
	private static final int GEAR_PIVOT = 9;
	private static final int GEAR_ROLLER = 18;
	private static final int SHOOTER_SHOOTER = 14;
	private static final int SHOOTER_CENTRIFUGE = 17;
	
	// Numerical values for Talons
	private static final int CLIMB_CLIMB_A = 15;
	private static final int CLIMB_CLIMB_B = 16;
	private static final int SHOOTER_SINGULATOR = 0;
	
	// Numerical values for Relays
	private static final int SHOOTER_LIGHT = 0;
	
	// NavX-MXP
	public static AHRS navX;
	
	// Climb
	public static CANTalon climbClimbA, climbClimbB;
	
	// Drive
	public static CANTalon driveBackLeft, driveFrontLeft, driveBackRight, driveFrontRight;
	
	// Gear
	public static Talon gearPivot;
	public static CANTalon gearRoller;
	
	// Shooter
	public static CANTalon shooterShooter, shooterCentrifuge;
	public static Talon shooterSingulator;
	public static Relay shooterLight;
	
	public static void setup() {
		
		climbInit();
		driveInit();
		gearInit();
		shooterInit();
		
	}
	
	public static void navXInit() {
		
		try {
			navX = new AHRS(SerialPort.Port.kMXP, AHRS.SerialDataType.kProcessedData, (byte) 50);
		} catch(Exception e) {
			DriverStation.reportError("NavX Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void climbInit() {
		
		try {
			climbClimbA = new CANTalon(CLIMB_CLIMB_A);
			climbClimbB = new CANTalon(CLIMB_CLIMB_B);
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
			gearPivot = new Talon(GEAR_PIVOT);
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
