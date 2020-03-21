package it.vitalegi.globalworkinghours;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import it.vitalegi.globalworkinghours.analysis.GetAnalysisService;
import it.vitalegi.globalworkinghours.analysis.GetDetailedDataService;
import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.cmd.ActionExec;
import it.vitalegi.globalworkinghours.out.CommandLineTable;
import it.vitalegi.globalworkinghours.out.Printer;

@SpringBootTest
@ActiveProfiles("test")
class GlobalWorkingHoursApplicationTests {

	@Autowired
	GetDetailedDataService getDetailedDataService;

	@Autowired
	ActionExec actionExec;

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		List<WorkingHours> workingHours = Arrays.asList(//
				WorkingHours.newInstance()//
						.name("IT")//
						.startHour(8).endHour(18)//
						.timezone(TimeZone.getTimeZone("Europe/Rome"))//
						.workingDays(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY).build(), //
				WorkingHours.newInstance()//
						.name("RO")//
						.startHour(8).endHour(18)//
						.timezone(TimeZone.getTimeZone("Europe/Bucharest"))//
						.workingDays(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY).build());

		LocalDate fromDate = LocalDate.of(2020, 4, 6);
		LocalDate toDate = LocalDate.of(2020, 4, 8);
		GetAnalysisService processor = getDetailedDataService;
		Printer printer = new CommandLineTable();
		TimeZone refTimeZone = TimeZone.getTimeZone("Europe/Rome");
		actionExec.execute(fromDate, toDate, refTimeZone, workingHours, processor, printer);
	}
}
