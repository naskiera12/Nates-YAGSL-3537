package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralIntake extends SubsystemBase {
    private final SparkMax intakeMotor1;
    private final SparkMax intakeMotor2;

    public CoralIntake() {
        intakeMotor1 = new SparkMax(14, MotorType.kBrushless);
        intakeMotor2 = new SparkMax(15, MotorType.kBrushless);

        // Configure motors using 2025 REVLib
        SparkBaseConfig config1 = new SparkMaxConfig().inverted(true).idleMode(IdleMode.kCoast);
        SparkBaseConfig config2 = new SparkMaxConfig().inverted(false).idleMode(IdleMode.kCoast);

        intakeMotor1.configure(config1, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        intakeMotor2.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    /**
     * Runs the intake at the given speed.
     * @param speed Speed value (-1.0 to 1.0)
     */
    public void runIntake(double speed) {
        intakeMotor1.set(speed);
        intakeMotor2.set(speed);
    }

    /**
     * Stops the intake motors.
     */
    public void stopIntake() {
        intakeMotor1.set(0);
        intakeMotor2.set(0);
    }
}
