package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Drive forward")
public class AutoOpmode extends LinearOpMode {

    private DcMotor left_drive;
    private DcMotor right_drive;
    private DcMotor mid_feeder;
    private CRServo left_servo;
    private CRServo right_servo;



    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        ElapsedTime myElapsedTime = new ElapsedTime();

        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        right_drive = hardwareMap.get(DcMotor.class,"right_drive");
        mid_feeder = hardwareMap.get(DcMotor.class, "mid_feeder");
        right_servo = hardwareMap.get(CRServo.class, "right_servo");
        left_servo = hardwareMap.get(CRServo.class, "left_servo");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            mid_feeder.setPower(1);
            myElapsedTime.reset();
            // Put run blocks here.
            while (opModeIsActive()) {
                if (myElapsedTime.milliseconds() < 1000) {
                    left_drive.setPower(0.5);
                    right_drive.setPower(-0.5);
                } else {
                    left_drive.setPower(0);
                    right_drive.setPower(0);
                }
                left_servo.setPower(1);
                right_servo.setPower(1);

                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}