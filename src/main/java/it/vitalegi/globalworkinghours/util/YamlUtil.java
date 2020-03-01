package it.vitalegi.globalworkinghours.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import it.vitalegi.globalworkinghours.bean.WorkingHours;

public class YamlUtil {

	public static List<WorkingHours> getWorkingHours(String filename) {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			return mapper.readValue(new File(filename), new TypeReference<List<WorkingHours>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
