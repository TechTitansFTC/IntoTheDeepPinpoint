package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

public class Functions {
    private Outtake outtake;
    private Intake intake;
    private Hang hang;
    private Differential slides;

    public enum SpecimenPickupState {
        START_SHOULDER,
        START_ELBOW,
        START_WRIST,
        START_CLAW
    }

    SpecimenPickupState specimenPickup = SpecimenPickupState.START_SHOULDER;

    public enum SpecimenPulldownState {
        PULLDOWN_SHOULDER,
        PULLDOWN_ELBOW,
        PULLDOWN_WRIST,
        PULLDOWN_CLAW
    }

    SpecimenPulldownState specimenPulldown = SpecimenPulldownState.PULLDOWN_SHOULDER;

    public enum SpecimenScoreState {
        SCORE_SHOULDER,
        SCORE_ELBOW,
        SCORE_WRIST,
        SCORE_CLAW
    }

    SpecimenScoreState specimenScore = SpecimenScoreState.SCORE_SHOULDER;

    public final String[] MAKESHIFT = {"DOWN", "CLOSE", "UP", "SCORE"};
    public int MAKESHIFT_pos = 0;
    public int lower_pos = 0;
    public int raise_pos = 0;
    public int up = 0;
    public int down = 0;

    ElapsedTime timer = new ElapsedTime();

    boolean x, a, b = false;

    public Functions(HardwareMap map) {
        slides = new Differential(map);
        intake = new Intake(map);
        hang = new Hang(map);
        outtake = new Outtake(map);
    }

    public void init() {
        slides.init();
        hang.init();
        intake.init();
        outtake.init();
        timer.reset();
    }

    //left bumper - claw open
    //right bumper - claw close
    //pickup to ram position - a
    // ram posiion to score - b
    // score to start position - x;

    public void start(boolean x) {
        switch (specimenPickup) {
            case START_SHOULDER:
                if (x) {
                    outtake.shoulderStart();
                    specimenPickup = SpecimenPickupState.START_ELBOW;
                }
                break;
            case START_ELBOW:
                if (timer.milliseconds() > 100) {
                    outtake.elbowStart();
                    specimenPickup = SpecimenPickupState.START_WRIST;
                }
                break;
            case START_WRIST:
                if (timer.milliseconds() > 50) {
                    outtake.wristStart();
                    specimenPickup = SpecimenPickupState.START_CLAW;
                }
                break;
            case START_CLAW:
                if (outtake.claw.getPosition() == outtake.CLAW_CLOSE) {
                    outtake.clawOpen();
                }
                specimenPickup = SpecimenPickupState.START_SHOULDER;
                x = false;
                break;
        }
    }

    public void pulldown(boolean a) {
        switch (specimenPulldown) {
            case PULLDOWN_SHOULDER:
                if (a) {
                    outtake.shoulderPullDown();
                    specimenPulldown = SpecimenPulldownState.PULLDOWN_ELBOW;
                }
                break;
            case PULLDOWN_ELBOW:
                if (timer.milliseconds() > 100) {
                    outtake.elbowPulldown();
                    specimenPulldown = SpecimenPulldownState.PULLDOWN_WRIST;
                }
                break;
            case PULLDOWN_WRIST:
                if (timer.milliseconds() > 50) {
                    outtake.wristPulldown();
                    specimenPulldown = SpecimenPulldownState.PULLDOWN_CLAW;
                }
                break;
            case PULLDOWN_CLAW:
                if (outtake.claw.getPosition() == outtake.CLAW_OPEN) {
                    outtake.clawClose();
                }
                specimenPulldown = SpecimenPulldownState.PULLDOWN_ELBOW;
                a = false;
                break;
        }
    }

    public void score(boolean b) {
        switch (specimenScore) {
            case SCORE_SHOULDER:
                if (b) {
                    outtake.shoulderScore();
                    specimenScore = SpecimenScoreState.SCORE_ELBOW;
                }
                break;
            case SCORE_ELBOW:
                if (timer.milliseconds() > 100) {
                    outtake.elbowScore();
                    specimenScore = SpecimenScoreState.SCORE_WRIST;
                }
                break;
            case SCORE_WRIST:
                if (timer.milliseconds() > 50) {
                    outtake.wristScore();
                    specimenScore = SpecimenScoreState.SCORE_CLAW;
                }
                break;
            case SCORE_CLAW:
                if (outtake.claw.getPosition() == outtake.CLAW_CLOSE) {
                    outtake.clawOpen();
                }
                specimenScore = SpecimenScoreState.SCORE_SHOULDER;
                b = false;
                break;
        }
    }

    public void openClaw() {
        outtake.clawOpen();
    }

    public void closeClaw() {
        outtake.clawClose();
    }
}