package org.example.demo.exceptions;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.OptimisticLockException;
import org.example.demo.utils.AppLogger;
import org.example.demo.utils.FormattingUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Interceptor
@HandleBackedBeanExceptions
public class BackedBeanExceptionHandlingInterceptor implements Serializable {

    @Inject
    private AppLogger logger;

    @AroundInvoke
    public Object handleExceptions(InvocationContext context) throws Exception {
        try {
            return context.proceed();
        } catch (OptimisticLockException e) {
            setErrorMsg(context.getTarget(), FormattingUtils.getShortClassName(context.getMethod().getReturnType()) + " has already been modified by another user.");
        } catch (ValidationException e) {
            setErrorMsg(context.getTarget(), e.getMessage());
        } catch (Exception e) {
            setErrorMsg(context.getTarget(), "An unknown error occurred.");
            logger.log("Unhandled exception: " + e.getMessage());
            throw e;
        }
        return null;
    }

    private void setErrorMsg(Object invoker, String errorMessage) {
        Class<?> invokerClass = invoker.getClass();
        try {
            Method setErrorMsgMethod = invokerClass.getDeclaredMethod("setErrorMsg", String.class);
            setErrorMsgMethod.setAccessible(true);
            setErrorMsgMethod.invoke(invoker, errorMessage);
        } catch (NoSuchMethodException ex) {
            logger.log("No errorMsg property found in class: " + invokerClass.getName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            // Should never happen
        }
    }

}
