package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Karine on 10/27/2015.
 */
public class MasterTeleOpRed extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motor3Dwheel;
    DcMotor motorHook;

    //Servo climberBlue;
    Servo redBucket;
    Servo blueBucket;
    Servo climberRed;
    Servo hook;
    Servo blueGrabber;
    Servo redGrabber;
    boolean wasButtonPressed;
    int hookEncoder;
    double armLeftAt;
    double hookPower;
    double encoderZero;
    /**
     * Constructor
     */
    public MasterTeleOpRed() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {


        //this is where we define all of the motors on the robot.
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motor3Dwheel = hardwareMap.dcMotor.get("motor_3");
        motorHook = hardwareMap.dcMotor.get("motor_4");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        //this is where we define the servos
        climberRed = hardwareMap.servo.get("servo_1");
        blueBucket = hardwareMap.servo.get("servo_2");
        redBucket = hardwareMap.servo.get("servo_3");
        blueGrabber = hardwareMap.servo.get("servo_4");
        redGrabber = hardwareMap.servo.get("servo_5");
        //climberBlue= hardwareMap.servo.get("servo_1");
        hook= hardwareMap.servo.get("servo_6");
        //we set this servo into its correct starting position.
        //climberBlue.setPosition(1);
        hookEncoder=motorHook.getCurrentPosition();
        encoderZero = motorHook.getCurrentPosition();
    }


    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {
        //driver#1's controls
        if (gamepad1.y) {                        //this makes it go forward fasr
            motorLeft.setPower(1);
            motorRight.setPower(1);
        } else if (gamepad1.a) {                  //this makes it go backwards fast
            motorLeft.setPower(-1);
            motorRight.setPower(-1);
        } else if (gamepad1.x) {                   //this makes it go forward slow
            motorLeft.setPower(.2);
            motorRight.setPower(.2);
        } else if (gamepad1.b) {                   //this makes it go backwards slow
            motorLeft.setPower(-.2);
            motorRight.setPower(-.2);
        } else if (gamepad1.left_bumper) {         //this makes it turn cc fast
            motorLeft.setPower(-1);
            motorRight.setPower(1);
        } else if (gamepad1.right_bumper) {        //this makes it turn c fast
            motorLeft.setPower(1);
            motorRight.setPower(-1);
        } else if (gamepad1.left_trigger > 0) {      // this makes it turn cc slow
            motorLeft.setPower(-.2);
            motorRight.setPower(.2);
        } else if (gamepad1.right_trigger > 0) {     //this makes it turn c slow
            motorLeft.setPower(.2);
            motorRight.setPower(-.2);
        } else {
            motorRight.setPower(0);
            motorLeft.setPower(0);
        }
        if (gamepad1.dpad_up) {
            motor3Dwheel.setPower(.1);
        } else if (gamepad1.dpad_down) {
            motor3Dwheel.setPower(-.1);
        }else {
            motor3Dwheel.setPower(0);
        }
        //driver2s controls
        if (gamepad2.y) {
            hook.setPosition(0.1);
        } else if (gamepad2.a) {
            hook.setPosition(0.9);
        } else {
            hook.setPosition(.5);
        }

        if(gamepad2.b){
            climberRed.setPosition(0.995);
        }else if(gamepad2.a){
            climberRed.setPosition(0.005);
        }
  /*      double armAngle = ((motorHook.getCurrentPosition()-encoderZero)/3.111)+30;
        if(armAngle>90){
            if(wasButtonPressed=false) {
                if(armLeftAt > armAngle) {
                    hookPower = hookPower+.1;
                }else if(armLeftAt<armAngle){
                    hookPower = hookPower-.1;
                }
            }
        }*/
        if (gamepad2.dpad_up) {
            motorHook.setPower(-.3);
            //  wasButtonPressed = true;
            //  armLeftAt = armAngle;
        } else if (gamepad2.dpad_down) {
            motorHook.setPower(.3);
            //   wasButtonPressed = true;
            //   armLeftAt = armAngle;
        } else {
            motorHook.setPower(0);
        }//else {
        // wasButtonPressed=false;
        // }
        int x = 666;
        if(gamepad1.y) {
            telemetry.addData("jeff", x);
        }
        //  telemetry.addData("armAngle: ", armAngle);
        telemetry.addData("motorHookEncoder: ", motorHook.getCurrentPosition());
        telemetry.addData("encoderZero: ", encoderZero);
        telemetry.addData("hookPower: ", hookPower);
    }

    //this sends information to the driver
        /*telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
*/


    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }



}

