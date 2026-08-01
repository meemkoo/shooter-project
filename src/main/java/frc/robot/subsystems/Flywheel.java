package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RPM;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.sbdc.loggerhead.LightSubsystem;
import com.sbdc.loggerhead.LogMode;
import com.sbdc.loggerhead.Loggable;
import com.sbdc.loggerhead.Loggerhead;
import com.sbdc.loggerhead.Table;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import frc.robot.constants.ShooterConstants;

public class Flywheel extends LightSubsystem implements Loggable {
  private interface ConstCtrl extends ShooterConstants.MotorPIDxFeedforward {}
  ;

  private TalonSRX rawMotor = new WPI_TalonSRX(ShooterConstants.flywheelMotorCanID);
  private CANcoder rawEncoder = new CANcoder(51);

  private PIDController pidcontroller =
      new PIDController(ConstCtrl.kP, ConstCtrl.kI, ConstCtrl.kD, 0.02);
  private SimpleMotorFeedforward feedforward =
      new SimpleMotorFeedforward(ConstCtrl.kS, ConstCtrl.kV, ConstCtrl.kA);

  private double currentOutput = 0;

  public Flywheel() {
    TalonSRXConfiguration motorConfig = new TalonSRXConfiguration();
    rawMotor.configFactoryDefault();

    motorConfig.peakCurrentLimit = 40;
    motorConfig.peakCurrentDuration = 100;
    motorConfig.continuousCurrentLimit = 40;

    rawMotor.setInverted(InvertType.InvertMotorOutput);
    rawMotor.setNeutralMode(NeutralMode.Coast);

    rawMotor.configAllSettings(motorConfig);
  }

  public void setDuty(double speed) {
    rawMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setSpeed(double speedRPM) {
    pidcontroller.setSetpoint(speedRPM);
    rawMotor.set(ControlMode.PercentOutput, currentOutput + feedforward.calculate(speedRPM));
  }

  @Override
  public void periodic() {
    currentOutput = pidcontroller.calculate(rawEncoder.getVelocity().getValue().in(RPM));
  }

  @Override
  public void simulationPeriodic() {}

  public void setupLogging(Table parentTable, LogMode logMode, Loggerhead loggerhead) {
    parentTable.addDoubleLogger(
        "speed", LogMode.NetworkOnly, () -> rawMotor.getSelectedSensorVelocity());
    parentTable.addDoubleLogger(
        "speed2", LogMode.NetworkOnly, () -> rawEncoder.getVelocity().getValue().in(RPM));
  }
}
