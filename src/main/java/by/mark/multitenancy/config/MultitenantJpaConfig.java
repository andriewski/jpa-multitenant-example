package by.mark.multitenancy.config;

import by.mark.multitenancy.tenant.TenantIdProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class MultitenantJpaConfig implements BeanPostProcessor {

    private final TenantIdProvider tenantIdProvider;

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) {
        if (bean instanceof EntityManagerFactory) {
            return new MultitenantEntityManagerFactory((EntityManagerFactory) bean, tenantIdProvider);
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
