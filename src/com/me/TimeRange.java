package com.me;

import java.util.Calendar;
import java.util.Date;

public class TimeRange implements Comparable<TimeRange>{

	private MyTime begin;
	public MyTime getBegin() {
		return begin;
	}

	public void setBegin(MyTime begin) {
		this.begin = begin;
	}

	public MyTime getEnd() {
		return end;
	}

	public void setEnd(MyTime end) {
		this.end = end;
	}

	private MyTime end;
	
	public TimeRange(String timerange) {
		if (timerange == null)
			return;
		String s[] = timerange.split("->");
		String tmp[] = s[0].split(":");
		begin = new MyTime(Integer.valueOf(tmp[0]), Integer.valueOf(tmp[1]));
		tmp = s[1].split(":");
		end = new MyTime(Integer.valueOf(tmp[0]), Integer.valueOf(tmp[1]));
	}

	@Override
	public int compareTo(TimeRange o) {
		return begin.compareTo(o.getBegin());
	}
	
	
	public class MyTime implements Comparable<MyTime>{
		private int hour;
		public int getHour() {
			return hour;
		}
		public void setHour(int hour) {
			this.hour = hour;
		}
		public int getMinute() {
			return minute;
		}
		public void setMinute(int second) {
			this.minute = second;
		}
		private int minute;
		
		public MyTime() {		}
		
		MyTime(int hour, int second){
			setHour(hour);
			setMinute(second);
		}
		public MyTime getNowTime(){
			MyTime m = new MyTime();
			m.setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
			m.setMinute(Calendar.getInstance().get(Calendar.SECOND));
			return m;
		}
		private int getSecond(){
			return hour*60*60 + minute*60;
		}
		@Override
		public int compareTo(MyTime o) {
			return getSecond() - o.getSecond();
		}
		public Date getDate(){
			Calendar c = Calendar.getInstance();
			c.clear(Calendar.HOUR_OF_DAY);
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.clear(Calendar.MINUTE);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, 0);
			Date d = c.getTime();
			return d;
		}
	}
	
}
