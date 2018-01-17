package tech.lapsa.insurance.dao.beans;

import java.time.Duration;
import java.time.Instant;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.logging.MyLogger;

public class LoggingInterceptor {

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
	}
    }
}
