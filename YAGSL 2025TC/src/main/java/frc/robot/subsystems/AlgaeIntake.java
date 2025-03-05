package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeIntake extends SubsystemBase {
    private final SparkMax intakeMotor;

    public AlgaeIntake() {
        intakeMotor = new SparkMax(11, MotorType.kBrushless);

        // Configure motor using 2025 REVLib
        SparkBaseConfig config = new SparkMaxConfig().inverted(false).idleMode(IdleMode.kBrake);

        intakeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    /**
     * Runs the intake at the given speed.
     * @param speed Speed value (-1.0 to 1.0)
     */
    public void runIntake(double speed) {
        intakeMotor.set(speed);
    }

    /**
     * Stops the intake motor.
     */
    public void stopIntake() {
        intakeMotor.set(0);
    }
}
