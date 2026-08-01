package frc.robot.constants;

import static edu.wpi.first.units.Units.KilogramSquareMeters;

import edu.wpi.first.units.measure.MomentOfInertia;

public interface ShooterConstants {
  int flywheelMotorCanID = 50;

  public interface MotorPIDxFeedforward {
    double kP = 0;
    double kI = 0;
    double kD = 0;

    double kS = 0;
    double kV = 0.00029;
    double kA = 0;
  }

  MomentOfInertia flywheelMOI =
      KilogramSquareMeters.of(0.0003434738); // From the `shape in lb*in^2 1.173709
}
