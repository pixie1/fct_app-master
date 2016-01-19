/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * A simple example of a linear op mode that will approach an IR beacon
 */
public class MasterGyroAutonomous extends LinearOpMode {

    //final static double MOTOR_POWER = 0.15; // Higher values will cause the robot to move faster
    //final static double HOLD_IR_SIGNAL_STRENGTH = 0.20; // Higher values will cause the robot to follow closer
    DcMotor motorRight;
    DcMotor motorLeft;
    //Servo servo1;
    //Servo servo2;
    ModernRoboticsI2cGyro sensorGyro;


    @Override
    public void runOpMode() throws InterruptedException {
        sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        //servo1 = hardwareMap.servo.get("servo_1");
        //servo2 = hardwareMap.servo.get("servo_5");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);//makes the robot run straight.
        sensorGyro.calibrate();
        Thread.sleep(100);

        waitForStart();
        gyroTurn(45, .4);
        Thread.sleep(2000);
        gyroTurn(90, .8);
        while(true) {
            telemetry.addData("heading", sensorGyro.getHeading());
            telemetry.addData("integratedZValue", sensorGyro.getIntegratedZValue());
        }
    }

    void gyroTurn(int degrees, double speed) {
        //if(degrees<0){
            while(sensorGyro.getHeading()<degrees){
                motorLeft.setPower(speed);
                motorRight.setPower(-speed);
                telemetry.addData("heading:", sensorGyro.getHeading());
                telemetry.addData("speed:", speed);
                telemetry.addData("degrees:", degrees);
              // telemetry.addData("getRotation", sensorGyro.getRotation());
            }

        /*}else if(degrees>0){
            while(sensorGyro.getHeading()>-degrees){
                motorLeft.setPower(-speed);
                motorRight.setPower(speed);
            }*/
        //}
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }


    /*int angleToEncoderTicks(double turnAmount) {
        double s = ((turnAmount) / 360 * (2 * Math.PI)) * (17.51 / 2);
        double cir = 5 * Math.PI;
        double numOfRotations = s / cir;
        Double encoderTicks = numOfRotations * 1440;
        int returnEncoderTicks = encoderTicks.intValue();
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
        int disInEncoderTicks = cmToEncoderTicks(disInCm);
        while (motorLeft.getCurrentPosition() < disInEncoderTicks) {
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

    void turn(int desHead, double speed){
        if (desHead <= 180) {
            while (sensorGyro.getHeading() < desHead) {
                motorLeft.setPower(speed);
                motorRight.setPower(-speed);
            }
        } else {
            while (sensorGyro.getHeading() > desHead) {
                motorLeft.setPower(-speed);
                motorRight.setPower(speed);
            }
            motorRight.setPower(0);
            motorLeft.setPower(0);
        /*int leftTicks = angleToEncoderTicks(turnAngle);
        int rightTicks = angleToEncoderTicks(-turnAngle);
        telemetry.addData("Now turning", 6);
        telemetry.addData("leftTicks", leftTicks);
        telemetry.addData("rightTicks", rightTicks);
        telemetry.addData("Left Encoder at:", motorLeft.getCurrentPosition());
        telemetry.addData("Right Encoder at:", motorRight.getCurrentPosition());
        telemetry.addData("speed:", speed);
        while(motorLeft.getCurrentPosition()<leftTicks)// || motorLeft.getCurrentPosition()<rightTicks)
        {
            motorRight.setPower(-speed);
            motorLeft.setPower(speed);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
        motorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }*/
}
