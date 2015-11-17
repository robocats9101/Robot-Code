package com.qualcomm.ftcrobotcontroller.opmodes;

import android.text.format.Time;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;



public class autonomousMode extends LinearOpMode
{
    DcMotor leftDrive, rightDrive;
    DcMotor leftArm, rightArm;
    ColorSensor colorSensor;
    OpticalDistanceSensor distanceSensor;
    botState _botState;
    position _position;
    int checkTimes = 0;

    public void initialize()
    {
        leftDrive = hardwareMap.dcMotor.get("LeftDrive");
        rightDrive = hardwareMap.dcMotor.get("RightDrive");
        leftArm = hardwareMap.dcMotor.get("LeftArm");
        rightArm = hardwareMap.dcMotor.get("RightArm");
        colorSensor = hardwareMap.colorSensor.get("ColorSensor");
        distanceSensor = hardwareMap.opticalDistanceSensor.get("DistanceSensor");
        /*
        leftDrive.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightDrive.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftArm.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightArm.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        _botState = botState.IDLE;
        */
    }


    public void runOpMode() throws InterruptedException
    {
        initialize();
        waitForStart();
        while(opModeIsActive())
        {
            positionStateManager();
            botStateManager();
        }
    }


    public enum botState
    {
        IDLE, DRIVING, BUCKET_DROP, TURNINGLEFT, TURNINGRIGHT, CLIMBING, SUCCESSFUL
    }

    public enum position
    {
        RED_ONE, RED_TWO, BLUE_ONE, BLUE_TWO
    }




    public botState botStateManager() throws InterruptedException {
        switch (_botState)
        {
            case IDLE:
                _botState = botState.DRIVING;
                break;

            case DRIVING:
                if(checkTimes < 8)
                {
                    leftDrive.setPower(1.0);
                    rightDrive.setPower(1.0);
                    sleep(2000);
                    _botState = botState.TURNINGRIGHT;
                    checkTimes++;
                }

                break;

            case TURNINGRIGHT:
                if(checkTimes < 8)
                {
                    leftDrive.setPower(1.0);
                    rightDrive.setPower(-1.0);
                    sleep(1000);
                    _botState = botState.DRIVING;
                    checkTimes++;
                }
                break;

            case TURNINGLEFT:

                break;

            case BUCKET_DROP:
                // do stuff to drop the people in the bucket
                break;

            case SUCCESSFUL:

                break;
        }

        return botState.BUCKET_DROP;
    }


    public position positionStateManager()
    {
        switch (_position)
        {
            case RED_ONE:
                //do stuff if in red one position
                break;
            case RED_TWO:
                //do stuff for red two position
                break;
            case BLUE_ONE:
                // do stuff for blue one position
                break;
            case BLUE_TWO:
                //do stuff for blue two position
                break;
        }

        return position.BLUE_ONE;
    }

}

