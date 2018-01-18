package tech.lapsa.insurance.dao.beans;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.logging.MyLogger;

public class LoggingInterceptor {

    private static final Map<Class<?>, Map<String, StatHolder>> CALLS_STATISTICS = new ConcurrentHashMap<>();

    private static class StatHolder {

	private long count = 0;
	private long duration = 0;

	private synchronized StatHolder add(Duration callDuration) {
	    count++;
	    duration += callDuration.toMillis();
	    return this;
	}

	private Duration getDurationTotal() {
	    return Duration.ofMillis(duration);
	}

	private long getCountTotal() {
	    return count;
	}

	private Duration getDurationAverage() {
	    return Duration.ofMillis(duration / count);
	}

	@Override
	public String toString() {
	    return MyStrings.format("CALLS COUNT %1$s, TOTAL DURATION %2$s, AVERAGE DURATION %3$s ",
		    getCountTotal(),
		    getDurationTotal(),
		    getDurationAverage());
	}
    }

    @AroundInvoke
    public Object methodInterceptor(final InvocationContext ctx) throws Exception {
	final String method = ctx.getMethod().getName();
	final Class<?> clazz = ctx.getMethod().getDeclaringClass();
	final MyLogger logger = MyLogger.newBuilder()
		.withNameOf(ctx.getTarget().getClass())
		.addPrefix(MyStrings.format("%1$s.%2$s", clazz, method))
		.build();
	final Instant started = Instant.now();
	logger.TRACE.log("STARTED");
	try {
	    final Object result = ctx.proceed();
	    logger.TRACE.log("COMPLETED");
	    return result;
	} finally {
	    final Instant completed = Instant.now();
	    final Duration dur = Duration.between(started, completed);
	    logger.DEBUG.log("CALL DURATION %1$s", dur);
	    final StatHolder callsStat = CALLS_STATISTICS
		    .computeIfAbsent(clazz, clz -> new ConcurrentHashMap<>())
		    .computeIfAbsent(method, meth -> new StatHolder())
		    .add(dur);
	    logger.DEBUG.log("SUMMARY %1$s", callsStat);
	    logger.TRACE.log("CALL STATISTICS: %1$s", CALLS_STATISTICS);
	}
    }
}
