package tn.esprit.pi.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingProduitAspect {
    @Before("execution(* tn.esprit.pi.services.ProduitService.*(..))")
    void clogClientDisplay(JoinPoint joinPoint){

        log.info("METHODE PLAY: "+joinPoint.getSignature().getName());
    }
}
