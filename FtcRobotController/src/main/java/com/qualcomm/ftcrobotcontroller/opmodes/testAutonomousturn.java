package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.util.EncoderMoveUtil;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Karine on 2/14/2016.
 */
public class testAutonomousturn extends LinearOpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorHook;
    Servo hook;
    ModernRoboticsI2cGyro sensorGyro;
    EncoderMoveUtil move;

    @Override
    public void runOpMode() throws InterruptedException {

        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        //makes the robot run straight.
        motorLeft.setDirection(DcMotor.Direction.REVERSE);sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        sensorGyro.calibrate();
        telemetry.addData("calibrating", 0);
        Thread.sleep(5000);
        telemetry.addData("calibrated", 1);
         move= new EncoderMoveUtil(motorLeft, motorRight, sensorGyro,telemetry);

        waitForStart();

        move.turnCC(60, 0.5);
        move.stopMotors();
       Thread.sleep(5000);
       move.checkAngleCC(30, 0);

     //   Thread.sleep(5000);

     //   int artificialZero = sensorGyro.getIntegratedZValue();

    //    move.turnCC(45,0.5);
      //  move.stopMotors();
       // Thread.sleep(5000);
     //   move.checkAngleCC(60,artificialZero);

    }
}
