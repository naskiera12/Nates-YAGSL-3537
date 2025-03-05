package frc.robot.commands;

import frc.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class MoveElevatorToBottom extends Command {
  private final ElevatorSubsystem elevator;
  private static final double TARGET_POSITION = 0;  // Adjust as needed
  private static final double TOLERANCE = 0.05;       // Encoder tolerance

  public MoveElevatorToBottom(ElevatorSubsystem elevator) {
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
