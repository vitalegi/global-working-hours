package it.vitalegi.globalworkinghours;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.vitalegi.globalworkinghours.analysis.GetAnalysisService;
import it.vitalegi.globalworkinghours.analysis.GetAnalysisServiceFactory;
import it.vitalegi.globalworkinghours.bean.WorkingHours;
import it.vitalegi.globalworkinghours.cmd.ActionExec;
import it.vitalegi.globalworkinghours.cmd.ActionHelp;
import it.vitalegi.globalworkinghours.out.Printer;
import it.vitalegi.globalworkinghours.out.PrinterFactory;
import it.vitalegi.globalworkinghours.util.YamlUtil;

@Component
@Profile("!test")
public class GlobalWorkingHoursRunner implements ApplicationRunner {

	private final Logger LOG = LoggerFactory.getLogger(GlobalWorkingHoursRunner.class);

	@Autowired
	GetAnalysisServiceFactory getAnalysisServiceFactory;

	@Autowired
	PrinterFactory printerFactory;

	@Autowired
	ActionExec actionExec;

	@Autowired
	ActionHelp actionHelp;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (args.getSourceArgs() == null || args.getSourceArgs().length == 0 || args.getOptionValues("help") != null
				|| args.getOptionValues("h") != null) {
			actionHelp.printManual();
			return;
		}

		String config = getFirstValue(args, "cfg", "config.yml");
		String from = getFirstValue(args, "from");
		String to = getFirstValue(args, "to");
		String processorId = getFirstValue(args, "m", GetAnalysisServiceFactory.WEEK);
		String printerId = getFirstValue(args, "p", PrinterFactory.CMD);
		String timeZoneId = getFirstValue(args, "tz", "Europe/Rome");

		List<WorkingHours> workingHours = null;
		LocalDate fromDate = null;
		LocalDate toDate = null;
		GetAnalysisService processor = null;
		Printer printer = null;
		TimeZone refTimeZone = null;
		try {
			workingHours = execOrFriendlyFail(YamlUtil::getWorkingHours, config, "Invalid config file");
			fromDate = execOrFriendlyFail(d -> LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")), from,
					"Invalid from date");
			toDate = execOrFriendlyFail(d -> LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")), to,
					"Invalid to date");
			processor = execOrFriendlyFail(getAnalysisServiceFactory::getAnalysisService, processorId,
					"Processor not found");
			printer = execOrFriendlyFail(printerFactory::getPrinter, printerId, "Printer not found");
			refTimeZone = execOrFriendlyFail(TimeZone::getTimeZone, timeZoneId, "Invalid TimeZoneId");
		} catch (Exception e) {
			System.err.println("PARAMS:");
			args.getOptionNames().forEach(o -> {
				System.err.println(" - " + o + ": " + args.getOptionValues(o));
			});
			actionHelp.printManual();
			return;
		}
		actionExec.execute(fromDate, toDate, refTimeZone, workingHours, processor, printer);
	}

	protected <E> E execOrFriendlyFail(Function<String, E> f, String param, String msg) {
		try {
			return f.apply(param);
		} catch (Exception e) {
			System.err.println("ERROR: " + msg);
			System.err.println(e.getMessage());
			throw e;
		}
	}

	protected String getFirstValue(ApplicationArguments args, String key) {
		return getFirstValue(args, key, null);
	}

	protected String getFirstValue(ApplicationArguments args, String key, String defaultValue) {
		List<String> values = args.getOptionValues(key);
		if (values == null || values.isEmpty()) {
			return defaultValue;
		}
		return values.get(0);
	}

}