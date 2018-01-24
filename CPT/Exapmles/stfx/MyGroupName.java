package stfx;
import robocode.*;
import java.awt.Color;

/**
 * MyGroupName - a robot by (your name here)
 * Basic template for your robot.
 */
public class MyGroupName extends Robot
{
   /** 
    * run: MyGroupName's default behavior
    */ 
   public void run() 
   {
      // Decorate your Robot
      this.setColors(Color.green,Color.green,Color.green);
      
	  // Main loop (infinite - game controls when it's over)
      while(true) 
      {
         // Add code here for how you want the robot to move

	  }

      // Code will never reach here -- and that okay for a change ;)
   }

   /**
    * onScannedRobot: What to do when you see another robot
    */
   public void onScannedRobot(ScannedRobotEvent e) 
   {
	  // Add code here
   }

   /**
    * onHitByBullet: What to do when you're hit by a bullet
    */
   public void onHitByBullet(HitByBulletEvent e) 
   {
	   // Add code here

   }
   
}
