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
    }


    /*
    start -> send to down (need to change function)
    extend -> need code
    pickup -> if within range (probably needs to be moved to extend), then if extended, then close the claw
    retract -> need code (bring the outtake back into the robot with specimen on)
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


    public String MAKESHIFT(boolean up, boolean down) {
        // {"DOWN", "CLOSE", "UP", "SCORE"}
        boolean advance = false;
        switch (MAKESHIFT_pos) {
            case 0:
                //TODO: code to make it go down (need to code in outtake)
                if (up) {
                    advance = true;
                }
                break;
            case 1:
                outtake.clawClose();
                if (outtake.getClawClosed()) {
                    advance = true;
                }
                break;
            case 2:

                if (down) {
                    advance = true;
                }
                break;
            case 3:

                if (!outtake.getClawClosed()) {
                    MAKESHIFT_pos = 0;
                }
                break;
        }

        if (advance) {
            MAKESHIFT_pos++;
        }

        return MAKESHIFT[MAKESHIFT_pos];
    }

    public void slideControl (boolean up, boolean down, boolean out, boolean in) {
        slides.slidesControl(up, down, out, in);
    }
}
