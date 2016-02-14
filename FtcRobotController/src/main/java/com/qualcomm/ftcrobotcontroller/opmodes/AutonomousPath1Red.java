package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.util.EncoderMoveUtil;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Karine on 2/13/2016.
 */
public class AutonomousPath1Red extends LinearOpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorHook;
    Servo hook;
    ModernRoboticsI2cGyro sensorGyro;
    EncoderMoveUtil encoderMoveUtil;

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
        encoderMoveUtil= new EncoderMoveUtil(motorLeft, motorRight, sensorGyro);
        // wait for fcs to start the match
        waitForStart();

        //go straight
        encoderMoveUtil.forward(60, 0.5);
        encoderMoveUtil.stopMotors();

        //turn 45 parallel to red line
        encoderMoveUtil.turnC(45, 0.5);
        encoderMoveUtil.stopMotors();
        encoderMoveUtil.checkAngleC(45, 0);
        encoderMoveUtil.stopMotors();
        telemetry.addData("FIRST TURN DONE", 0000000);


        //go straight
//
        encoderMoveUtil.forward(200, 0.5);
        encoderMoveUtil.stopMotors();
//            //turn to face beacon
        int artificialZero = sensorGyro.getIntegratedZValue();
        Thread.sleep(100);
        encoderMoveUtil.turnC(45, 0.5);
        encoderMoveUtil.stopMotors();
        encoderMoveUtil.checkAngleC(45, artificialZero);
        telemetry.addData("SECOND TURN DONE", 00000000);
        //back up slightly
        //find and align on line
        //go forward till blue wheels are on line
        //extend hook
        //drop climber
        //bring arm backup
        //backup

    }
}

