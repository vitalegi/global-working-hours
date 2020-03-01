package it.vitalegi.globalworkinghours;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GetTimesService {

	public List<LocalDateTime> getTimesWith1hSpan(LocalDate fromDay, LocalDate toDay) {

		return getTimesWithMinuteSpan(fromDay, toDay, 0);
	}

	public List<LocalDateTime> getTimesWithMinuteSpan(LocalDate fromDay, LocalDate toDay, int... minutes) {
		if (minutes == null || minutes.length == 0) {
			throw new IllegalArgumentException("Provide at least 1 minute");
		}
		List<LocalDateTime> times = new ArrayList<>();

		LocalDate currDay = fromDay;
		while (!currDay.isAfter(toDay)) {
			for (int i = 0; i < 24; i++) {
				for (int minute : minutes) {
					times.add(LocalDateTime.of(currDay, LocalTime.of(i, minute)));
				}
			}
			currDay = currDay.plusDays(1);
		}
		return times;
	}

	public List<LocalDateTime> getDays(LocalDate fromDay, LocalDate toDay) {

		List<LocalDateTime> times = new ArrayList<>();

		LocalDate currDay = fromDay;
		while (!currDay.isAfter(toDay)) {
			times.add(LocalDateTime.of(currDay, LocalTime.of(0, 0)));
			currDay = currDay.plusDays(1);
		}
		return times;
	}
}
