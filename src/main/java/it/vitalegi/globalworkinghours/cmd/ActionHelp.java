package it.vitalegi.globalworkinghours.cmd;

import java.io.PrintStream;

import org.springframework.stereotype.Component;

@Component
public class ActionHelp {

	public void printManual() {
		PrintStream out = System.out;
		out.println(
				"--from=yyyy-MM-dd --to=yyyy-MM-dd [--tz=timeZone] [--cfg=path/to/file] [--m=week/list/agenda] [--p=cmd/csv]");
		out.println();
		out.println("Example usage:");
		out.println("--from=2020-02-01 --to=2020-02-29 -tz=Europe/Rome --cfg=./config.yml -m=week -p=cmd");
		out.println();
		out.println("--from=2020-02-01 --to=2020-02-29 -tz=UTC --cfg=./config.yml -m=week -p=cmd");
		out.println();
		out.println("Arguments:");
		out.println();
		out.println("from");
		out.println("  Date since when you want to have the analysis");
		out.println("to");
		out.println("  Date to when you want the analysis");
		out.println("tz");
		out.println("  TimeZone to be used. E.g.: Europe/Rome, UTC.");
		out.println("  Default: Europe/Rome");
		out.println("cfg");
		out.println("  path to configuration file, YML format");
		out.println("  Default: config.yml");
		out.println("m");
		out.println("  mode to use for the analysis.");
		out.println("    week: default, collapse data on a weekly bases");
		out.println("    list: for each time slot, gives info");
		out.println("    week: collapses adjacent equal slot");
		out.println("p");
		out.println("  presentation mode to be used");
		out.println("    cmd: default, print an ascii table");
		out.println("    csv: print in csv format");
	}
}
