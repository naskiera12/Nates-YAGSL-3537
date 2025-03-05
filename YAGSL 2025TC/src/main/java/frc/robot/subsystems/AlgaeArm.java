package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeArm extends SubsystemBase {
    private final SparkMax armMotor1;
    private final SparkMax armMotor2;

    public AlgaeArm() {
        armMotor1 = new SparkMax(9, MotorType.kBrushless);
        armMotor2 = new SparkMax(10, MotorType.kBrushless);

        // Configure motors using 2025 REVLib
        SparkBaseConfig config1 = new SparkMaxConfig().inverted(false).idleMode(IdleMode.kBrake);
        SparkBaseConfig config2 = new SparkMaxConfig().inverted(true).idleMode(IdleMode.kBrake);

        armMotor1.configure(config1, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        armMotor2.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    /**
     * Runs the arm at the given speed.
     * @param speed Speed value (-1.0 to 1.0)
     */
    public void runArm(double speed) {
        armMotor1.set(speed);
        armMotor2.set(speed);
    }

    /**
     * Stops the arm motors.
     */
    public void stopArm() {
        armMotor1.set(0);
        armMotor2.set(0);
    }
}
