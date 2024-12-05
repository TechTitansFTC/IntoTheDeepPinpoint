//package org.firstinspires.ftc.teamcode;
//
//import java.lang.Math;
//import java.sql.Time;
//import java.util.Timer;
//
//public class PDFL {
//    private double kP = 0.0;
//    private double kD = 0.0;
//    private double kF = 0.0;
//    private double kL = 0.0;
//
//    private double deadZone = 0.0;
//
//    private boolean homed = false;
//    private double homedConstant = 0.0;
//
//    private Timer timer = new Timer();
//
//    private RingBuffer timeBuffer = new RingBuffer(3, 0.0);
//    private RingBuffer errorBuffer = new RingBuffer(3, 0.0);
//
//    public PDFL(double kP, double kD, double kF, double kL) {
//        this.kP = kP;
//        this.kD = kD;
//        this.kF = kF;
//        this.kL = kL;
//    }
//
//    public void updateConstants(double kP, double kD, double kF, double kL) {
//        this.kP = kP;
//        this.kD = kD;
//        this.kF = kF;
//        this.kL = kL;
//    }
//
//    public void setDeadZone(double deadZone) {
//        this.deadZone = deadZone;
//    }
//
//    public void setHomed(boolean homed) {
//        this.homed = homed;
//    }
//
//    public void setHomedConstant(double constant) {
//        homedConstant = constant;
//    }
//
//    public void reset() {
//        timer.reset();
//        timeBuffer.fill(0.0);
//        errorBuffer.fill(0.0);
//    }
//
//    public double run(double error) {
//        if (homed) {
//            return homedConstant;
//        }
//
//        double time = timer.getTime();
//
//        double previousTime = timeBuffer.getValue(time);
//        double previousError = errorBuffer.getValue(error);
//
//        double deltaTime = time - previousTime;
//        double deltaError = error - previousError;
//
//        //if PDFL hasn't been updated, reset it
//        if (deltaTime > 200) {
//            reset();
//            return run(error);
//        }
//
//        double p = pComponent(error);
//        double d = dComponent(deltaError, deltaTime);
//        double f = fComponent();
//        double l = lComponent(error);
//
//        double response = p + d + f + l;
//
//        if (Math.abs(error) < deadZone) {
//            response = p + d + f; //same response but with no lower limit
//        }
//
//        return response;
//    }
//
//    private double pComponent(double error) {
//        double response = kP * error;
//        return response;
//    }
//
//    private double dComponent(double delta_error, double delta_time) {
//        double derivative = delta_error / delta_time;
//        double response = derivative * kD;
//        return response;
//    }
//
//    private double fComponent() {
//        double response = kF;
//        return response;
//    }
//
//    private double lComponent(double error) {
//        //returns direction of value (if value is negative will return -1, if positive will return 1, if 0 will return 0)
//        double direction = Math.signum(error);
//        double response = direction * kL;
//        return response;
//    }
//}