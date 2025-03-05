package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeArm;

public class AlgaeArmDown extends Command {
    private final AlgaeArm arm;

    public AlgaeArmDown(AlgaeArm arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.runArm(0.2);
    }

    @Override
    public void end(boolean interrupted) {
        arm.stopArm();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}