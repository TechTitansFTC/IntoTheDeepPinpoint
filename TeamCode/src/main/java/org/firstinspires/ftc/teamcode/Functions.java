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
    };
    SpecimenPickupState specimenPickup = SpecimenPickupState.START_SHOULDER;

    public enum SpecimenPulldownState {
        PULLDOWN_SHOULDER,
        PULLDOWN_ELBOW,
        PULLDOWN_WRIST,
        PULLDOWN_CLAW
    };
    SpecimenPulldownState specimenPulldown = SpecimenPulldownState.PULLDOWN_SHOULDER;

    public enum SpecimenScoreState {
        SCORE_SHOULDER,
        SCORE_ELBOW,
        SCORE_WRIST,
        SCORE_CLAW
    };
    SpecimenScoreState specimenScore = SpecimenScoreState.SCORE_SHOULDER;


    public final String[] MAKESHIFT = {"DOWN", "CLOSE", "UP", "SCORE"};
    public int MAKESHIFT_pos = 0;
    public int lower_pos = 0;
    public int raise_pos = 0;
    public int up = 0;
    public int down = 0;

    ElapsedTime timer = new ElapsedTime();


    public Functions (HardwareMap map) {
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
                break;
        }
    }

    public void pulldown(boolean a) {
        switch (specimenPulldown) {
            case PULLDOWN_SHOULDER:
                if (a) {
                    outtake.shoulderPulldown();
                    specimenPickup = SpecimenPulldownState.PULLDOWN_ELBOW;
                }
                break;
            case PULLDOWN_ELBOW:
                if (timer.milliseconds() > 100) {
                    outtake.elbowStart();
                    specimenPickup = SpecimenPickupState.START_WRIST;
                }
                break;
            case PULLDOWN_WRIST:
                if (timer.milliseconds() > 50) {
                    outtake.wristStart();
                    specimenPickup = SpecimenPickupState.START_CLAW;
                }
                break;
            case PULLDOWN_CLAW:
                if (outtake.claw.getPosition() == outtake.CLAW_CLOSE) {
                    outtake.clawOpen();
                }
                specimenPickup = SpecimenPickupState.START_SHOULDER;
                break;
        }
    }


    public void specimenScore(boolean start) {
        switch (specimenScore) {
            case SPECIMEN_PREP:
                if (start) {
                    specimenScore = SpecimenScoreState.SPECIMEN_LOWER;
                }
                break;
            case SPECIMEN_LOWER:
                //TODO: slightly lower the slides
                break;
            case SPECIMEN_RELEASE:
                outtake.clawOpen();
                specimenScore = SpecimenScoreState.SPECIMEN_RETRACT;
                break;
            case SPECIMEN_RETRACT:
                //TODO: add the code to retract to pos where pickup starts from
                break;
        }
    }
    public void fsmup(){
        switch (up){
            case 0:
                outtake.clawClose();
                up++;
                break;
            case 1:
                outtake.shoulderScore();
                up++;
                break;

            case 2:
                outtake.elbowScore();
                up++;
                break;

            case 3:
                outtake.wristScore();
                up = 0;
                break;

            default: up = 0;
        }
    }
    public void fsmdn(){
        switch (down){
            case 0:
                outtake.shoulderPullDown();
                up++;
                break;

            case 1:
                outtake.clawOpen();
                up++;
                break;

            case 2:
                outtake.shoulderScore();
                up++;
                break;

            case 3:
                outtake.wristStart();
                up++;
                break;

            case 4:
                outtake.elbowStart();
                up++;
                break;

            case 5:
                outtake.shoulderStart();
                up=0;
                break;

            default: up = 0;
        }
    }
}

//    public String MAKESHIFT(boolean up, boolean down) {
//        // {"DOWN", "CLOSE", "UP", "SCORE"}
//        boolean advance = false;
//        switch (MAKESHIFT_pos) {
//            case 0:
//                //TODO: code to make it go down (need to code in Outtake)
//                switch (lower_pos) {
//                    case 0:
//                        outtake.setElbow(1); // needs to be changed
//                        lower_pos++;
//                        break;
//                    case 1:
//                        outtake.wristScore();
//                        if (outtake.checkElbow(1)) {
//                            lower_pos++;
//                        }
//                        break;
//                    case 2:
//                        outtake.setElbow(2); // needs to be set
//                        lower_pos = 3;
//                        break;
//                    case 3:
//                        break;
//                }
//                if (up && lower_pos == 3) {
//                    advance = true;
//                }
//                break;
//            case 1:
//                outtake.clawClose();
//                if (outtake.getClawClosed()) {
//                    advance = true;
//                    raise_pos = 0;
//                }
//                break;
//            case 2:
//                //TODO: code to make it go up (need to code in Outtake)
//                switch (raise_pos) {
//                    case 0:
//                        outtake.setElbow(3); // needs to be changed
//                        raise_pos++;
//                        break;
//                    case 1:
//                        outtake.wristScore();
//                        if (outtake.checkElbow(3)) {
//                            raise_pos++;
//                        }
//                        break;
//                    case 2:
//                        outtake.setElbow(4); // needs to be set
//                        raise_pos = 3;
//                        break;
//                    case 3:
//                        break;
//                }
//                if (down && raise_pos == 3) {
//                    advance = true;
//                }
//                break;
//            case 3:
//
//                if (!outtake.getClawClosed()) {
//                    MAKESHIFT_pos = 0;
//                    lower_pos = 0;
//                }
//                break;
//        }
//
//        if (advance) {
//            MAKESHIFT_pos++;
//        }
//
//        return MAKESHIFT[MAKESHIFT_pos];
//    }

//    public void slideControl (boolean up, boolean down, boolean out, boolean in) {
//        slides.slidesControl(up, down, out, in);
//    }
//}
