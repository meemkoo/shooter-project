package frc.robot.constants;

import static edu.wpi.first.units.Units.KilogramSquareMeters;

import edu.wpi.first.units.measure.MomentOfInertia;

public interface ShooterConstants {
  int flywheelMotorCanID = 50;

  MomentOfInertia flywheelMOI =
      KilogramSquareMeters.of(0.0003434738); // From the `shape in lb*in^2 1.173709
}
