// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private WPI_VictorSPX bag;
  private XboxController con1;
  private XboxController con2;
  private WPI_VictorSPX FR;
  private WPI_VictorSPX FL;
  private WPI_VictorSPX BR;
  private WPI_VictorSPX BL;
  private MotorControllerGroup R;
  private MotorControllerGroup L;
  private DifferentialDrive drive;


  double bagspeed=0,carspeed=0;
  boolean on=false,reverse=false;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putNumber("Bag_Motor_Speed", 0);
    SmartDashboard.putNumber("Car_Speed", 0);
    bag = new WPI_VictorSPX(7);//把數字(0)改成馬達控制器編號
    con1 = new XboxController(0);
    con2 = new XboxController(1);
    FR = new WPI_VictorSPX(1);
    FL = new WPI_VictorSPX(8);
    BR = new WPI_VictorSPX(9);
    BL = new WPI_VictorSPX(11);
    R = new MotorControllerGroup(FR, BR);
    L = new MotorControllerGroup(FL, BL);
    drive = new DifferentialDrive(R,L);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    bagspeed=SmartDashboard.getNumber("Bag_Motor_Speed", 0);
    carspeed=SmartDashboard.getNumber("Car_Speed", 0);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    System.out.println("enabled");
    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // bag motor control //
    if(con1.getAButtonPressed()) on=!on;
    if(con1.getBButtonPressed()) reverse=!reverse;
    if(on&&reverse) bag.set(-bagspeed);
    else if(on&&!reverse) bag.set(bagspeed);
    else bag.set(0);
    
    drive.tankDrive(con2.getLeftY()*carspeed, con2.getRightY()*-carspeed);
//
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
