package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
  private final SparkMax motor;
  private final SparkClosedLoopController closedLoopController;
  private final RelativeEncoder encoder;

  // Feedforward for gravity compensation
  private final SimpleMotorFeedforward feedforward;
  private static final double kS = 0.05;
  private static final double kG = 0.2;
  private static final double kV = 0.0;

  // Shuffleboard dashboard entries
  private final GenericEntry encoderPositionEntry;
  private final GenericEntry encoderVelocityEntry;

  // Simulation variables (used only when running in simulation)
  private double simulationTargetPosition = 0;
  private double simulatedPosition = 0;
  private static final double kSimSpeed = 5.0; // Adjust simulation speed as needed

  public ElevatorSubsystem() {
    motor = new SparkMax(13, MotorType.kBrushless);
    closedLoopController = motor.getClosedLoopController();
    encoder = motor.getEncoder();

    // Initialize feedforward for gravity compensation.
    feedforward = new SimpleMotorFeedforward(kS, kG, kV);

    SparkMaxConfig config = new SparkMaxConfig();
    config.encoder.positionConversionFactor(1)
          .velocityConversionFactor(1);
    config.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
          .p(0.1)
          .i(0)
          .d(0)
          .outputRange(-1, 1);
    // Set motor to brake mode.
    config.idleMode(IdleMode.kBrake);
    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    // Add Shuffleboard widgets under the "Elevator" tab
    ShuffleboardTab elevatorTab = Shuffleboard.getTab("Elevator");
    encoderPositionEntry = elevatorTab.add("Encoder Position", encoder.getPosition()).getEntry();
    encoderVelocityEntry = elevatorTab.add("Encoder Velocity", encoder.getVelocity()).getEntry();
  }

  /**
   * Moves the elevator to the given target encoder position.
   * Negative target values are clamped to zero.
   */
  public void moveToPosition(double targetPosition) {
    // Clamp target to a minimum of 0.
    if (targetPosition > 0) {
      targetPosition = 0;
    }
    if (RobotBase.isSimulation()) {
      simulationTargetPosition = targetPosition;
    } else {
      // Calculate gravity feedforward (assumes zero desired velocity)
      double ff = feedforward.calculate(0.0);
      closedLoopController.setReference(targetPosition, ControlType.kPosition, ClosedLoopSlot.kSlot0, ff);
    }
  }

  /** Returns the current encoder position. */
  public double getEncoderPosition() {
    return RobotBase.isSimulation() ? simulatedPosition : encoder.getPosition();
  }

  @Override
  public void periodic() {
    if (RobotBase.isSimulation()) {
      // Simple simulation: gradually move simulatedPosition toward simulationTargetPosition.
      double dt = 0.02; // Assuming a 20ms loop period.
      double error = simulationTargetPosition - simulatedPosition;
      double simulatedVelocity = kSimSpeed * error;
      simulatedPosition += simulatedVelocity * dt;

      // Prevent simulatedPosition from going below zero.
      if (simulatedPosition < 0) {
        simulatedPosition = 0;
        simulatedVelocity = 0;
      }

      // Update Shuffleboard with simulated values.
      encoderPositionEntry.setDouble(simulatedPosition);
      encoderVelocityEntry.setDouble(simulatedVelocity);
    } else {
      // Update Shuffleboard with actual encoder values.
      encoderPositionEntry.setDouble(encoder.getPosition());
      encoderVelocityEntry.setDouble(encoder.getVelocity());
    }
  }
}
