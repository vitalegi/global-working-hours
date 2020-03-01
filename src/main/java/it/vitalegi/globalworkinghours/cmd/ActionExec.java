package it.vitalegi.globalworkinghours.cmd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.vitalegi.globalworkinghours.GetTimesService;
import it.vitalegi.globalworkinghours.analysis.GetAnalysisService;
import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.out.Printer;

@Component
public class ActionExec {

	@Autowired
	private GetTimesService getTimesService;

	public void execute(LocalDate from, LocalDate to, TimeZone refTimeZone, List<WorkingHours> workingHours,
			GetAnalysisService processor, Printer printer) {
		List<LocalDateTime> times = getTimesService.getTimesWith1hSpan(from, to);

		System.out.println(printer.print(processor.process(refTimeZone, times, workingHours)));
	}
}
