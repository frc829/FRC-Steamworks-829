package org.usfirst.frc.team829.system;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;

public class RobotMap {

	// Numerical values for CAN Talons
	private static final int DRIVE_BACK_LEFT = 10;
	private static final int DRIVE_FRONT_LEFT = 11;
	private static final int DRIVE_BACK_RIGHT = 12;
	private static final int DRIVE_FRONT_RIGHT = 13;
	private static final int GEAR_PIVOT = 14;
	private static final int GEAR_ROLLER = 15;
	private static final int SHOOTER_SHOOTER = 16;
	private static final int SHOOTER_CENTRIFUGE = 17;
	
	// Numerical values for Talons
	private static final int CLIMB_CLIMB_A = 0;
	private static final int CLIMB_CLIMB_B = 1;
	
	// Numerical values for Relays
	private static final int SHOOTER_LIGHT = 0;
	
	// NavX-MXP
	public static AHRS navx;
	
	// Climb
	public static Talon climbClimbA, climbClimbB;
	
	// Drive
	public static CANTalon driveBackLeft, driveFrontLeft, driveBackRight, driveFrontRight;
	
	// Gear
	public static CANTalon gearPivot, gearRoller;
	
	// Shooter
	public static CANTalon shooterShooter, shooterCentrifuge;
	public static Relay shooterLight;
	
	public static void setup() {
		
		climbInit();
		driveInit();
		gearInit();
		shooterInit();
		
	}
	
	public static void navXInit() {
		
		try {
			navx = new AHRS(SPI.Port.kMXP);
		} catch(Exception e) {
			DriverStation.reportError("NavX Error: " + e.getMessage(), true);
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
		} catch(Exception e) {
			DriverStation.reportError("Drive Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void gearInit() {
		
		try {
			gearPivot = new CANTalon(GEAR_PIVOT);
			gearRoller = new CANTalon(GEAR_ROLLER);
			gearPivot.reverseOutput(true);
		} catch(Exception e) {
			DriverStation.reportError("Gear Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void shooterInit() {
		
		try {
			shooterCentrifuge = new CANTalon(SHOOTER_CENTRIFUGE);
			shooterShooter = new CANTalon(SHOOTER_SHOOTER);
			shooterLight = new Relay(SHOOTER_LIGHT);
		} catch(Exception e) {
			DriverStation.reportError("Shooter Error: " + e.getMessage(), true);
		}
		
	}
	
}
