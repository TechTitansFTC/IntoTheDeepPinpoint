package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous
public class S3_auton extends LinearOpMode {
    ElapsedTime timer;

    public void runOpMode() throws InterruptedException {
        Functions robot = new Functions(hardwareMap);
        MotorPower m = new MotorPower(hardwareMap);
        timer = new ElapsedTime();
        robot.init();
        waitForStart();
        if (isStopRequested()) return;


        if (opModeIsActive()) {
            //lift slides
            robot.autoScore();

            //move to bar
            m.move(1, 550);
            sleep(500);
            robot.autoPullDown();
            sleep(500);
            robot.autoStart();
            sleep(250);
            //push 3
            m.move(-0.7, 200);
            sleep(250);
            m.strafe(0.7, 500);
            sleep(250);
            m.move(0.7, 500);
            sleep(250);
            m.strafe(0.7, 500);
            sleep(250);
            m.move(-0.7, 800);
            sleep(250);
            m.move(-0.7, 200);
            sleep(250);
            m.strafe(0.7, 500);
            sleep(250);
            m.move(0.7, 500);
            sleep(250);
            m.strafe(0.7, 500);
            sleep(250);
            m.move(-0.7, 800);






        }
    }
}
