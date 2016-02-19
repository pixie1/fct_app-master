package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.util.EncoderMoveUtil;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Date;

/**
 * Created by Karine on 2/18/2016.
 */
public class AutonomousRED_mountain extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorHook;
    Servo hook;
    Servo blueDebrisPickup;
    Servo redDebrisPickup;
    ModernRoboticsI2cGyro sensorGyro;
    EncoderMoveUtil encoderMoveUtil;
    ColorSensor sensorRed;
    ColorSensor sensorBlue;
    ModernRoboticsAnalogOpticalDistanceSensor ods;

    @Override
    public void runOpMode() throws InterruptedException {

        //drop climber
        //park on mountain

        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        hook = hardwareMap.servo.get("servo_6");
        blueDebrisPickup = hardwareMap.servo.get("servo_4");
        redDebrisPickup = hardwareMap.servo.get("servo_5");
        motorHook = hardwareMap.dcMotor.get("motor_3");
        sensorBlue = hardwareMap.colorSensor.get("color_blue");
        sensorBlue.setI2cAddress(0x42);
        sensorBlue.enableLed(true);
        sensorRed= hardwareMap.colorSensor.get("color_red");
        sensorRed.enableLed(true);

        sensorGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        sensorGyro.calibrate();

        telemetry.addData("calibrating", 0);
        Thread.sleep(5000);
        telemetry.addData("calibrated", 1);
        encoderMoveUtil= new EncoderMoveUtil(motorLeft, motorRight, sensorGyro,telemetry);
        // wait for fcs to start the match
        waitForStart();

        //go straight
        encoderMoveUtil.forward(83, 0.5);
        encoderMoveUtil.stopMotors();

        //turn 45 parallel to red line
        encoderMoveUtil.turnC(45, 0.5);
        encoderMoveUtil.stopMotors();
        encoderMoveUtil.checkAngleC(45, 0, 5);
        encoderMoveUtil.stopMotors();
        telemetry.addData("FIRST TURN DONE", 0000000);
        //go straight
//
        encoderMoveUtil.forward(160, 0.4);

        encoderMoveUtil.stopMotors();
        Thread.sleep(500);
//            //turn to face beacon
        int artificialZero = sensorGyro.getIntegratedZValue();
        Thread.sleep(100);
        encoderMoveUtil.turnC(90 - Math.abs(artificialZero), 0.5);
        encoderMoveUtil.stopMotors();
        encoderMoveUtil.checkAngleC(90 - Math.abs(artificialZero), artificialZero, 3);
        telemetry.addData("SECOND TURN DONE", 00000000);
        Thread.sleep(1000);

        motorLeft.setPower(-.2);
        motorRight.setPower(-.2);
        Thread.sleep(2500);
        encoderMoveUtil.stopMotors();
        Thread.sleep(200);
        encoderMoveUtil.stopMotors();
        Thread.sleep(200);
        encoderMoveUtil.stopMotors();
        encoderMoveUtil.backward(37,.2); //tweak at tournament
        encoderMoveUtil.stopMotors();
        Thread.sleep(100);
        encoderMoveUtil.stopMotors();
        Thread.sleep(100);

        Long start= new Date().getTime();
        Long current= new Date().getTime();
        while(current-start<3500){
            hook.setPosition(0.1);
            blueDebrisPickup.setPosition(0.5);
            redDebrisPickup.setPosition(0.5);
            current=new Date().getTime();
            telemetry.addData("done", "no");
        }
        hook.setPosition(0.5);
        telemetry.addData("done", "yes");

        while(Math.abs(motorHook.getCurrentPosition())<200) {
            motorHook.setPower(.25);
            hook.setPosition(0.5);
            telemetry.addData("pos", motorHook.getCurrentPosition());
        }
        while(Math.abs(motorHook.getCurrentPosition())<300) {
            motorHook.setPower(.15);
            hook.setPosition(0.5);
            telemetry.addData("pos1.5", motorHook.getCurrentPosition());
        }
        motorHook.setPower(0);
        Thread.sleep(100);
        while(Math.abs(motorHook.getCurrentPosition())>50){
            motorHook.setPower(-0.25);
            hook.setPosition(0.5);
            telemetry.addData("pos2", motorHook.getCurrentPosition());
        }
        motorHook.setPower(0);

        encoderMoveUtil.backward(70, 0.3);
        encoderMoveUtil.stopMotors();
        Thread.sleep(100);
        //turn to mountain
        int artificalZero=sensorGyro.getIntegratedZValue();
        encoderMoveUtil.turnC(145-Math.abs(artificalZero), 0.5);
        encoderMoveUtil.stopMotors();
        encoderMoveUtil.checkAngleC(145 - Math.abs(artificalZero), artificalZero, 5);
        encoderMoveUtil.stopMotors();

        encoderMoveUtil.forward(30, 0.5);
        encoderMoveUtil.stopMotors();
        Thread.sleep(100);
        encoderMoveUtil.turnC(160 - Math.abs(artificalZero), 0.5);
        encoderMoveUtil.stopMotors();
        encoderMoveUtil.checkAngleC(160 - Math.abs(artificalZero), artificalZero, 5);
        encoderMoveUtil.stopMotors();
        artificalZero=sensorGyro.getIntegratedZValue();


        motorLeft.setPower(-0.5);
        motorRight.setPower(-0.5);
        Thread.sleep(400);
        encoderMoveUtil.stopMotors();


    }
}
