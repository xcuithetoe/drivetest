// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
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

  private Spark m_left1 = new Spark(2);
  private Spark m_left2 = new Spark(3);
  private Spark m_right1 = new Spark(4);
  private Spark m_right2 = new Spark(5);
  private Joystick m_joystick = new Joystick(0);

  int wpk = 0 ;
  private int m_driveMode = 0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    if (RobotBase.isReal()) {
        m_right1.setInverted(true);
        m_right2.setInverted(true);
    }
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

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
      wpk++ ;
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
  public void teleopInit() {}

  private void tankDrive() {
    double leftY = m_joystick.getRawAxis(1);
    double rightY = m_joystick.getRawAxis(5);

    m_left1.set(leftY);
    m_left2.set(leftY);
    m_right1.set(rightY);
    m_right2.set(rightY);
  }

  private void arcadeDrive(){
    double angle = m_joystick.getRawAxis(0) * 0.5;
    double force = m_joystick.getRawAxis(1);

    double leftY = force + angle;
    double rightY = force - angle;

    m_left1.set(-leftY);
    m_left2.set(-leftY);
    m_right1.set(-rightY);
    m_right2.set(-rightY);
  }

  private void cheesyDrive() {
    double angle = m_joystick.getRawAxis(0) * -0.5;
    double force = m_joystick.getRawAxis(3);

    double leftY =  force + angle;
    double rightY = force - angle;

    m_left1.set(-leftY);
    m_left2.set(-leftY);
    m_right1.set(-rightY);
    m_right2.set(-rightY);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
      wpk++;
      
      if (m_joystick.getRawButton(5)) {
        m_driveMode ++;
      }

      if (m_driveMode > 3) {
        m_driveMode = 0;
      }

      if (m_driveMode == 0) {
        tankDrive();
      } else if (m_driveMode == 1) {
        arcadeDrive();
      } else {
        cheesyDrive();
      }
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
}
