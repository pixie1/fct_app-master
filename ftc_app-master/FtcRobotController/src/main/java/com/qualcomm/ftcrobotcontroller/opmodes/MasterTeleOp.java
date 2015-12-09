package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Karine on 10/27/2015.
 */
public class MasterTeleOp extends OpMode  {
    /*
	 * Note: the configuration of the servos is such that
	 * as the arm servo approache0,	 t * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
    the arm position moves up (ahewfromay  fs loor).
            */
    // TETRIX VALUES.
    //final static double ARM_MIN_RANGE  = 0.20;
    //final static double ARM_MAX_RANGE  = 0.90;
    // final static double CLAW_MIN_RANGE  = 0.20;
    // final static double CLAW_MAX_RANGE  = 0.7;

    // position of the arm servo.
    //   double armPosition;

    // amount to change the arm servo position.
    // double armDelta = 0.1;

    // position of the claw servo
    // double clawPosition;

    // amount to change the claw servo position by
    //  double clawDelta = 0.1;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motor3Dwheel;
    DcMotor motorHook;

    Servo climberBlue;
    Servo climberRed;
    Servo hook;

    /**
     * Constructor
     */
    public MasterTeleOp() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {


		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot and reversed.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motor3Dwheel = hardwareMap.dcMotor.get("motor_3");
        motorHook = hardwareMap.dcMotor.get("motor_4");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

          climberRed = hardwareMap.servo.get("servo_6");
          climberBlue= hardwareMap.servo.get("servo_1");
        hook= hardwareMap.servo.get("servo_5");

        // assign the starting position of the wrist and claw
        //   armPosition = 0.2;
        //   clawPosition = 0.2;
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

        if(gamepad1.y) {                        //this makes it go forward fasr
            motorLeft.setPower(1);
            motorRight.setPower(1);
        }else if(gamepad1.a) {                  //this makes it go backwards fast
            motorLeft.setPower(-1);
            motorRight.setPower(-1);
        }else if(gamepad1.x){                   //this makes it go forward slow
            motorLeft.setPower(.2);
            motorRight.setPower(.2);
        }else if(gamepad1.b){                   //this makes it go backwards slow
            motorLeft.setPower(-.2);
            motorRight.setPower(-.2);
        }else if(gamepad1.left_bumper){         //this makes it turn cc fast
            motorLeft.setPower(-1);
            motorRight.setPower(1);
        }else if(gamepad1.right_bumper){        //this makes it turn c fast
            motorLeft.setPower(1);
            motorRight.setPower(-1);
        }else if(gamepad1.left_trigger>0){      // this makes it turn cc slow
            motorLeft.setPower(-.2);
            motorRight.setPower(.2);
        }else if(gamepad1.right_trigger>0){     //this makes it turn c slow
            motorLeft.setPower(.2);
            motorRight.setPower(-.2);
        }else {
            motorRight.setPower(0);
            motorLeft.setPower(0);
        }
        if(gamepad1.dpad_up){
            motor3Dwheel.setPower(.1);
        }else if(gamepad1.dpad_down){
            motor3Dwheel.setPower(-.1);
        }

        

        //this sends information to the driver
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}

