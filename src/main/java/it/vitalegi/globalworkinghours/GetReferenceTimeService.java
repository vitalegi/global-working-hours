package it.vitalegi.globalworkinghours;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

@Service
public class GetReferenceTimeService {

	public ZonedDateTime getReferenceTime(LocalDateTime time) {
		return ZonedDateTime.of(time, ZoneOffset.UTC);
	}
}
