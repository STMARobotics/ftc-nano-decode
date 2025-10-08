package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Drive forward")
public class AutoOpmode extends LinearOpMode {

    private DcMotor left_drive;
    private DcMotor right_drive;

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
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
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
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}