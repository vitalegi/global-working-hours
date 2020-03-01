package it.vitalegi.globalworkinghours.analysis;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.globalworkinghours.GetReferenceTimeService;
import it.vitalegi.globalworkinghours.GetWorkingHoursService;
import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.out.OutputData;
import it.vitalegi.globalworkinghours.util.DateUtil;
import it.vitalegi.globalworkinghours.util.WorkingUtil;

/**
 * Retrieves data in a tabular format, giving, for each time, the working/non
 * working countries
 *
 * @author giorg
 *
 */
@Service
public class GetDetailedDataService implements GetAnalysisService {

	@Autowired
	GetWorkingHoursService getWorkingHoursService;

	@Autowired
	GetReferenceTimeService getReferenceTimeService;

	@Override
	public OutputData process(TimeZone refTimeZone, List<LocalDateTime> times, List<WorkingHours> workingHours) {

		OutputData data = new OutputData();

		data.addRow(headers(refTimeZone, workingHours));

		for (LocalDateTime time : times) {
			data.addRow(row(refTimeZone, time, workingHours));
		}
		return data;
	}

	protected List<String> headers(TimeZone refTimeZone, List<WorkingHours> workingHours) {
		List<String> headers = new ArrayList<>();
		headers.add("Time (" + refTimeZone.getID() + ")");
		headers.add("Working");
		workingHours.stream().map(WorkingHours::getName).forEach(name -> headers.add(name));
		return headers;
	}

	protected List<String> row(TimeZone refTimeZone, LocalDateTime time, List<WorkingHours> workingHours) {
		ZonedDateTime referenceDateTime = ZonedDateTime.of(time, refTimeZone.toZoneId());

		List<String> row = new ArrayList<>();
		row.add(DateUtil.formatDateTimeLong(referenceDateTime));

		row.add(WorkingUtil.workingToString(getWorkingHoursService.isWorkingTimes(referenceDateTime, workingHours)));

		for (WorkingHours workingHour : workingHours) {
			boolean workingTime = getWorkingHoursService.isWorkingTimes(referenceDateTime, workingHour);
			row.add(WorkingUtil.workingToString(workingTime));
		}
		return row;
	}
}
