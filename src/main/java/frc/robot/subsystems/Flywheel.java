package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RPM;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.sbdc.loggerhead.LightSubsystem;
import com.sbdc.loggerhead.LogMode;
import com.sbdc.loggerhead.Loggable;
import com.sbdc.loggerhead.Loggerhead;
import com.sbdc.loggerhead.Table;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.constants.ShooterConstants;

public class Flywheel extends LightSubsystem implements Loggable {
  private TalonSRX rawMotor = new WPI_TalonSRX(ShooterConstants.flywheelMotorCanID);
  private CANcoder rawEncoder = new CANcoder(51);

  // private TalonSRXSimCollection rawMotorSimCollection = rawMotor.getSimCollection();

  // private FlywheelSim flywheelSim = new FlywheelSim(
  //   LinearSystemId.createFlywheelSystem(DCMotor.getCIM(1),
  // ShooterConstants.flywheelMOI.in(KilogramSquareMeters), 1),
  //   DCMotor.getMiniCIM(1),
  //   1
  //   );

  public Flywheel() {
    TalonSRXConfiguration motorConfig = new TalonSRXConfiguration();
    rawMotor.configFactoryDefault();

    motorConfig.peakCurrentLimit = 40;
    motorConfig.peakCurrentDuration = 100;
    motorConfig.continuousCurrentLimit = 40;

    motorConfig.remoteFilter0.remoteSensorDeviceID = 51;
    motorConfig.remoteFilter1.remoteSensorDeviceID = 51;
    motorConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.CANCoder;
    motorConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.CANCoder;

    rawMotor.configSensorTerm(SensorTerm.Diff0, RemoteFeedbackDevice.RemoteSensor0);
    rawMotor.configSensorTerm(SensorTerm.Diff1, RemoteFeedbackDevice.RemoteSensor0);
    rawMotor.configRemoteFeedbackFilter(51, RemoteSensorSource.CANCoder, 0, 10);
    rawMotor.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0, 0, 10);
    rawMotor.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, 0, 10);

    motorConfig.slot0.kP = 1;
    motorConfig.slot0.kI = 0;
    motorConfig.slot0.kD = 0;

    rawMotor.setInverted(InvertType.InvertMotorOutput);
    rawMotor.setNeutralMode(NeutralMode.Coast);

    rawMotor.configAllSettings(motorConfig);
  }

  public void setSpeed(AngularVelocity speed) {
    rawMotor.set(ControlMode.Velocity, speed.in(RPM));
  }

  public void setDuty(double speed) {
    rawMotor.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void setupLogging(Table parentTable, LogMode logMode, Loggerhead loggerhead) {
    parentTable.addDoubleLogger(
        "speed", LogMode.NetworkOnly, () -> rawMotor.getSelectedSensorVelocity());
    parentTable.addDoubleLogger(
        "speed2", LogMode.NetworkOnly, () -> rawEncoder.getVelocity().getValueAsDouble());
  }
  //   flywheelSim.setInput(rawMotor.getMotorOutputPercent() * RoboRioSim.getVInVoltage());
  //   flywheelSim.update(0.02);

  //   shooter_motor_sim.iterate(
  //       Units.radiansPerSecondToRotationsPerMinute(
  //           flywheelSim.getAngularVelocityRadPerSec()),
  //       RoboRioSim.getVInVoltage(), 0.02
  //   );

  //   RoboRioSim.setVInVoltage(
  //       BatterySim.calculateDefaultBatteryLoadedVoltage(flywheelSim.getCurrentDrawAmps()));

  //   // Fixme! TODO: AHHHHHH
  //   SmartDashboard.putNumber("shooter_speed", flywheelSim.getAngularVelocity().in(RPM));
  // }
}
