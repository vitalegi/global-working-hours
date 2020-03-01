package it.vitalegi.globalworkinghours.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.vitalegi.globalworkinghours.GlobalWorkingHoursRunner;

@Component
@Aspect
public class LoggingAspect {

	private final Logger LOG = LoggerFactory.getLogger(GlobalWorkingHoursRunner.class);

	@Around("@annotation(LogExecutionTime)")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		long startTime = System.currentTimeMillis();
		LOG.info("Call method: " + joinPoint.getSignature().getName());
		LOG.debug("Arguments: " + Arrays.toString(joinPoint.getArgs()));
		try {
			Object out = joinPoint.proceed();

			LOG.info("End OK. Duration [{}]ms", System.currentTimeMillis() - startTime);
			return out;
		} catch (Throwable e) {
			LOG.info("End KO. Duration [{}]ms Error: [{}] Message: {}", System.currentTimeMillis() - startTime,
					e.getClass().getName(), e.getMessage());
			throw e;
		}
	}

}