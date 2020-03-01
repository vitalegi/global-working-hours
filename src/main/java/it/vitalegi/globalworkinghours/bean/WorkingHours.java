package it.vitalegi.globalworkinghours.bean;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class WorkingHours {

	protected String name;
	protected int startHour;
	protected int endHour;
	protected List<DayOfWeek> workingDays;
	protected TimeZone timezone;

	public static WorkingHoursBuilder newInstance() {
		return new WorkingHoursBuilder();
	}

	@Override
	public String toString() {
		return "WorkingHours [name=" + name + ", startHour=" + startHour + ", endHour=" + endHour + ", workingDays="
				+ workingDays + ", timezone=" + timezone + "]";
	}

	public static class WorkingHoursBuilder {
		WorkingHours instance;

		public WorkingHoursBuilder() {
			instance = new WorkingHours();
		}

		public WorkingHoursBuilder name(String name) {
			instance.setName(name);
			return this;
		}

		public WorkingHoursBuilder startHour(int startHour) {
			instance.setStartHour(startHour);
			return this;
		}

		public WorkingHoursBuilder endHour(int endHour) {
			instance.setEndHour(endHour);
			return this;
		}

		public WorkingHoursBuilder timezone(TimeZone timezone) {
			instance.setTimezone(timezone);
			return this;
		}

		public WorkingHoursBuilder workingDays(List<DayOfWeek> workingDays) {
			instance.setWorkingDays(workingDays);
			return this;
		}

		public WorkingHoursBuilder workingDays(DayOfWeek... workingDays) {
			instance.setWorkingDays(Arrays.asList(workingDays));
			return this;
		}

		public WorkingHours build() {
			return instance;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}

	public List<DayOfWeek> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<DayOfWeek> workingDays) {
		this.workingDays = workingDays;
	}

}
