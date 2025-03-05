package frc.robot.commands;

import frc.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class MoveElevatorToMiddle extends Command {
  private final ElevatorSubsystem elevator;
  private static final double TARGET_POSITION = -63;  // Adjust as needed
  private static final double TOLERANCE = 0.05;

  public MoveElevatorToMiddle(ElevatorSubsystem elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    elevator.moveToPosition(TARGET_POSITION);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(elevator.getEncoderPosition() - TARGET_POSITION) < TOLERANCE;
  }
}
