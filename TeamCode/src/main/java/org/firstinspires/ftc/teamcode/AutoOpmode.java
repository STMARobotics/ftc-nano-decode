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

@Autonomous(name = "RC", group = "StarterBot")
public class AutoOpmode extends LinearOpMode {

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

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            flywheel.setPower(0.55);
            myElapsedTime.reset();
            // Put run blocks here.
            while (opModeIsActive()) {
                if (myElapsedTime.milliseconds() < 1000) {
                    left_drive.setPower(0.5);
                    right_drive.setPower(0.5);
                    left_driveB.setPower(0.5);
                    right_driveB.setPower(0.5);
                } else {
                    left_drive.setPower(0);
                    right_drive.setPower(0);
                    left_driveB.setPower(0);
                    right_driveB.setPower(0);
                }
                if (myElapsedTime.milliseconds() > 4000 && myElapsedTime.milliseconds() < 13000) {
                    if (!isShooting) {
                        // The trigger was just pulled, start the timer
                        shootTimer.reset();
                    }
                    // Feed in the first 1/2 of each second, stop in the second half
                    if (shootTimer.milliseconds() % 3575 < 200) {
                        isShooting = true;
                        leftFeeder.setPower(-1);
                        rightFeeder.setPower(-1);
                        telemetry.addLine("RUNNING FEEDER");
                    } else {
                        leftFeeder.setPower(0);
                        rightFeeder.setPower(0);
                        telemetry.addLine("PAUSING FEEDER");
                    }
                    if (myElapsedTime.milliseconds() > 13000 && myElapsedTime.milliseconds() < 17000) {
                        left_driveB.setPower(1);
                        right_driveB.setPower(-1);
                    }
                }
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}
