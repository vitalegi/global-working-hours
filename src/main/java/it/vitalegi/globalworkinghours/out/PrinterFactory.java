package it.vitalegi.globalworkinghours.out;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class PrinterFactory {

	public static final String CMD = "CMD";
	public static final String CSV = "CSV";

	private Map<String, Printer> services;

	public PrinterFactory() {
		services = new HashMap<>();
	}

	@PostConstruct
	protected void init() {
		services.put(CMD, new CommandLineTable());
		services.put(CSV, new CsvExport());
	}

	public Printer getPrinter(String id) {
		if (id == null) {
			throw new NullPointerException("id null");
		}
		Printer service = services.get(id.toUpperCase());
		if (service == null) {
			throw new IllegalArgumentException("id " + id + " non recognized");
		}
		return service;
	}
}