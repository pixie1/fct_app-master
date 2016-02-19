package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Karine on 2/14/2016.
 */
public class TryColorSensor extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {



            // write some device information (connection info, name and type)
            // to the log file.
            hardwareMap.logDevices();

            // get a reference to our ColorSensor object.
            ColorSensor sensorRGB = hardwareMap.colorSensor.get("color_red");
           sensorRGB.setI2cAddress(0x42);
            // bEnabled represents the state of the LED.
            boolean bEnabled = true;


            // turn the LED on in the beginning, just so user will know that the sensor is active.
            sensorRGB.enableLed(false);

            // wait one cycle.
            waitOneFullHardwareCycle();

            // wait for the start button to be pressed.
            waitForStart();

            // hsvValues is an array that will hold the hue, saturation, and value information.
            float hsvValues[] = {0F,0F,0F};

            // values is a reference to the hsvValues array.
            final float values[] = hsvValues;

            // get a reference to the RelativeLayout so we can change the background
            // color of the Robot Controller app to match the hue detected by the RGB sensor.


            // while the op mode is active, loop and read the RGB data.
            // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
            while (opModeIsActive()) {
                // check the status of the x button on either gamepad.
              //  bCurrState = gamepad1.x || gamepad2.x;

                  // turn on the LED.
                    sensorRGB.enableLed(true);

                 //   bEnabled=!bEnabled;
                // convert the RGB values to HSV values.
                Color.RGBToHSV(sensorRGB.red(), sensorRGB.green(), sensorRGB.blue(), hsvValues);

                // send the info back to driver station using telemetry function.
                telemetry.addData("ok Clear", sensorRGB.alpha());
                telemetry.addData("Red  ", sensorRGB.red());
                telemetry.addData("Green", sensorRGB.green());
                telemetry.addData("Blue ", sensorRGB.blue());
                telemetry.addData("Hue", hsvValues[0]);

                // change the background color to match the color detected by the RGB sensor.
                // pass a reference to the hue, saturation, and value array as an argument
                // to the HSVToColor method.


                // wait a hardware cycle before iterating.
                waitOneFullHardwareCycle();
            }
        }
    }

