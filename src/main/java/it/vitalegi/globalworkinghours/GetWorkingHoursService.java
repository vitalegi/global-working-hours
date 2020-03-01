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
				&& isWorkingTime(zonedDateTime, workingHours.getStartHour(), workingHours.getEndHour());
	}

	protected boolean isWorkingDay(ZonedDateTime zonedDateTime, List<DayOfWeek> workingDays) {
		DayOfWeek day = zonedDateTime.getDayOfWeek();

		if (workingDays == null) {
			return true;
		}
		return workingDays.stream().anyMatch(w -> w.equals(day));
	}

	protected boolean isWorkingTime(ZonedDateTime zonedDateTime, int startHour, int endHour) {

		int hour = zonedDateTime.getHour();
		if (startHour <= hour && hour <= endHour) {
			return true;
		}
		return false;
	}
}
