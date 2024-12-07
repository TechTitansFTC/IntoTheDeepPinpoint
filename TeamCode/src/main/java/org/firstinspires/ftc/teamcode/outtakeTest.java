package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(group = "testing")
public class outtakeTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        Servo shoulderL = hardwareMap.get(Servo.class, "rotateML"); // main U-D
        Servo shoulderR = hardwareMap.get(Servo.class, "rotateMR"); // main U-D
        Servo elbowL = hardwareMap.get(Servo.class, "elbowL"); // subset U-D
        Servo elbowR = hardwareMap.get(Servo.class, "elbowR");
        Servo wrist = hardwareMap.get(Servo.class, "wrist"); // L-R
        Servo claw = hardwareMap.get(Servo.class, "claw");
        ElapsedTime timer = new ElapsedTime();
        String[] servos = {"Shoulders", "Elbows", "Wrist", "Claw"};

        waitForStart();
        if (isStopRequested()) {return;}
        shoulderL.setDirection(Servo.Direction.FORWARD);
        shoulderL.setDirection(Servo.Direction.FORWARD);
        shoulderL.setPosition(0);
        shoulderR.setPosition(1);
        elbowR.setPosition(0);
        elbowL.setPosition(0);
        wrist.setPosition(0);
        claw.setPosition(0);
        timer.reset();

        int testServoNum = 0;
        while (opModeIsActive()) {
            // 0 - shoulders, 1 - elbows, 2 - wrist, 3 - claw
            // need to check whether l and r will move in same way - i coded in a way it should work

            if (timer.milliseconds() > 300) {
                switch (testServoNum) {
                    case 0:
                        if (gamepad1.dpad_up) {
                            shoulderL.setPosition(shoulderL.getPosition() + 0.05);
                            shoulderR.setPosition(shoulderR.getPosition() - 0.05);
                        } else if (gamepad1.dpad_down) {
                            shoulderL.setPosition(shoulderL.getPosition() - 0.05);
                            shoulderR.setPosition(shoulderR.getPosition() + 0.05);
                        } else if (gamepad1.dpad_right) {
                            shoulderL.setPosition(shoulderL.getPosition() + 0.01);
                            shoulderR.setPosition(shoulderR.getPosition() - 0.01);
                        } else if (gamepad1.dpad_left) {
                            shoulderL.setPosition(shoulderL.getPosition() - 0.01);
                            shoulderR.setPosition(shoulderR.getPosition() + 0.01);
                        }
                        break;
                    case 1:
                        if (gamepad1.dpad_up) {
                            elbowL.setPosition(elbowL.getPosition() + 0.05);
                            elbowR.setPosition(elbowR.getPosition() - 0.05);
                        } else if (gamepad1.dpad_down) {
                            elbowL.setPosition(elbowL.getPosition() - 0.05);
                            elbowR.setPosition(elbowR.getPosition() + 0.05);
                        } else if (gamepad1.dpad_right) {
                            elbowL.setPosition(elbowL.getPosition() + 0.01);
                            elbowR.setPosition(elbowR.getPosition() - 0.01);
                        } else if (gamepad1.dpad_left) {
                            elbowL.setPosition(elbowL.getPosition() - 0.01);
                            elbowR.setPosition(elbowR.getPosition() + 0.01);
                        }
                        break;
                    case 2:
                        if (gamepad1.dpad_up) {
                            wrist.setPosition(wrist.getPosition() + 0.05);
                        } else if (gamepad1.dpad_down) {
                            wrist.setPosition(wrist.getPosition() - 0.05);
                        } else if (gamepad1.dpad_right) {
                            wrist.setPosition(wrist.getPosition() + 0.01);
                        } else if (gamepad1.dpad_left) {
                            wrist.setPosition(wrist.getPosition() - 0.01);
                        }
                        break;
                    case 3:
                        if (gamepad1.dpad_up) {
                            claw.setPosition(claw.getPosition() + 0.05);
                        } else if (gamepad1.dpad_down) {
                            claw.setPosition(claw.getPosition() - 0.05);
                        } else if (gamepad1.dpad_right) {
                            claw.setPosition(claw.getPosition() + 0.01);
                        } else if (gamepad1.dpad_left) {
                            claw.setPosition(claw.getPosition() - 0.01);
                        }
                        break;
                }
                timer.reset();
            }



            telemetry.addData("Testing Servo", servos[testServoNum]);

            telemetry.addData("pos", shoulderL.getPosition());
            telemetry.addData("pos", shoulderR.getPosition());
            telemetry.addData("pos", elbowL.getPosition());
            telemetry.addData("pos", wrist.getPosition());
            telemetry.addData("pos", claw.getPosition());
            telemetry.update();
            if (gamepad1.right_bumper) {
                timer.reset();
                testServoNum += 1;
                while (timer.milliseconds() < 250){

                }
            } else if (gamepad1.left_bumper) {
                timer.reset();
                testServoNum -= 1;
                while (timer.milliseconds() < 250){

                }
            }
            if (testServoNum >= servos.length) {
                testServoNum = 0;
            }
        }
    }
}
