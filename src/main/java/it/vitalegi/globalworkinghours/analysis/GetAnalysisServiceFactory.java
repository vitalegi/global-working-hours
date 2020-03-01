package it.vitalegi.globalworkinghours.analysis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAnalysisServiceFactory {

	public static final String LIST = "LIST";
	public static final String AGENDA = "AGENDA";
	public static final String WEEK = "WEEK";

	@Autowired
	private GetDetailedDataService getDetailedDataService;

	@Autowired
	private GetAgendaDataService getAgendaDataService;

	@Autowired
	private GetWeekCalendarDataService getWeekCalendarDataService;

	private Map<String, GetAnalysisService> services;

	public GetAnalysisServiceFactory() {
		services = new HashMap<>();
	}

	@PostConstruct
	protected void init() {
		services.put(LIST, getDetailedDataService);
		services.put(AGENDA, getAgendaDataService);
		services.put(WEEK, getWeekCalendarDataService);
	}

	public GetAnalysisService getAnalysisService(String id) {
		if (id == null) {
			throw new NullPointerException("id null");
		}
		GetAnalysisService service = services.get(id.toUpperCase());
		if (service == null) {
			throw new IllegalArgumentException("id " + id + " non recognized");
		}
		return service;
	}
}
