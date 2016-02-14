package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Karine on 2/13/2016.
 */
public class AutonomousPath1Red extends LinearOpMode {

    final static double MOTOR_POWER = 0.15; // Higher values will cause the robot to move faster
    final static double HOLD_IR_SIGNAL_STRENGTH = 0.20; // Higher values will cause the robot to follow closer

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorHook;
    Servo hook;
    ModernRoboticsI2cGyro sensorGyro;

    @Override
    public void runOpMode() throws InterruptedException {

        //Make sure the robot knows that the motors are there.
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        //makes the robot run straight.
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        hook = hardwareMap.servo.get("servo_6");
        motorHook = hardwareMap.dcMotor.get("motor_3");

        sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        sensorGyro.calibrate();
        telemetry.addData("calibrating", 0);
            Thread.sleep(5000);
        telemetry.addData("calibrated", 1);
        // wait for fcs to start the match
        waitForStart();

        //go straight

            double speed=0.5;
                int disInEncoderTicks = cmToEncoderTicks(60);
                while(Math.abs(motorLeft.getCurrentPosition())<disInEncoderTicks){
                 //   telemetry.addData("Centimeters:", 25);
                 //   telemetry.addData("speed:", speed);
                    telemetry.addData("Encoder Ticks:", disInEncoderTicks);
                    telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
                    telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
                    motorLeft.setPower(-speed);
                    motorRight.setPower(-speed);
                }
                motorLeft.setPower(0);
                motorRight.setPower(0);

        Thread.sleep(100);

        //turn 45 parallel to red line
        turnC(45, 0.5);
        stopMotors();
        checkAngle(45, 0);
        stopMotors();
        telemetry.addData("FIRST TURN DONE", 0000000);


        //go straight
//
           forward(200,0.5);
           stopMotors();
//            //turn to face beacon
        int artificialZero = sensorGyro.getIntegratedZValue();
        Thread.sleep(100);
        turnC(45, 0.5);
        stopMotors();
        checkAngle(45, artificialZero);
        telemetry.addData("SECOND TURN DONE", 00000000);
        //back up slightly
        //find and align on line
        //go forward till blue wheels are on line
        //extend hook
        //drop climber
        //bring arm backup
        //backup

    }

    void stopMotors() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int angleToEncoderTicks(double turnAmount) {

        double s = ((turnAmount) / 360 * (2 * Math.PI)) * (17.51 / 2);
        double cir = 4 * Math.PI;
        double numOfRotations = s / cir;
        Double encoderTicks = numOfRotations * 1440;
        int returnEncoderTicks = (encoderTicks.intValue())*2;
        return returnEncoderTicks;
    }

    int cmToEncoderTicks(double cm) {
        double d = 2.54 * 5;
        double pi = 3.1415;
        double encoderConstant = 1440;
        double rotationConstant = d * pi;
        Double doubleEncoderTicks = (cm * (1 / rotationConstant)) * encoderConstant;
        int encoderTicks = doubleEncoderTicks.intValue();
        return encoderTicks;
    }

    void forward(double disInCm, double speed) {
        int current = motorLeft.getCurrentPosition();
        int disInEncoderTicks = cmToEncoderTicks(disInCm);
        while (Math.abs(motorLeft.getCurrentPosition()) < disInEncoderTicks + Math.abs(current)) {
            telemetry.addData("Centimeters:", disInCm);
            telemetry.addData("speed:", speed);
            telemetry.addData("Encoder Ticks:", disInEncoderTicks);
            telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
            telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
            motorLeft.setPower(-speed);
            motorRight.setPower(-speed);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    void turnCC(double turnAngle, double speed) {

        int previousPosition = motorLeft.getCurrentPosition();

        int leftTicks = angleToEncoderTicks(turnAngle);
       // int rightTicks = angleToEncoderTicks(-turnAngle);
        telemetry.addData("Now turning", 6);
        telemetry.addData("leftTicks", leftTicks);
        //telemetry.addData("rightTicks", rightTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
       // telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
        telemetry.addData("current Position", previousPosition);
        while (Math.abs(Math.abs(motorLeft.getCurrentPosition())- Math.abs(previousPosition))< leftTicks) // || motorLeft.getCurrentPosition()<rightTicks)
        {
            motorRight.setPower(-speed);
            motorLeft.setPower(speed);
            telemetry.addData("Now turning", 6);
            telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

    }

    void turnC(double turnAngle, double speed) {
      //  double upperBound = sensorGyro.getIntegratedZValue() + (turnAngle / 2) + 1;
      //  double lowerBound = sensorGyro.getIntegratedZValue() + (turnAngle / 2) - 1;
        int x;
        int current = motorLeft.getCurrentPosition();
        int leftTicks = angleToEncoderTicks(turnAngle);
        telemetry.addData("Now turning", 6);
        telemetry.addData("leftTicks", leftTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
        telemetry.addData("speed:", speed);
        while (Math.abs(motorLeft.getCurrentPosition()) < leftTicks + Math.abs(current))// || motorLeft.getCurrentPosition()<rightTicks)
        {
            motorRight.setPower(speed);
            motorLeft.setPower(-speed);
        }
        stopMotors();


    }

    void checkAngle(int targetAngle, int aZero) {
        int angleDifference=  Math.abs(sensorGyro.getIntegratedZValue())-(Math.abs(aZero)+targetAngle);
        telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
        telemetry.addData("angleDifference", angleDifference);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(angleDifference<-2){
            turnC(angleDifference,0.5);
        }else if(angleDifference>2){
            turnCC(angleDifference,0.5);
        }
    }
}


/*while (true) {
        if (Math.abs(sensorGyro.getIntegratedZValue()) > Math.abs(lowerBound) && Math.abs(sensorGyro.getIntegratedZValue()) < Math.abs(upperBound)) {
        stopMotors();
        telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
        telemetry.addData("turnAngle", turnAngle);
        telemetry.addData("upperBound", upperBound);
        telemetry.addData("lowerBound", lowerBound);
        telemetry.addData("leftTicks", leftTicks);
        telemetry.addData("rightTicks", rightTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
        telemetry.addData("speed:", speed);
        break;
        } else {
        if (Math.abs(sensorGyro.getIntegratedZValue()) < Math.abs(lowerBound)) {
        motorLeft.setPower(0.5);
        motorRight.setPower(-0.5);
        } else if (Math.abs(sensorGyro.getIntegratedZValue()) > Math.abs(upperBound)) {
        motorLeft.setPower(-0.5);
        motorRight.setPower(0.5);
        } else {
        stopMotors();
        break;
        }
        telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
        telemetry.addData("turnAngle", turnAngle);
        telemetry.addData("upperBound", upperBound);
        telemetry.addData("lowerBound", lowerBound);
        telemetry.addData("leftTicks", leftTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
        telemetry.addData("speed:", "still turning");

        }
        }

        stopMotors();
        telemetry.addData("BROKE", turnAngle);
        telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
        telemetry.addData("turnAngle", turnAngle);
        telemetry.addData("upperBound", upperBound);
        telemetry.addData("lowerBound", lowerBound);
        telemetry.addData("leftTicks", leftTicks);
        telemetry.addData("rightTicks", rightTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
        telemetry.addData("speed:", speed);*/
