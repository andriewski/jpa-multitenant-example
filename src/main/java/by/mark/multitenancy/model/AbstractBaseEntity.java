package by.mark.multitenancy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = "string"))
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@EntityListeners(TenantListener.class)
public abstract class AbstractBaseEntity implements TenantAware, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "tenant_id", length = 30)
    private String tenantId;

    protected AbstractBaseEntity(String tenantId) {
        this.tenantId = tenantId;
    }
}