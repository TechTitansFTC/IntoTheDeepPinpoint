package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Functions {
    private Outtake outtake;
    private Intake intake;
    private Hang hang;
    private Differential slides;

    public enum SpecimenPickupState {
        SPECIMEN_START,
        SPECIMEN_EXTEND,
        SPECIMEN_PICKUP,
        SPECIMEN_RETRACT,
        SPECIMEN_RAISE,
        SPECIMEN_PREP
    };
    SpecimenPickupState specimenPickup = SpecimenPickupState.SPECIMEN_START;

    public enum SpecimenScoreState {
        SPECIMEN_PREP,
        SPECIMEN_LOWER,
        SPECIMEN_RELEASE,
        SPECIMEN_RETRACT
    };
    SpecimenScoreState specimenScore = SpecimenScoreState.SPECIMEN_PREP;


    public final String[] MAKESHIFT = {"DOWN", "CLOSE", "UP", "SCORE"};
    public int MAKESHIFT_pos = 0;
    public int lower_pos = 0;
    public int raise_pos = 0;
    public int up = 0;
    public int down = 0;

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
//        outtake.init();
    }


    /*
    start -> send to down (need to change function)
    extend -> need code
    pickup -> if within range (probably needs to be moved to extend), then if extended, then close the claw
    retract -> need code (bring the Outtake back into the robot with specimen on)
    raise -> send up (need to change function)
    prep -> prep for scoring (needs code)
     */
    public void specimenPickUp(boolean start) {
        switch (specimenPickup) {
            case SPECIMEN_START:
                if (start) {
                    slides.outtakeDown();
                    specimenPickup = SpecimenPickupState.SPECIMEN_EXTEND;
                }
                break;
            case SPECIMEN_EXTEND:
                //TODO: add the code to extend
                specimenPickup = SpecimenPickupState.SPECIMEN_PICKUP;
                break;
            case SPECIMEN_PICKUP:
                //TODO: implement differential encoder vars
                if (slides.withinRange(1000, 10, true) && slides.withinRange(1000, 10, false)) {
                    outtake.clawClose();
                    specimenPickup = SpecimenPickupState.SPECIMEN_RETRACT;
                }
                break;
            case SPECIMEN_RETRACT:
                //TODO: add the code to retract
                break;
            case SPECIMEN_RAISE:
                slides.outtakeUp();
                specimenPickup = SpecimenPickupState.SPECIMEN_PREP;
                break;
            case SPECIMEN_PREP:
                //TODO: prep for scoring
                specimenPickup = SpecimenPickupState.SPECIMEN_START;
                break;
        }
    }


    /*
    prep - position from pickup
    lower - get the specimen onto bar
    release - release it
    retract - retract to position for pickup start
     */
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
