package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    private Servo shoulderL; // main U-D
    private Servo shoulderR; // main U-D
    private Servo elbow; // subset U-D
    private Servo wrist; // L-R
    private Servo claw;

    //TODO: FIND ALL SERVO VALUES
    private final double SHOULDER_L_SCORE = 0.2;//arm outside robot
    private final double SHOULDER_L_START = 0.95;//arm in robot

    private final double SHOULDER_R_SCORE = 0.8;//same but with othe arm

    private final double SHOULDER_R_START = 0.05;

    private final double SHOULDER_R_PULLDOWN = 1;//same but with othe arm

    private final double SHOULDER_L_PULLDOWN = 0.0;
    private final double ELBOW_START= 0.0;
    private final double ELBOW_SCORE= 0.75;

    private final double WRIST_SCORE = 0.09;
    private final double WRIST_START = 0.73;
    private final double CLAW_CLOSE = 0.5;//closes claw
    private final double CLAW_OPEN = 0.0;//open claw

    public Outtake(HardwareMap m){
        this.shoulderL = m.servo.get("rotateML"); //port 6 CH
        this.shoulderR = m.servo.get("rotateMR"); //port 0 EH
        this.elbow = m.servo.get("elbow"); //port 5 CH
        this.wrist = m.servo.get("wrist"); //port 2 CH
        this.claw = m.servo.get("claw"); //port 1 CH
    }

    public void init(){
        shoulderL.setPosition(SHOULDER_L_START);
        shoulderR.setPosition(SHOULDER_R_START);
        wrist.setPosition(WRIST_START);
        claw.setPosition(CLAW_OPEN);
        elbow.setPosition(ELBOW_START);
    }
    public void shoulderScore(){
        shoulderL.setPosition(SHOULDER_L_SCORE);
        shoulderR.setPosition(SHOULDER_R_SCORE);
    }
    public void shoulderStart(){
        shoulderL.setPosition(SHOULDER_L_START);
        shoulderR.setPosition(SHOULDER_R_START);
    }
    public void shoulderPullDown(){
        shoulderL.setPosition(SHOULDER_L_PULLDOWN);
        shoulderR.setPosition(SHOULDER_R_PULLDOWN);
    }
    public void wristScore(){
        wrist.setPosition(WRIST_SCORE);
    }
    public void wristStart(){
        wrist.setPosition(WRIST_START);
    }

    public void clawClose(){
        claw.setPosition(CLAW_CLOSE);
    }
    public void clawOpen(){
        claw.setPosition(CLAW_OPEN);
    }

    public void elbowStart(){
        elbow.setPosition(ELBOW_START);
    }
    public void elbowScore(){
        elbow.setPosition(ELBOW_SCORE);
    }


}
