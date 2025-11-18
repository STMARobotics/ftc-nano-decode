package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "DriveCode", group = "StarterBot")
public class DriveCode extends OpMode {

    public boolean flywheeling = false;

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor rightDriveB = null;
    private DcMotor leftDriveB = null;
    private DcMotor flywheel = null;


    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;

    private final ElapsedTime shootTimer = new ElapsedTime();

    private boolean isShooting = false;

    public void init() {

        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        leftDriveB = hardwareMap.get(DcMotor.class, "left_driveB");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        rightDriveB = hardwareMap.get(DcMotor.class, "right_driveB");
        flywheel = hardwareMap.get(DcMotor.class, "launch_drive");

        leftFeeder = hardwareMap.get(CRServo.class, "left_Feeder");
        rightFeeder = hardwareMap.get(CRServo.class, "right_Feeder");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDriveB.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDriveB.setDirection(DcMotor.Direction.FORWARD);
        flywheel.setDirection(DcMotor.Direction.FORWARD);

        leftFeeder.setDirection(CRServo.Direction.FORWARD);
        rightFeeder.setDirection(CRServo.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
        leftDriveB.setZeroPowerBehavior(BRAKE);
        rightDriveB.setZeroPowerBehavior(BRAKE);
        flywheel.setZeroPowerBehavior(BRAKE);
        telemetry.addData("Status", "initialized");
        telemetry.update();
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        double rightPower;
        double leftPower;
        double rightPowerB;
        double leftPowerB;
        double drive;
        double turn;
        double max;
        boolean slow;
        boolean fast;

        turn = -gamepad1.left_stick_y;
        drive = gamepad1.right_stick_x;

        leftPower = drive + turn;
        rightPower = drive - turn;
        leftPowerB = drive + turn;
        rightPowerB = drive - turn;

        // Normalize the values so neither exceed +/- 1.0
        max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
            leftPowerB /= max;
            rightPowerB /= max;
        }

        // I want to burn the Math statement so
        // so so so so so so
        // so so so SO SOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
        // much.
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        leftDriveB.setPower(leftPowerB);
        rightDriveB.setPower(rightPowerB);
//clockwise = forward
//Toggles FlyWheel
        if (gamepad1.x) {
            flywheeling = true;
            flywheel.setPower(0.5);
            telemetry.addLine("SPINNING");
        }
        if (gamepad1.y) {
            flywheeling = true;
            flywheel.setPower(0.67);
            telemetry.addLine("SPINNING");
        }
        if (gamepad1.b) {
            leftFeeder.setPower(-1);
            rightFeeder.setPower(1);
            flywheel.setPower(-0.5);
            telemetry.addLine("REVERSE BACKUP ON");
        }
        if (gamepad1.right_bumper) {
            flywheeling = false;
            leftFeeder.setPower(0);
            rightFeeder.setPower(0);
            flywheel.setPower(0);
            telemetry.addLine("STOPPING");
        }
        // Make it that different button = different flywheel speed
        // Toggles feeders on and off every 1/2 second
        if (gamepad1.left_trigger >= 0.1) {
            if (!isShooting) {
                // The trigger was just pulled, start the timer
                shootTimer.reset();
            }
            // Feed in the first 1/2 of each second, stop in the second half
            if (shootTimer.milliseconds() % 3575 < 200) {
                if (flywheeling) {
                    leftFeeder.setPower(1);
                    rightFeeder.setPower(1);
                    telemetry.addLine("RUNNING FEEDER");
                }
            } else {
                leftFeeder.setPower(0);
                rightFeeder.setPower(0);
                telemetry.addLine("PAUSING FEEDER");
            }
            isShooting = true;
        } else {
            isShooting = false;
            leftFeeder.setPower(0);
            rightFeeder.setPower(0);
        }

        telemetry.update();
    }
}
