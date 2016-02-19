 package com.qualcomm.ftcrobotcontroller.util;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Chloe on 2/15/2016.
 */
public class ColorSensorUtil {

    DcMotor motorLeft;
    DcMotor motorRight;
    ColorSensor sensorRed;
    ColorSensor sensorBlue;
    Telemetry telemetry;

    public ColorSensorUtil(DcMotor motorLeft, DcMotor motorRight, ColorSensor sensorRed, ColorSensor sensorBlue, Telemetry telemetry) {
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        this.sensorRed = sensorRed;
        this.sensorBlue = sensorBlue;
        this.telemetry=telemetry;
    }

    public void alignOnRedLine() {

        int flag = 0;
        while (flag == 0) {
            if (sensorBlue.red() > 0 && sensorRed.red() > 0) {
                flag = 1;
                motorLeft.setPower(0);
                motorRight.setPower(0);
            } else {
                if (sensorBlue.red() > 0) {
                    motorLeft.setPower(0);
                    motorRight.setPower(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   // while (sensorRed.red() == 0) {
                        telemetry.addData("case", "blue side sees line");
                        motorRight.setPower(0.3);
                        motorLeft.setPower(-0.3);
                   // }
                } else if (sensorRed.red() > 0) {
                    motorLeft.setPower(0);
                    motorRight.setPower(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //while (sensorBlue.red() == 0) {
                        telemetry.addData("case", "red side sees line");
                        motorLeft.setPower(0.3);
                        motorRight.setPower(-0.3);
                   // }
                } else {
                    //while (sensorBlue.red() == 0 && sensorRed.red() == 0) {
                        telemetry.addData("case", "no line");
                        motorLeft.setPower(-0.2);
                        motorRight.setPower(-0.2);
                   // }
                }
            }
        }

    }
}
