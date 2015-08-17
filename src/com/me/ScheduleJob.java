package com.me;

import java.util.Timer;

public class ScheduleJob {
	private static Timer timer = new Timer();

	public static Timer getTimer() {
		return timer;
	}

	public static void setTimer(Timer timer) {
		ScheduleJob.timer = timer;
	}
	
	public static void cancel(){
		timer.cancel();
	}
}
