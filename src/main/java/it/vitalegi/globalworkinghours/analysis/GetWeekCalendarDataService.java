package it.vitalegi.globalworkinghours.analysis;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.globalworkinghours.GetReferenceTimeService;
import it.vitalegi.globalworkinghours.GetWorkingHoursService;
import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.out.OutputData;
import it.vitalegi.globalworkinghours.util.DateUtil;
import it.vitalegi.globalworkinghours.util.WorkingUtil;

@Service
public class GetWeekCalendarDataService implements GetAnalysisService {

	private final Logger LOG = LoggerFactory.getLogger(GetWeekCalendarDataService.class);

	final static List<DayOfWeek> DAYS_OF_WEEK = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
			DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

	final static DayOfWeek WEEK_STARTS_BY = DAYS_OF_WEEK.get(0);

	@Autowired
	GetWorkingHoursService getWorkingHoursService;

	@Autowired
	GetReferenceTimeService getReferenceTimeService;

	@Override
	public OutputData process(TimeZone refTimeZone, List<LocalDateTime> times, List<WorkingHours> workingHours) {

		List<Week> weeks = groupByWeeks(times);

		OutputData data = new OutputData();
		for (Week week : weeks) {
			data.addRows(rows(week, refTimeZone, workingHours));
		}
		return data;
	}

	protected List<String> headers(TimeZone refTimeZone, List<WorkingHours> workingHours) {
		List<String> headers = new ArrayList<>();
		headers.add("Week");
		headers.add("Time (" + refTimeZone.getID() + ")");
		DAYS_OF_WEEK.forEach(day -> {
			headers.add(day.name());
			//WorkingUtil.getNames(workingHours).forEach(name -> headers.add(name));
			//headers.add(".");
		});
		return headers;
	}

	protected List<List<String>> rows(Week week, TimeZone refTimeZone, List<WorkingHours> workingHours) {
		List<List<String>> rows = new ArrayList<>();
		List<LocalTime> times = getLocalTimes(week);
		rows.add(headers(refTimeZone, workingHours));
		for (LocalTime time : times) {
			rows.add(row(week, time, refTimeZone, workingHours));
		}
		return rows;
	}

	protected List<String> row(Week week, LocalTime time, TimeZone refTimeZone, List<WorkingHours> workingHours) {
		List<String> row = new ArrayList<>();

		LocalDate startOfWeek = week.getWeekDate();
		LocalDate endOfWeek = startOfWeek.plusDays(6);

		row.add(DateUtil.formatDateLong(startOfWeek) + " - " + DateUtil.formatDateLong(endOfWeek));
		row.add(DateUtil.formatTime(time));

		LocalDate localDate = startOfWeek;
		for (DayOfWeek day : DAYS_OF_WEEK) {
			LocalDateTime localTime = LocalDateTime.of(localDate, time);
			ZonedDateTime referenceDateTime = ZonedDateTime.of(localTime, refTimeZone.toZoneId());

			boolean working = getWorkingHoursService.isWorkingTimes(referenceDateTime, workingHours);
			row.add(WorkingUtil.workingToString(working));

			for (WorkingHours workingHour : workingHours) {
				working = getWorkingHoursService.isWorkingTimes(referenceDateTime, workingHour);
				//row.add(WorkingUtil.workingToString(working));
			}
			//row.add(".");

			localDate = localDate.plusDays(1);
		}

		return row;
	}

	protected List<Week> groupByWeeks(List<LocalDateTime> times) {
		List<Week> weeks = new ArrayList<>();

		for (LocalDateTime time : times) {
			LocalDate startOfWeek = time.with(ChronoField.DAY_OF_WEEK, WEEK_STARTS_BY.getValue()).toLocalDate();

			Week week = weeks.stream().filter(w -> w.getWeekDate().equals(startOfWeek)).findFirst().orElse(null);
			if (week == null) {
				week = new Week(startOfWeek);
				weeks.add(week);
			}
			if (!week.getTimes().contains(time.toLocalTime())) {
				week.getTimes().add(time.toLocalTime());
			}
		}
		weeks.sort(Comparator.comparing(Week::getWeekDate));
		return weeks;
	}

	protected List<LocalTime> getLocalTimes(Week week) {
		return week.getTimes().stream()//
				.distinct()//
				.sorted()//
				.collect(Collectors.toList());
	}

	private static class Week {
		LocalDate weekDate;
		List<LocalTime> times;

		public Week(LocalDate weekDate) {
			this.weekDate = weekDate;
			times = new ArrayList<>();
		}

		public LocalDate getWeekDate() {
			return weekDate;
		}

		public List<LocalTime> getTimes() {
			return times;
		}
	}
}
