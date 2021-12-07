package by.mark.multitenancy.config;

import by.mark.multitenancy.tenant.TenantIdProvider;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;

import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
class MultitenantEntityManagerFactory implements EntityManagerFactory {

    private final EntityManagerFactory delegate;
    private final TenantIdProvider tenantIdProvider;

    @Override
    public EntityManager createEntityManager() {
        return getWithMultitenantFilter(delegate.createEntityManager());
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return getWithMultitenantFilter(delegate.createEntityManager(map));
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        return getWithMultitenantFilter(delegate.createEntityManager(synchronizationType));
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return getWithMultitenantFilter(delegate.createEntityManager(synchronizationType, map));
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return delegate.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return delegate.getMetamodel();
    }

    @Override
    public boolean isOpen() {
        return delegate.isOpen();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public Map<String, Object> getProperties() {
        return delegate.getProperties();
    }

    @Override
    public Cache getCache() {
        return delegate.getCache();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return delegate.getPersistenceUnitUtil();
    }

    @Override
    public void addNamedQuery(String name, Query query) {
        delegate.addNamedQuery(name, query);
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return delegate.unwrap(cls);
    }

    @Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
        delegate.addNamedEntityGraph(graphName, entityGraph);
    }

    @SuppressWarnings("resource")
    private EntityManager getWithMultitenantFilter(EntityManager em) {
        ofNullable(tenantIdProvider.getTenantId())
            .ifPresent(tenantId -> {
                Session session = em.unwrap(Session.class);
                Filter filter = session.enableFilter("tenantFilter");
                filter.setParameter("tenantId", tenantId);
                filter.validate();
            });

        return em;
    }
}
