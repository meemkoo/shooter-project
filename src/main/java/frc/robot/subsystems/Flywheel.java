package frc.robot.subsystems;

import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.RPM;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXSimCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.sbdc.loggerhead.LightSubsystem;
import com.sbdc.loggerhead.Loggable;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.ShooterConstants;

public class Flywheel extends LightSubsystem implements Loggable {


  private TalonSRX rawMotor = new WPI_TalonSRX(ShooterConstants.flywheelMotorCanID);
  private TalonSRXSimCollection rawMotorSimCollection = rawMotor.getSimCollection();

  private FlywheelSim flywheelSim = new FlywheelSim(
    LinearSystemId.createFlywheelSystem(DCMotor.getCIM(1), ShooterConstants.flywheelMOI.in(KilogramSquareMeters), 1),
    DCMotor.getMiniCIM(1),
    1
    );


  public Flywheel() {
    TalonSRXConfiguration motorConfig = new TalonSRXConfiguration();

    rawMotorSimCollection.addQuadraturePosition(0);

    motorConfig.peakCurrentLimit = 40;
    motorConfig.peakCurrentDuration = 100;
    motorConfig.continuousCurrentLimit = 40;

    rawMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

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

  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {}
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
