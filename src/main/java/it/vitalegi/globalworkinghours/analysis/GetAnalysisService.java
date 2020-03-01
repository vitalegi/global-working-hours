package it.vitalegi.globalworkinghours.analysis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.logging.LogExecutionTime;
import it.vitalegi.globalworkinghours.out.OutputData;

public interface GetAnalysisService {

	@LogExecutionTime
	public OutputData process(TimeZone refTimeZone, List<LocalDateTime> times, List<WorkingHours> workingHours);
}
