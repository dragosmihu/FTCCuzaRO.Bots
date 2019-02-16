package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
//@Disabled
@TeleOp(name = "Tank Drive", group = "TeleOp")

public class TankDrive extends LinearOpMode{
    HardwarePushbot robot = new HardwarePushbot();
    double          clawOffset      = 0;                       // Servo mid position
    final double    CLAW_SPEED      = 0.01 ;                   // sets rate to move servo
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            double left;
            double right;

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;
            //Will be used as motor power, input from gamepad stick

            robot.FL.setPower(left);
            robot.BL.setPower(left);
            robot.FR.setPower(right);
            robot.BR.setPower(right);
            //Positives and negatives may still be messed up
            while(gamepad2.a) robot.hangLift.setPower(.3);
            robot.hangLift.setPower(0);
            while(gamepad2.b) robot.hangLift.setPower(-.3);
            robot.hangLift.setPower(0);
            if(gamepad2.dpad_up) robot.ajutor.setPower(-1);
            if(gamepad2.dpad_left) robot.ajutor.setPower(0);
            if(gamepad2.dpad_down) robot.ajutor.setPower(1);
            if (gamepad2.right_bumper)
                clawOffset += CLAW_SPEED;
            else if (gamepad2.left_bumper)
                clawOffset -= CLAW_SPEED;
            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            robot.markerdrop.setPosition(robot.MID_SERVO + clawOffset);

            
        }
    }
}
