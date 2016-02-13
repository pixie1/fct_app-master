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
            hook= hardwareMap.servo.get("servo_6");
            motorHook = hardwareMap.dcMotor.get("motor_3");

            sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
            sensorGyro.calibrate();
            while(sensorGyro.isCalibrating()){
                Thread.sleep(200);
            }
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
            turnC(88,0.5);

            //go straight

            forward(200,0.5);
            Thread.sleep(100);
            //turn to face beacon
            turnC(90, 0.5);
            //back up slightly
            //find and align on line
            //go forward till blue wheels are on line
            //extend hook
            //drop climber
            //bring arm backup
            //backup

        }

        int angleToEncoderTicks(double turnAmount){

            double s = ((turnAmount)/360*(2*Math.PI))*(17.51/2);
            double cir = 4*Math.PI;
            double numOfRotations = s/cir;
            Double encoderTicks = numOfRotations*1440;
            int returnEncoderTicks = encoderTicks.intValue();
            return returnEncoderTicks;
        }

        int cmToEncoderTicks(double cm){
            double d = 2.54*5;
            double pi = 3.1415;
            double encoderConstant = 1440;
            double rotationConstant = d*pi;
            Double doubleEncoderTicks = (cm*(1/rotationConstant))*encoderConstant;
            int encoderTicks= doubleEncoderTicks.intValue();
            return encoderTicks;
        }

        void forward(double disInCm, double speed)
        {
            int current= motorLeft.getCurrentPosition();
            int disInEncoderTicks = cmToEncoderTicks(disInCm);
            while(Math.abs(motorLeft.getCurrentPosition())<disInEncoderTicks+Math.abs(current)){
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

            int currentPosition=motorLeft.getCurrentPosition();

            int leftTicks = angleToEncoderTicks(turnAngle);
            int rightTicks = angleToEncoderTicks(-turnAngle);
            telemetry.addData("Now turning", 6);
            telemetry.addData("leftTicks", leftTicks);
            telemetry.addData("rightTicks", rightTicks);
            telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
            telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
            telemetry.addData("speed:", speed);
            while(motorLeft.getCurrentPosition()<leftTicks+Math.abs(currentPosition))// || motorLeft.getCurrentPosition()<rightTicks)
            {
                motorRight.setPower(-speed);
                motorLeft.setPower(speed);
            }
            motorLeft.setPower(0);
            motorRight.setPower(0);

        }

    void turnC(double turnAngle, double speed)
    {
        int current= motorLeft.getCurrentPosition();
        int leftTicks = angleToEncoderTicks(turnAngle);
        int rightTicks = angleToEncoderTicks(-turnAngle);
        telemetry.addData("Now turning", 6);
        telemetry.addData("leftTicks", leftTicks);
        telemetry.addData("rightTicks", rightTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
        telemetry.addData("speed:", speed);
        while(Math.abs(motorLeft.getCurrentPosition())<leftTicks+Math.abs(current))// || motorLeft.getCurrentPosition()<rightTicks)
        {
            motorRight.setPower(speed);
            motorLeft.setPower(-speed);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}


