package org.example.demo.interceptors;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Logger;

@Interceptor
@PleaseLogThis
public class LoggingInterceptor {

    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        // Log the method invocation
        String methodName = context.getMethod().getName();
        logger.warning("Method " + methodName + " is being invoked");

        // Proceed with the original method invocation
        Object result = context.proceed();

        // Log the result after method execution
        logger.warning("Method " + methodName + " has completed execution");

        return result;
    }
}