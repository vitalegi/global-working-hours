package it.vitalegi.globalworkinghours;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.vitalegi.globalworkinghours.bean.WorkingHours;

@Service
public class GetWorkingHoursService {

	private final Logger LOG = LoggerFactory.getLogger(GetWorkingHoursService.class);

	public boolean isWorkingTimes(ZonedDateTime referenceDateTime, List<WorkingHours> workingHours) {

		for (WorkingHours instance : workingHours) {
			boolean workingTime = isWorkingTimes(referenceDateTime, instance);

			if (workingTime) {
				return true;
			}
		}
		return false;
	}

	public boolean isWorkingTimes(ZonedDateTime referenceDateTime, WorkingHours workingHours) {

		ZonedDateTime zonedDateTime = referenceDateTime.withZoneSameInstant(workingHours.getTimezone().toZoneId());

		return isWorkingDay(zonedDateTime, workingHours.getWorkingDays())
				&& isAfterWorkStart(zonedDateTime, workingHours.getStartHour())
				&& isBeforeWorkEnd(zonedDateTime, workingHours.getEndHour());
	}

	protected boolean isWorkingDay(ZonedDateTime zonedDateTime, List<DayOfWeek> workingDays) {
		DayOfWeek day = zonedDateTime.getDayOfWeek();

		if (workingDays == null) {
			return true;
		}
		return workingDays.stream().anyMatch(w -> w.equals(day));
	}

	protected boolean isAfterWorkStart(ZonedDateTime zonedDateTime, int startHour) {

		int hour = zonedDateTime.getHour();
		int minute = zonedDateTime.getMinute();
		if (hour * 60 + minute >= startHour * 60) {
			return true;
		}
		return false;
	}

	protected boolean isBeforeWorkEnd(ZonedDateTime zonedDateTime, int endHour) {

		int hour = zonedDateTime.getHour();
		int minute = zonedDateTime.getMinute();
		if (hour * 60 + minute <= endHour * 60) {
			return true;
		}
		return false;
	}
}
