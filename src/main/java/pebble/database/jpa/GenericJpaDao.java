package pebble.database.jpa;

import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;


/**
 * Defines high level database operations for database entities.
 * @param <T> entity type
 */
public abstract class GenericJpaDao<T> {

    protected Class<T> entityClass;
    protected EntityManager entityManager;

    /**
     * Creates the Jpa DAO.
     */
    public GenericJpaDao() {
        Class clazz = !getClass().getName().contains("$$EnhancerByGuice$$") ?
                getClass() :
                getClass().getSuperclass(); // dirty Guice trick
        entityClass =
                (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Returns the entity manager.
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets the entity manager.
     * @param entityManager the EntityManager that's set
     */
    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves the entity into a DB.
     * @param entity the entity that's saved
     */
    @Transactional
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    /**
     * Returns an entity based on a key.
     * @param primaryKey the key of the object
     * @return the entity that belongs to the key
     */
    @Transactional
    public Optional<T> find(Object primaryKey) {
        return Optional.ofNullable(entityManager.find(entityClass, primaryKey));
    }

    /**
     * Returns a list of all entities from the DB.
     * @return all the entities in current query
     */
    @Transactional
    public List<T> findAll() {
        TypedQuery<T> typedQuery =
                entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e",
                        entityClass);
        return typedQuery.getResultList();
    }

    /**
     * Removes an entity from the DB.
     * @param entity that's removed.
     */
    @Transactional
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    /**
     * Updates the DB with an entity.
     * @param entity that's updated with.
     */
    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

}

