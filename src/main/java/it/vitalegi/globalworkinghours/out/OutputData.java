package it.vitalegi.globalworkinghours.out;

import java.util.ArrayList;
import java.util.List;

public class OutputData {

	protected List<List<String>> rows;

	public OutputData() {
		rows = new ArrayList<>();
	}

	protected int cols() {
		if (!rows.isEmpty()) {
			return rows.get(0).size();
		}
		return 0;
	}

	public List<List<String>> getRows() {
		return rows;
	}

	public void addRow(List<String> row) {
		rows.add(row);
	}

	public void addRows(List<List<String>> rows) {
		this.rows.addAll(rows);
	}

}
