package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

                                                                                                                                                                                       //kaycee is un observant
public class teleOpDrive extends OpMode
{
    //*** Initialize the Motors
    DcMotor leftDrive, rightDrive;
    DcMotor leftArm, rightArm;
    DcMotor slideRail;
    Servo ziplineTrigger;
    //*** initializes the variables for the power of the two drive motors to zero
    private float throttleLeftDrive, throttleRightDrive;
    //*** initializes the variables for the arm power to zero
    private double armValue;
    private boolean rightArmReverser = false;
    private boolean leftArmReverser = false;
    private double railValue;


    public void init()
    {
        //*** Set the motor Objects to their hardware specification within the Robot Controller
        rightDrive = hardwareMap.dcMotor.get("RightDrive");
        leftDrive = hardwareMap.dcMotor.get("LeftDrive");
        leftArm = hardwareMap.dcMotor.get("LeftArm");
        rightArm = hardwareMap.dcMotor.get("RightArm");
        slideRail = hardwareMap.dcMotor.get("SlideRail");
    }


    public void loop()
    {

        if(gamepad1.right_bumper)
        {
            //sets the Arm Reverse for the right arm to true if the right bumper on the first gamepad is pushed
            rightArmReverser = true;
        }

        else
        {
            // set the Arm Reverse to false if nothing is pressed
            rightArmReverser = false;
        }



        if(gamepad1.left_bumper)
        {
            // sets the Left Arm Reverse to true if the left bumper is pressed
            leftArmReverser = true;
        }

        else
        {
            // sets the Left Arm Reverse to false if nothing is pressed
            leftArmReverser = false;
        }


        //*** Set the Drive throttles to the gamepad's left and right stick
        throttleLeftDrive = gamepad1.left_stick_y;
        throttleRightDrive = -gamepad1.right_stick_y;
        //*** Set the power of the motors to the throttle, controlled by the left and right sticks
        rightDrive.setPower(throttleRightDrive);
        leftDrive.setPower(throttleLeftDrive);



       if(gamepad1.right_trigger > 0.0f)
       {
           // converts the float to a boolean by checking if the trigger value is greater than 0, or, in other words, if the trigger is pressed
           if (armVal(gamepad1.right_trigger) <= 1 && !rightArmReverser)
           {
               // enters this block and does NOT reverse the arm value for the right arm if the armValue is less than or equal to 1 AND the arm reverser is false
               rightArm.setPower(-armValue);
           }

           else if(armVal(gamepad1.right_trigger) > 1.0f && !rightArmReverser)
           {
                // if the armValue is greater than 1 AND the arm reverser is false, set the right arm power to max value
               rightArm.setPower(-1);
           }
           else if(armVal(gamepad1.right_trigger) <= 1.0f && rightArmReverser)
           {
               // enters this block and DOES reverse the arm value for the right arm if the armValue is less than or equal to 1 AND the arm reverser is true
               rightArm.setPower(armValue);
           }
           else if(armVal(gamepad1.right_trigger) > 1.0f && rightArmReverser)
           {
               // if the armValue is greater than 1 AND the arm reverser is true, set the arm to the opposite of max power
               rightArm.setPower(1);
           }
       }

       else if(gamepad1.left_trigger > 0.0f)
       {
           // if the left trigger is pressed, enter this block
           if (armVal(gamepad1.left_trigger) <= 1.0f && !leftArmReverser)
           {
               // enters this block and does NOT reverse the arm value for the left arm if the armValue is less than or equal to 1 AND the arm reverser is false
               leftArm.setPower(-armValue);
           }

           else if(armVal(gamepad1.left_trigger) > 1.0f && !leftArmReverser)
           {
               // if the armValue is greater than 1 AND the arm reverser is false, set the arm to the max power
               leftArm.setPower(-1);
           }

           else if(armVal(gamepad1.left_trigger) <= 1.0f && leftArmReverser)
           {
               // enters this block and DOES reverse the arm value for the left arm if the armValue is less than or equal to 1 AND the arm reverser is trie
               leftArm.setPower(armValue);
           }
           else if(armVal(gamepad1.left_trigger) >= 1.0f && leftArmReverser)
           {
               // if the armValue is greater than 1 AND the arm reverser is true, set the arm to the opposite of max power
               leftArm.setPower(1);
           }

       }

        //*** if neither trigger is being pressed, reset the values to zero
       else
       {
           armValue = 0.0f;
           rightArm.setPower(0.0f);
           leftArm.setPower(0.0f);
       }


        if(gamepad2.right_trigger > 0.0f && railValue < 1.0f)
        {
            // if the trigger is pressed AND the max value isn't surpassed, set the value to the railVal method return
            slideRail.setPower(railVal(gamepad2.right_trigger));
        }

        else if(gamepad2.left_trigger > 0.0f && railValue < 1.0f)
        {
            // if the left trigger is pressed AND the max value isn't surpassed set the value to the opposite of the RailVal method return
            slideRail.setPower(-railVal(gamepad2.left_trigger));
        }
        else if (gamepad2.right_trigger > 0.0f && railValue > 1.0f)
        {
            // if the railValue is greater than one AND the right trigger is pressed, the slideRail power is set to max
            slideRail.setPower(1.0f);
        }
        else if(gamepad2.left_trigger > 0.0f && railValue > 1.0f)
        {
            // if the railValue is greater than one AND the left trigger is pressed, the slideRail power is set to the opposite of max
            slideRail.setPower(-1.0f);
        }
        else
        {
            slideRail.setPower(0.0f);
        }


    }

    public void stop()
    {

    }


   public double armVal(double input)
   {
       /* sets the value of the arm motor */
       armValue = Math.pow(input, 2);
       return armValue;
   }

    public double railVal(double input)
    {
        /* sets the value that the sliderail motor is reading */
        railValue = ((1/2) * Math.pow(input, 2));
        return railValue;
    }



}