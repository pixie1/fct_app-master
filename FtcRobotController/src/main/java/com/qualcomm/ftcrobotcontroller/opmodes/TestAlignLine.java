package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.util.ColorSensorUtil;
import com.qualcomm.ftcrobotcontroller.util.EncoderMoveUtil;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Karine on 2/16/2016.
 */
public class TestAlignLine extends LinearOpMode{

    DcMotor motorLeft;
    DcMotor motorRight;
    ColorSensor sensorBlue;
    ColorSensor sensorRed;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        sensorBlue = hardwareMap.colorSensor.get("color_blue");
        sensorBlue.setI2cAddress(0x42);
        sensorBlue.enableLed(true);

        sensorRed= hardwareMap.colorSensor.get("color_red");
        sensorRed.enableLed(true);
        ColorSensorUtil colorSensorUtil= new ColorSensorUtil(motorLeft, motorRight,sensorRed, sensorBlue, telemetry);
        waitForStart();
          colorSensorUtil.alignOnRedLine();

    }
}
