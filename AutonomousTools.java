package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.vuforia.Vuforia;
import com.vuforia.CameraDevice;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class AutonomousTools {
    final double MAX_WHEEL_VELOCITY = 0.32634;
    static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    public VuforiaLocalizer vuforia;

    public TFObjectDetector tfod;

    public AutonomousTools() {

    }

    private void setMotorPower(double speed, int fl, int fr, int bl, int br, HardwarePushbot logan) {
        logan.FL.setPower(speed * fl);
        logan.FR.setPower(speed * fr);
        logan.BL.setPower(speed * bl);
        logan.BR.setPower(speed * br);
    }

    private void setMotorPower(HardwarePushbot logan) {
        logan.FL.setPower(0);
        logan.FR.setPower(0);
        logan.BL.setPower(0);
        logan.BR.setPower(0);
        logan.ajutor.setPower(0);
    }

    public void moveForward(int a, int moveTime, double speed, HardwarePushbot logan) throws InterruptedException {
       
        if(a==1) logan.ajutor.setPower(1);
        setMotorPower(speed, 1, 1, 1, 1, logan);
        Thread.sleep(moveTime);
        setMotorPower(logan);
        
    }

    public void turn(double degree, char dir, HardwarePushbot logan) throws InterruptedException {
      
        if (dir == 'r') {
            setMotorPower(1, 1, -1, 1, -1, logan);
        }
        else if (dir == 'l') {
            setMotorPower(1, -1, 1, -1, 1, logan);
        }
        Thread.sleep((int)(550 * degree / 90));
        setMotorPower(logan);
    }

    public void strafe(int time, char dir, HardwarePushbot logan) throws InterruptedException {
        if (dir == 'r') {
            setMotorPower(0.7, 1, -1, -1, 1, logan);
        }
        else if (dir == 'l'){
            setMotorPower(0.7, -1, 1, 1, -1, logan);
        }
        Thread.sleep(time);
        setMotorPower(logan);
    }

    public void land(HardwarePushbot logan) throws InterruptedException {

        logan.hangLift.setPower(-1);
        Thread.sleep(3900);
        logan.hangLift.setPower(0);
    }

    public void lowerMarker(HardwarePushbot logan) throws InterruptedException {
        logan.markerdrop.setPosition(1);
        Thread.sleep(1000);
        
    }

    public void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AcI20fb/////AAABmV5NqIX4JkMWh08S68hUAa4lABqi43ZJWgk2BBpgcnlTDq0ZLILm0ag9wiiHoh4qy3KoCs6QcR5KfR4+cSdBU9j5rIHC2VEyVSgQwaDZUNGbOUm+wnFpd+s3x5hBxKL2KnX1OawF+rI6jJAl0WVWh3L/zagHgiKDYWomW33PuaigmxMq0H2t/JjBi1cQBG5cFSK/XDlLJNfKAFez1ov8De9GJqQDIET2Q+D8hTt4U5trACoOKYrzEOWvuIY/9y1GQgIUzwXtOzX+NvbMc2+PcS9pl9MjeDAKy/dga0v3w5P6hFSp3GG/p+HrBqiMI6h3rsgy4sWtikHghVV0KdsOwEf3WK3BJ/+WyETboBIK7lsR";
        parameters.cameraDirection = CameraDirection.BACK;
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);
        CameraDevice.getInstance().setFlashTorchMode(true);
    }

    public void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        this.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, this.vuforia);
        this.tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        tfodParameters.minimumConfidence = 0.6;
    }

 // Commented out due to not using foam roller idea for qualifier
   /* public void changeRollerLift(boolean up) throws InterruptedException { //if up is true, it is up, so the lift needs to go down, else, it goes up
        if (!up) {
            hulk.rollerLift.setPower(-0.25); //not too fast
            Thread.sleep(2000);
        }
        else {
            hulk.rollerLift.setPower(0.0625); //gravity pulls the lift down
            Thread.sleep(250);
        }
        //EXPERIMENTAL time = (2 PI rad) / (angular velocity (6.7544 rad/s)) * 1000         (UNUSED)
    }
    */

}
