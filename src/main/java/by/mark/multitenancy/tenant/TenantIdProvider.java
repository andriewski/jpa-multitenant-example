package by.mark.multitenancy.tenant;

import by.mark.multitenancy.filter.TenantContext;
import org.springframework.stereotype.Component;

@Component
public class TenantIdProvider {

    public String getTenantId() {
        return TenantContext.getCurrentTenant();
    }
}
