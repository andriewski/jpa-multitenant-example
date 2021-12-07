package by.mark.multitenancy.model;

import by.mark.multitenancy.tenant.TenantIdProvider;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import static java.util.Objects.requireNonNull;

@Component
@NoArgsConstructor
public class TenantListener {

    @Setter(onMethod = @__(@Autowired))
    private TenantIdProvider tenantIdProvider;

    @PreUpdate
    @PreRemove
    @PrePersist
    public void setTenant(TenantAware entity) {
        String tenantId = requireNonNull(tenantIdProvider.getTenantId());
        entity.setTenantId(tenantId);
    }
}