package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "FX", group = "StarterBot")
public class QualFixAutoOpmode extends LinearOpMode {

    private final ElapsedTime shootTimer = new ElapsedTime();
    private boolean isShooting = false;

    private DcMotor left_drive;
    private DcMotor right_drive;
    private DcMotor left_driveB;
    private DcMotor right_driveB;
    private DcMotor flywheel;
    private CRServo leftFeeder;
    private CRServo rightFeeder;


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
        left_driveB = hardwareMap.get(DcMotor.class, "left_driveB");
        right_driveB = hardwareMap.get(DcMotor.class, "right_driveB");
        flywheel = hardwareMap.get(DcMotor.class, "launch_drive");
        rightFeeder = hardwareMap.get(CRServo.class, "right_Feeder");
        leftFeeder = hardwareMap.get(CRServo.class, "left_Feeder");
        leftFeeder.setDirection(CRServo.Direction.FORWARD);
        rightFeeder.setDirection(CRServo.Direction.REVERSE);

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            flywheel.setPower(0.525);
            myElapsedTime.reset();
            // Put run blocks here.
            while (opModeIsActive()) {
                if (myElapsedTime.milliseconds() < 1200) {
                    left_drive.setPower(-0.5);
                    right_drive.setPower(-0.5);
                    left_driveB.setPower(-0.5);
                    right_driveB.setPower(-0.5);
                } else if (myElapsedTime.milliseconds() > 1200 && myElapsedTime.milliseconds() < 4000) {
                    left_drive.setPower(0);
                    right_drive.setPower(0);
                    left_driveB.setPower(0);
                    right_driveB.setPower(0);
                }
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}



