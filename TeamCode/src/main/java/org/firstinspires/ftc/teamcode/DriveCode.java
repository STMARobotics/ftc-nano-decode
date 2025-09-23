//67

package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "DriveCode", group = "StarterBot")
//@disabled

public class DriveCode extends OpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor launchDrive = null;

    private CRServo spinnySpinL = null;
    private CRServo spinnySpinR = null;

    public void init() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        launchDrive = hardwareMap.get(DcMotor.class, "launch_drive");

        spinnySpinL = hardwareMap.get(CRServo.class, "spinny_SpinL");
        spinnySpinR = hardwareMap.get(CRServo.class, "spinny_SpinR");


        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        launchDrive.setDirection(DcMotor.Direction.FORWARD);

        spinnySpinL.setDirection(CRServo.Direction.FORWARD);
        spinnySpinR.setDirection(CRServo.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
        telemetry.addData("Status", "initialized");
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



        //Toggles FlyWheel
        if (gamepad1.right_trigger >= 0.1) {
            launchDrive.setPower(0.75);
            System.out.println("SPINNIG");
        }
        if (gamepad1.right_bumper) {
            launchDrive.setPower(0);
            System.out.println("STOPPING");
        }
        //Toggles Servo
        if (gamepad1.left_trigger >= 0.1) {
            spinnySpinL.setPower(1);
            spinnySpinR.setPower(1);
            System.out.println("SERVO_WORKING");
        }
        if (gamepad1.left_bumper) {
                spinnySpinL.setPower(0);
                spinnySpinR.setPower(0);
                System.out.println("SERVO_STOPPING");
            }
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        }
    }
