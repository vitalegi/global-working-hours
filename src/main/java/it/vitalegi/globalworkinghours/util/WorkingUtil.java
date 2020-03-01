package it.vitalegi.globalworkinghours.util;

import java.util.List;
import java.util.stream.Collectors;

import it.vitalegi.globalworkinghours.bean.WorkingHours;

public class WorkingUtil {

	public static String workingToString(boolean working) {
		if (working) {
			return "W";
		}
		return "";
	}

	public static List<String> getNames(List<WorkingHours> workingHours) {
		return workingHours.stream().map(WorkingHours::getName).collect(Collectors.toList());
	}
}
