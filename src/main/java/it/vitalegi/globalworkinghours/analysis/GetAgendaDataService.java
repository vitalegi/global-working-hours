package it.vitalegi.globalworkinghours.analysis;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.globalworkinghours.GetReferenceTimeService;
import it.vitalegi.globalworkinghours.GetTimeComparationService;
import it.vitalegi.globalworkinghours.GetWorkingHoursService;
import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.out.OutputData;
import it.vitalegi.globalworkinghours.util.DateUtil;
import it.vitalegi.globalworkinghours.util.Pair;
import it.vitalegi.globalworkinghours.util.WorkingUtil;

/**
 * Gives a tabular representation of the data, compacting times in interval,
 * using the condition:
 * <li>at least one working hour
 * <li>no working hour
 *
 * @author giorg
 *
 */
@Service
public class GetAgendaDataService implements GetAnalysisService {

	@Autowired
	GetWorkingHoursService getWorkingHoursService;

	@Autowired
	GetReferenceTimeService getReferenceTimeService;

	@Autowired
	GetTimeComparationService getTimeComparationService;

	@Override
	public OutputData process(TimeZone refTimeZone, List<LocalDateTime> times, List<WorkingHours> workingHours) {
		OutputData data = new OutputData();

		data.addRow(headers(refTimeZone));

		List<Pair<LocalDateTime, LocalDateTime>> intervals = getTimeComparationService.getIntervals(times,
				workingHours);
		for (Pair<LocalDateTime, LocalDateTime> interval : intervals) {
			data.addRow(row(refTimeZone, interval, workingHours));
		}
		return data;
	}

	protected List<String> headers(TimeZone refTimeZone) {
		List<String> headers = new ArrayList<>();
		headers.add("From (" + refTimeZone.getID() + ")");
		headers.add("To (" + refTimeZone.getID() + ")");
		headers.add("Hours");
		headers.add("Working");
		return headers;
	}

	protected List<String> row(TimeZone refTimeZone, Pair<LocalDateTime, LocalDateTime> interval,
			List<WorkingHours> workingHours) {

		LocalDateTime start = interval.getValue1();
		LocalDateTime end = interval.getValue2();

		ZonedDateTime refFromTime = ZonedDateTime.of(start, refTimeZone.toZoneId());
		ZonedDateTime refToTime = ZonedDateTime.of(end, refTimeZone.toZoneId());

		List<String> row = new ArrayList<>();
		row.add(DateUtil.formatDateTimeLong(refFromTime));
		row.add(DateUtil.formatDateTimeLong(refToTime));

		row.add(Math.round(Duration.between(refFromTime, refToTime).toMinutes() / 60.0) + "");
		row.add(WorkingUtil.workingToString(getWorkingHoursService.isWorkingTimes(refFromTime, workingHours)));

		return row;
	}
}
