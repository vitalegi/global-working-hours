package it.vitalegi.globalworkinghours.out;

import java.util.List;
import java.util.stream.Collectors;

public class CsvExport implements Printer {

	protected String columnSeparator = ";";
	protected String newLine = "\n";

	protected OutputData outputData;

	@Override
	public StringBuilder print(OutputData outputData) {
		StringBuilder sb = new StringBuilder();
		for (List<String> row : outputData.getRows()) {
			sb.append(row.stream()//
					.collect(Collectors.joining(columnSeparator)))//
					.append(newLine);
		}
		return sb;
	}
}