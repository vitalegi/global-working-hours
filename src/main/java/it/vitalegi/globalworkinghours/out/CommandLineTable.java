package it.vitalegi.globalworkinghours.out;

import java.util.List;

public class CommandLineTable implements Printer {

	protected char columnSeparator = '|';
	protected char paddingCharacter = ' ';
	protected String newLine = "\n";

	@Override
	public StringBuilder print(OutputData outputData) {
		StringBuilder sb = new StringBuilder();
		int[] widths = getWidths(outputData);
		for (List<String> row : outputData.getRows()) {
			sb.append(printRow(widths, row, outputData)).append(newLine);
		}
		return sb;
	}

	protected int[] getWidths(OutputData outputData) {
		int[] widths = new int[outputData.cols()];
		for (int col = 0; col < outputData.cols(); col++) {
			widths[col] = getColumnWidth(col, outputData);
		}
		return widths;
	}

	protected int getColumnWidth(int col, OutputData outputData) {
		return outputData.getRows().stream().mapToInt(r -> r.get(col).length()).max().orElse(0);
	}

	protected StringBuilder printRow(int[] widths, List<String> values, OutputData outputData) {
		StringBuilder sb = new StringBuilder();
		for (int col = 0; col < outputData.cols(); col++) {
			sb.append(columnSeparator);
			sb.append(getTextWithPadding(values.get(col), widths[col]));
		}
		sb.append(columnSeparator);
		return sb;
	}

	protected String getTextWithPadding(String text, int expectedLength) {
		while (text.length() < expectedLength) {
			text += paddingCharacter;
		}
		return text;
	}
}
