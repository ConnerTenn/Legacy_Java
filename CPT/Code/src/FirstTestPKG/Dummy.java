package FirstTestPKG;
import robocode.*;

public class Dummy extends AdvancedRobot
{
	final double TAU = 6.28318530717958647692;


	@Override
	public void run()
	{

		// Get the radar spinning so we can get ScannedRobotEvents
		setTurnRadarLeftRadians(Double.POSITIVE_INFINITY);
		int i = 0;
		while (true)
		{
			System.out.println("Update" + i + "\n\n");

			//setTurnRightRadians(0.2*TAU);
			setAhead(1000.0);
			setAhead(50.0);execute(); for (int j = 0; j < 100000; j++) {}
			setTurnRightRadians(0.01 * TAU);
			setBack(50.0);execute(); for (int j = 0; j < 100000; j++) {}
			setTurnLeftRadians(0.01 * TAU);
			System.out.println("Done:" + i + "\n\n");

			i++;
			
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event)
	{
		//System.out.println("Opponent aim:" + event.getGunHeadingRadians());
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