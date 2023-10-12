package org.waitlight.codememo.common.mvc.deprecated;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DeprecatedEndpointAspect {
    @Before("@annotation(DeprecatedEndpoint)")
    public void interceptMethod(JoinPoint joinPoint) {
        throw new UnsupportedOperationException();
    }
}