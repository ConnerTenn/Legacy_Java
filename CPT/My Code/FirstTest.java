package FirstTestPKG;
import robocode.*;
import java.awt.event.KeyEvent;

public class FirstTest extends AdvancedRobot
{
	final double TAU = 6.28318530717958647692;
	
	boolean[] keys = new boolean[255];
	
	@Override
	public void run()
	{

		// Get the radar spinning so we can get ScannedRobotEvents
		setTurnRadarLeftRadians(Double.POSITIVE_INFINITY);

		while (true)
		{
			//System.out.println("Update");

			//setTurnLeftRadians(0.9*TAU);
			//setAhead(5.0);
			if (keys['w'])
			{
				setAhead(5.0);
			}
			if (keys['s'])
			{
				setBack(5.0);
			}
			if (keys['a'])
			{
				setTurnLeftRadians(0.01*TAU);
			}
			if (keys['d'])
			{
				setTurnRightRadians(0.01*TAU);
			}
			if (keys['f'])
			{
				fire(getEnergy() - 0.1);
			}

			execute();
		}
	}
	
	@Override
	public void onKeyPressed(KeyEvent e)
	{
		//System.out.println("Key:" +e.getKeyCode());
		
		keys[e.getKeyChar()] = true;
	}
	
	@Override
	public void onKeyReleased(KeyEvent e)
	{
		//System.out.println("Key:" +e.getKeyCode());
		
		keys[e.getKeyChar()] = false;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event)
	{
		System.out.println("See Robot");
	}

	// The following events MUST be overriden if you plan to multithread your robot.
	// Failure to do so can cause exceptions and general annoyance.

	@Override
	public void onWin(WinEvent e)
	{
	}

	@Override
	public void onDeath(DeathEvent e)
	{
	}


	@Override
	public void onBattleEnded(BattleEndedEvent e)
	{
	}
}