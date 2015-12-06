package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotAuto
//
/**
 * Provide a basic autonomous operational mode that uses the left and right
 * drive motors and associated encoders implemented using a state machine for
 * the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
public class MasterAutonomous extends MasterTelemetry

{
    //--------------------------------------------------------------------------
    //
    // PushBotAuto
    //
    /**
     * Construct the class.
     *
     * The system calls this member when the class is instantiated.
     */
    public MasterAutonomous ()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.



    } // PushBotAuto

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start ()

    {
        //
        // Call the PushBotHardware (super/base class) start method.
        //
        super.start ();

        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_drive_encoders ();

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Implement a state machine that controls the robot during auto-operation.
     * The state machine uses a class member and encoder input to transition
     * between states.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override public void loop ()

    {

        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
            //
            // Synchronize the state machine and hardware.
            //
            case 0:
                //
                // Reset the encoders to ensure they are at a known good value.
                //
                reset_drive_encoders ();

                //
                // Transition to the next state when this method is called again.
                //
                v_state++;

                break;
            //
            // Drive forward until the encoders exceed the specified values.
            //
            case 1:
                forward(1.0f, 20);
                break;
            //
            // Wait...
            //
            case 2:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                break;
            //
            // Turn left until the encoders exceed the specified values.
            //
            case 3:
                forward(1.0f, 20);

                break;
            //
            // Wait...
            //
            case 4:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }else{
                    reset_drive_encoders();
                }
                break;
            //
            // Turn right until the encoders exceed the specified values.
            //
            case 5:
                turn(90, 0.5f);
                break;
            //
            // Wait...
            //
            case 6:
                if (have_drive_encoders_reset ())
                    ;
                //
                //  no action - stay in this case until the OpMode is stopped.
                // ThPerformis method will still be called regardless of the state machine.
                //
            default:
                //
                // The autonomous actions have been accomplished (i.e. the state has
                // transitioned into its final state.
                //
                break;
        }
        // Send telemetry data to the driver station.
        //
       // update_telemetry (); // Update common telemetry
        telemetry.addData("18", "State: " + v_state);

    } // loop

    //--------------------------------------------------------------------------
    //
    // v_state
    //
    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */
    private int v_state = 0;


    void forward(float speed, double disInCm){

        int distanceInEncoderTicks = cmToEncoderTicks(disInCm);
        run_using_encoders();
        set_drive_power(speed, speed);
        if (have_drive_encoders_reached(distanceInEncoderTicks, distanceInEncoderTicks))
        {
            set_drive_power(0,0);
            reset_drive_encoders();
            v_state++;
        }

    }
    void turn(int turnAngle, float speed){

        int leftTicks = angleToEncoderTicks(turnAngle);
        int rightTicks = angleToEncoderTicks(turnAngle);
        run_using_encoders();

        set_drive_power(speed, -speed);
        if (has_left_drive_encoder_reached(leftTicks))
        {
            set_drive_power(0,0);
            reset_drive_encoders();
            v_state++;
        }
       /* try {
            wait(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        set_drive_power(0,0);
        reset_drive_encoders();
        v_state++;*/
    }
} // PushBotAuto
