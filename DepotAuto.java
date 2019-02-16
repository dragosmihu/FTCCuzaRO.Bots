package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
import java.util.Objects;
import java.lang.String;

@Autonomous(name = "Depot", group = "Autonomous")
public class DepotAuto extends LinearOpMode {
    HardwarePushbot logan = new HardwarePushbot();
    AutonomousTools t = new AutonomousTools();
    public ElapsedTime matchTimer;
    final double MAX_WHEEL_VELOCITY = 0.77203; // Good to keep track of

    public void runOpMode() throws InterruptedException {
        logan.init(hardwareMap);
        t.initVuforia();
        t.initTfod(hardwareMap);
        String p = "";
        waitForStart();
        t.land(logan);
        sleep(1);
        t.moveForward(0,200,.7,logan);
        t.turn(83, 'l',logan);
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
                            p = "Right"; t.tfod=null;
                        }
                        else if (goldMineralY != -1 && silverMineral1Y != -1) {   // If you can see one gold and one silver ...
                            // ... if the gold is to the right of the silver, the gold is in the center ...
                            if (goldMineralY > silverMineral1Y) {
                                p = "Center"; t.tfod=null;
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
       
        if (p.equals("Center")) {
            t.turn(3,'r',logan);
            t.moveForward(0,2900,1, logan);
            t.turn(120,'l',logan);
            t.lowerMarker(logan);
            t.turn(16,'r',logan);
            t.moveForward(0,4000,1,logan);
        
        }
     else if (p.equals("Right")) {
        
            t.turn(23,'r',logan);
            t.moveForward(0,1300,1, logan);
            t.moveForward(0,500,-1, logan);
            t.turn(73,'l',logan);
            
         //Waits for Depot robot to finish using the depot area
            //t.moveForward(0,100,1,logan);
            t.moveForward(0,2200,1,logan);
            t.turn(50,'l',logan);
            t.moveForward(0,2900,-1,logan);
            t.lowerMarker(logan);
            t.turn(16,'r',logan);
            t.moveForward(0,4300,1,logan);
           }
        else { 
            
            
       t.turn(4,'l',logan);
            t.moveForward(0,2400,1, logan);
            t.turn(105,'l',logan);
         //Waits for Depot robot to finish using the depot area
            t.moveForward(0,1800,-1,logan);
            t.lowerMarker(logan);
            t.turn(20,'r',logan);
            t.moveForward(1,4000,1,logan);
        
        } 
            
    }
}












