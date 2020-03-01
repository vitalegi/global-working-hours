package it.vitalegi.globalworkinghours;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.util.Pair;

@Service
public class GetTimeComparationService {

	@Autowired
	GetWorkingHoursService getWorkingHoursService;
	@Autowired
	GetReferenceTimeService getReferenceTimeService;

	public boolean equals(LocalDateTime time1, LocalDateTime time2, List<WorkingHours> workingHours) {
		ZonedDateTime ref1 = getReferenceTimeService.getReferenceTime(time1);
		ZonedDateTime ref2 = getReferenceTimeService.getReferenceTime(time2);

		boolean w1 = getWorkingHoursService.isWorkingTimes(ref1, workingHours);
		boolean w2 = getWorkingHoursService.isWorkingTimes(ref2, workingHours);
		return w1 == w2;
	}

	public List<Pair<LocalDateTime, LocalDateTime>> getIntervals(List<LocalDateTime> times,
			List<WorkingHours> workingHours) {

		List<Pair<LocalDateTime, LocalDateTime>> intervals = new ArrayList<>();

		for (int i = 0; i < times.size(); i++) {
			LocalDateTime intervalStart = times.get(i);
			LocalDateTime intervalEnd = getIntervalEnd(times, workingHours, intervalStart);
			intervals.add(new Pair<>(intervalStart, intervalEnd));

			while (times.get(i).isBefore(intervalEnd)) {
				i++;
			}
		}
		return intervals;
	}

	protected LocalDateTime getIntervalEnd(List<LocalDateTime> times, List<WorkingHours> workingHours,
			LocalDateTime fromDate) {

		ZonedDateTime ref1 = getReferenceTimeService.getReferenceTime(fromDate);
		boolean w1 = getWorkingHoursService.isWorkingTimes(ref1, workingHours);

		LocalDateTime intervalEnd = fromDate;

		for (LocalDateTime time : times) {
			if (time.isBefore(fromDate)) {
				continue;
			}
			ZonedDateTime ref2 = getReferenceTimeService.getReferenceTime(time);
			boolean w2 = getWorkingHoursService.isWorkingTimes(ref2, workingHours);

			if (w1 != w2) {
				return intervalEnd;
			}
			intervalEnd = time;
		}

		return intervalEnd;
	}
}
