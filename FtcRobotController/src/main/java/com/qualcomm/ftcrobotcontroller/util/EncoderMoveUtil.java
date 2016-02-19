package com.qualcomm.ftcrobotcontroller.util;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Karine on 2/13/2016.
 */
public class EncoderMoveUtil {

    DcMotor motorLeft;
    DcMotor motorRight;
    ModernRoboticsI2cGyro sensorGyro;
    public Telemetry telemetry;
    int counter;
    boolean correctPos;

    public EncoderMoveUtil(DcMotor motorLeft, DcMotor motorRight, Telemetry telemetry){
        this.motorLeft=motorLeft;
        this.motorRight= motorRight;
        this.telemetry=telemetry;
    }

    public EncoderMoveUtil(DcMotor motorLeft, DcMotor motorRight, ModernRoboticsI2cGyro sensorGyro,Telemetry telemetry){
        this.motorLeft=motorLeft;
        this.motorRight=motorRight;
        this.sensorGyro=sensorGyro;
        this.telemetry=telemetry;
    }

     public int cmToEncoderTicks(double cm) {
        double d = 2.54 * 5;
        double pi = 3.1415;
        double encoderConstant = 1440;
        double rotationConstant = d * pi;
        Double doubleEncoderTicks = (cm * (1 / rotationConstant)) * encoderConstant;
        int encoderTicks = doubleEncoderTicks.intValue();
        return encoderTicks;
    }

     public void stopMotors() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     public  int angleToEncoderTicks(double turnAmount) {

        double s = ((turnAmount) / 360 * (2 * Math.PI)) * (17.51 / 2);
        double cir = 4 * Math.PI; //4in wheels diameter
        double numOfRotations = s / cir;
        Double encoderTicks = numOfRotations * 1440;
        int returnEncoderTicks = (encoderTicks.intValue())*2;
        return returnEncoderTicks;
    }

    public void turnCC(double turnAngle, double speed) {

        int previousPosition = motorLeft.getCurrentPosition();

        int leftTicks = angleToEncoderTicks(turnAngle);
        telemetry.addData("Now turning", 5);
        telemetry.addData("leftTicks", leftTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        telemetry.addData("previous Position", previousPosition);

        while (Math.abs(motorLeft.getCurrentPosition()- previousPosition)< Math.abs(leftTicks)) // || motorLeft.getCurrentPosition()<rightTicks)
        {
            motorRight.setPower(-speed);
            motorLeft.setPower(speed);
            telemetry.addData("Now turning", 5);
            telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        }
        telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
        stopMotors();
    }

   public void turnC(double turnAngle, double speed) {

        int previous = motorLeft.getCurrentPosition();
        int leftTicks = angleToEncoderTicks(turnAngle);
        telemetry.addData("Now turning", 6);
        telemetry.addData("leftTicks", leftTicks);
        while (Math.abs(motorLeft.getCurrentPosition()-previous) <Math.abs(leftTicks))// || motorLeft.getCurrentPosition()<rightTicks)
        {
            motorRight.setPower(speed);
            motorLeft.setPower(-speed);
            telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        }
        telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
        stopMotors();
    }

    public void forward(double disInCm, double speed) {
        int current = motorLeft.getCurrentPosition();
        int disInEncoderTicks = cmToEncoderTicks(disInCm);
        while (Math.abs(motorLeft.getCurrentPosition()) < disInEncoderTicks + Math.abs(current)) {
            telemetry.addData("Centimeters:", disInCm);
            telemetry.addData("Encoder Ticks:", disInEncoderTicks);
            telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
            telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
            motorLeft.setPower(-speed);
            motorRight.setPower(-speed);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void backward(double disInCm, double speed) {
        int current = motorLeft.getCurrentPosition();
        int disInEncoderTicks = cmToEncoderTicks(disInCm);
        while (Math.abs(Math.abs(motorLeft.getCurrentPosition())-Math.abs(current)) < Math.abs(disInEncoderTicks)) {
            telemetry.addData("Centimeters:", disInCm);
            telemetry.addData("Encoder Ticks:", disInEncoderTicks);
            telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
            telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
            motorLeft.setPower(speed);
            motorRight.setPower(speed);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void checkAngleC(int targetAngle, int aZero, int threshold) {
        counter=0;
        correctPos=false;
        while(counter<=threshold&&correctPos==false) {
            int angleDifference = Math.abs(sensorGyro.getIntegratedZValue()) - (Math.abs(aZero - targetAngle));
            telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
            telemetry.addData("angleDifference", angleDifference);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (angleDifference < -2) {
                turnC(angleDifference, 0.5);
                correctPos=false;
                counter++;
            } else if (angleDifference > 2) {
                turnCC(angleDifference, 0.5);
                correctPos=false;
                counter++;
            }else{
                correctPos=true;
            }
        }
    }

   public void checkAngleCC(int targetAngle, int aZero, int threshold) {
        counter = 0;
        correctPos = false;
        while (counter <= threshold && correctPos == true) {
            int angleDifference = Math.abs(sensorGyro.getIntegratedZValue()) - (Math.abs(aZero + targetAngle));
            telemetry.addData("IZV", sensorGyro.getIntegratedZValue());
            telemetry.addData("angleDifference", angleDifference);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (angleDifference < -2) {
                turnCC(angleDifference, 0.5);
                counter++;
                correctPos = false;
            } else if (angleDifference > 2) {
                turnC(angleDifference, 0.5);
                counter++;
                correctPos = false;
            } else {
                correctPos = true;
            }
        }
    }
}
