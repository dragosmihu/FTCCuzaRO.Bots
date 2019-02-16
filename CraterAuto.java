package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import java.util.List;

@Autonomous(name = "Crater", group = "Autonomous")

public class CraterAuto extends LinearOpMode {
    HardwarePushbot logan = new HardwarePushbot();
    AutonomousTools t = new AutonomousTools();
        public ElapsedTime matchTimer;
    int a=0;
    final double MAX_WHEEL_VELOCITY = 0.77203;
    public void runOpMode() throws InterruptedException {
        logan.init(hardwareMap);
        t.initVuforia();
        t.initTfod(hardwareMap);
        String p = "";
        waitForStart();
        t.land(logan);
        sleep(3000);
        t.moveForward(0,200,.7,logan);
        t.turn(60, 'l',logan);
        t.moveForward(0,100, -1, logan);
        t.tfod.activate();

        if (t.tfod != null) {
            boolean foundMinerals = false;
            matchTimer = new ElapsedTime();
            if(matchTimer.equals(10)) t.turn(1,'r',logan);
            while (!foundMinerals) {
                List<Recognition> updatedRecognitions = t.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Objects Detected", updatedRecognitions.size());
                    if (updatedRecognitions.size() == 2) {
                        foundMinerals = true;
                        int goldMineralY = -1;
                        int silverMineral1Y = -1;
                        int silverMineral2Y = -1;
                        
                        // This just records values, and is unchanged
            
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(t.LABEL_GOLD_MINERAL)) {
                                goldMineralY = (int) recognition.getTop();
                            }
                            else if (silverMineral1Y == -1) {
                                silverMineral1Y = (int) recognition.getTop();
                            }
                            else {
                                silverMineral2Y = (int) recognition.getTop();
                            }
                        }

                        // If there is no gold (-1) and there two silvers (not -1) the gold
                        // is not visible, and must be on the right
                        if (goldMineralY == -1 && silverMineral1Y != -1 && silverMineral2Y != -1) {
                            p = "Right"; t.tfod= null;
                            
                        }
                        else if (goldMineralY != -1 && silverMineral1Y != -1) {   // If you can see one gold and one silver ...
                            // ... if the gold is to the right of the silver, the gold is in the center ...
                            if (goldMineralY > silverMineral1Y) {
                                p = "Center"; t.tfod = null;
                            }   // ... otherwise it is on the left
                            else {
                                p = "Left"; t.tfod=null;
                            }
                        }
                    }

                }
            }
        }
        telemetry.addData("Gold Mineral Position: ", p);
        telemetry.update();
        
        if(p.equals("Center"))
        {
            t.turn(3,'l',logan);
            t.moveForward(0,1300,1, logan);
            t.moveForward(0,350,-1, logan);
            t.turn(67,'l',logan);
         //Waits for Depot robot to finish using the depot area
            t.moveForward(0,1500,1,logan);
            t.turn(57,'l',logan);
            t.moveForward(0,2150,1,logan);
            t.turn(180,'l',logan);
            t.lowerMarker(logan);
            t.moveForward(1,3500,1,logan);
             //extra push to get slightly over the crater

        }
        else if(p.equals("Right"))
        {t.turn(23,'l',logan);
             t.moveForward(0,1450,1, logan);
            t.moveForward(0,500,-1, logan);
            t.turn(47,'l',logan);
         //Waits for Depot robot to finish using the depot area
            t.moveForward(0,1250,1,logan);
            t.turn(60,'l',logan);
            t.moveForward(0,2400,1,logan);
            t.turn(180,'l',logan);
            t.lowerMarker(logan);
            t.moveForward(1,3700,1,logan);
            
        }
        else
        { 
            
             //extra push to get slightly over //extra push to get slightly over 
             t.turn(12,'r',logan);
            t.moveForward(0,1300,1, logan);
            t.moveForward(0,500,-1, logan);
            t.turn(90,'l',logan);
         //Waits for Depot robot to finish using the depot area
            t.moveForward(0,1800,1,logan);
            t.turn(63,'l',logan);
            t.moveForward(0,2750,1,logan);
            t.turn(180,'l',logan);
            t.lowerMarker(logan);
            t.moveForward(1,3550,1,logan);
        }
        
    }
}
