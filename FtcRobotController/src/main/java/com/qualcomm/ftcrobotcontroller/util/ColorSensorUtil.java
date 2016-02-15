package com.qualcomm.ftcrobotcontroller.util;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Chloe on 2/15/2016.
 */
public class ColorSensorUtil {

    DcMotor motorLeft;
    DcMotor motorRight;
    ColorSensor sensorRed;
    ColorSensor sensorBlue;

    public ColorSensorUtil(DcMotor motorLeft, DcMotor motorRight, ColorSensor sensorRed, ColorSensor sensorBlue) {
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        this.sensorRed = sensorRed;
        this.sensorBlue = sensorBlue;
    }

    public void alignOnRedLine() {

        int flag = 0;
        while (flag == 0) {
            if (sensorBlue.red() > 0 && sensorRed.red() > 0) {
                flag = 1;
            } else {
                if (sensorBlue.red() > 0) {
                    while (sensorRed.red() == 0) {
                        motorLeft.setPower(-0.5);
                    }
                } else if (sensorRed.red() > 0) {
                    while (sensorBlue.red() == 0) {
                        motorRight.setPower(-0.5);
                    }
                } else {
                    while (sensorBlue.red() == 0 && sensorRed.red() == 0) {
                        motorLeft.setPower(0.5);
                        motorRight.setPower(0.5);
                    }
                }
            }
        }
    }
}
