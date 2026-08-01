// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.sbdc.loggerhead.LogMode;
import com.sbdc.loggerhead.Loggerhead;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Flywheel;

public class RobotContainer {
  private final Flywheel flywheel = new Flywheel();

  private final CommandXboxController m_driverController = new CommandXboxController(0);

  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    Loggerhead.getInstance().getConfigurator().setConfigureCallback(this::configureLogging);
    Loggerhead.getInstance().initializeLogging(true);
  }

  public void addPeriodics(Robot robot) {
    robot.addPeriodic(Loggerhead.getInstance()::update, 0.02);
  }

  public void configureLogging() {
    Loggerhead.getInstance()
        .getRootTable()
        .getSubTable("Flywheel")
        .addLoggable(flywheel, LogMode.Both);
    ;
  }

  private void configureBindings() {
    m_driverController
        .b()
        .whileTrue(
            Commands.runEnd(() -> flywheel.setSpeed(200), () -> flywheel.setDuty(0), flywheel));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Commands.none();
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_exampleSubsystem);
  }
}
