package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "DriveCode", group = "StarterBot")
public class DriveCode extends OpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor flywheel = null;

    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;

    private ElapsedTime shootTimer = new ElapsedTime();
    private boolean isShooting = false;

    public void init() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        flywheel = hardwareMap.get(DcMotor.class, "launch_drive");

        leftFeeder = hardwareMap.get(CRServo.class, "spinny_SpinL");
        rightFeeder = hardwareMap.get(CRServo.class, "spinny_SpinR");


        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        flywheel.setDirection(DcMotor.Direction.FORWARD);

        leftFeeder.setDirection(CRServo.Direction.FORWARD);
        rightFeeder.setDirection(CRServo.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
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
        double drive;
        double turn;
        double max;

        drive = gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x;

        leftPower  = drive + turn;
        rightPower = drive - turn;

        // Normalize the values so neither exceed +/- 1.0
        max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (max > 1.0)
        {
            leftPower /= max;
            rightPower /= max;
        }

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        //Toggles FlyWheel
        if (gamepad1.right_trigger >= 0.1) {
            flywheel.setPower(0.75);
            telemetry.addLine("SPINNING");
        }
        if (gamepad1.right_bumper) {
            flywheel.setPower(0);
            telemetry.addLine("STOPPING");
        }

        // Toggles feeders on and off every 1/2 second
        if (gamepad1.left_trigger >= 0.1) {
            if (!isShooting) {
                // The trigger was just pulled, start the timer
                shootTimer.reset();
            }
            // Feed in the first 1/2 of each second, stop in the second half
            if (shootTimer.milliseconds() % 750 < 250) {
                leftFeeder.setPower(1);
                rightFeeder.setPower(1);
                telemetry.addLine("RUNNING FEEDER");
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
