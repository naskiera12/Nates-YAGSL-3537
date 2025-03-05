package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralIntake;

public class CoralIntakeIn extends Command {
    private final CoralIntake intake;

    public CoralIntakeIn(CoralIntake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.runIntake(-0.7);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}