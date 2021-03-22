package swt6.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManagerFactory;

public class JpaInterceptor {
    private EntityManagerFactory entityManagerFactory;

    protected EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory emFactory) {
        this.entityManagerFactory = emFactory;
    }

    public Object holdEntityManager(ProceedingJoinPoint pjp) throws Throwable {
        if (entityManagerFactory == null)
            throw new IllegalArgumentException("Property 'entityManagerFactory' is required");

        boolean participate = false;
        if (TransactionSynchronizationManager.hasResource(entityManagerFactory))
            participate = true;
        else {
            JpaUtil.openEntityManager(entityManagerFactory);
        }

        try {
            return pjp.proceed(); // delegates to method of target class.
        } finally {
            if (!participate) {
                JpaUtil.closeEntityManager(entityManagerFactory);
            }
        }
    }
}
