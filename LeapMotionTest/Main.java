/******************************************************************************\
 * Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
 * Leap Motion proprietary and confidential. Not for distribution.              *
 * Use subject to the terms of the Leap Motion SDK Agreement available at       *
 * https://developer.leapmotion.com/sdk_agreement, or another agreement         *
 * between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
class Main {
    final static int THRESHOLD_OF_FRAMES = 150;
    final static int LENGTH_OF_PASSWORD = 6;
    public static Frame latestFrame;
    public static int currentFrameId = -1;
    public static Scanner sc = new Scanner (System.in);
    public static ArrayList <User> users = new ArrayList <>();
    public static void main(String[] args) {
        while (true){
            System.out.print("Enter y for new user or l to login: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")){
                ArrayList <Frame> frameList = new ArrayList<>();
                try {
                    System.out.println("System will get your motion input upon countdown");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("3");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("2");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("1 Start Posing!");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("GO");
                    boolean allValid = true;
                    for (int i = 0; i<=LENGTH_OF_PASSWORD * 5;i++){
                        Frame frameToAdd = getFrameInput ();
                        if (frameToAdd == null){
                            allValid = false;
                            i = LENGTH_OF_PASSWORD * 7; //Break
                        }else{    
                            frameList.add(frameToAdd);
                            System.out.println("Pose!");
                        }
                    }if (allValid){
                        ArrayList <ArrayList<Finger>> fingerListList = new ArrayList <>();
                        for (int i = 0; i<LENGTH_OF_PASSWORD * 5;i++){
                            Frame frame1 = frameList.get(i);
                            Frame frame2= frameList.get(i+1);
                            fingerListList.add(extendedFingers (frame1,frame2));
                        }
                        
                        System.out.println("Thank You! Please enter your name.");
                        String name = sc.nextLine();
                        
                    }else {
                    System.out.println("Please stay in the input at all times! Try Again!");
                    }
                    
                    } catch (InterruptedException e) {
                            //Handle exception
                    }
                
            }else if (choice.equalsIgnoreCase("l")){
                
            }else {
                System.out.println("Please Try Again with a valid input: ");
            }
    

    }

}


/**
 * Method: fingersEquals
 * 
 * description
 * 
 * Returns:
 * 
 *   Bool - Whether or not the two 'hand symbols' are 
 */
public static boolean handsymbol_Equals (){

    return false;
}

/**
 * Method: find_rep
 * 
 * Takes an Arraylist o
 * 
 * Parameters:
 * 
 *   ArrayList<Finger> list_of_fingerlists - [type/description]
 * 
 * Returns:
 * 
 *   FingerList - return description
 */
 //public static ArrayList <ArrayList<Finger>> find_rep(ArrayList <ArrayList<Finger>> fingerListList){
     //Ha
 //}

    public static Frame getFrameInput (){
        long millis = System.currentTimeMillis();
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();
        Frame toReturn = null;
        // Have the sample listener receive events from the controller
        controller.addListener(listener);
        
        while(System.currentTimeMillis() <(millis + THRESHOLD_OF_FRAMES )){
            if (latestFrame != null){
            if (!latestFrame.hands().isEmpty()){
                 System.out.println("Frame id: " + latestFrame.id()
+ ", timestamp: " + latestFrame.timestamp()
+ ", hands: " + latestFrame.hands().count()
+ ", fingers: " + latestFrame.fingers().count()
+ ", tools: " + latestFrame.tools().count()
+ ", gestures " + latestFrame.gestures().count());
                controller.removeListener(listener);
                toReturn = latestFrame;
                break;
            }
        }
        }
        controller.removeListener(listener);
                        try {
        TimeUnit.MILLISECONDS.sleep((millis + 200) - System.currentTimeMillis());
                            
                    } catch (InterruptedException e) {
                            //Handle exception
                    }
        return toReturn;
    }
            /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public static ArrayList<Finger> extendedFingers(Frame frame1, Frame frame2){
        ArrayList<Finger> extendedFingerList = new ArrayList <>();
        Hand hand1 = frame1.hands().frontmost();
        Hand hand2 = frame2.hands().frontmost();
        FingerList extended1 = hand1.fingers().extended();
        FingerList extended2 = hand2.fingers().extended();
        
        for (Finger finger1 : extended1){
            for (Finger finger2 : extended2){
                if (finger1.type() == finger2.type())
                extendedFingerList.add(finger1);
            }
        }
        
        return extendedFingerList;
    
    }
    }


class SampleListener extends Listener {
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        Main.latestFrame= controller.frame();
    }
}

