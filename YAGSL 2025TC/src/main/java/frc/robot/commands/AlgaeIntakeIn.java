package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeIntake;

public class AlgaeIntakeIn extends Command {
    private final AlgaeIntake intake;

    public AlgaeIntakeIn(AlgaeIntake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.runIntake(0.75);
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