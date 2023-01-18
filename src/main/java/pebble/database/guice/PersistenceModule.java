package pebble.database.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * A guice module that incapsulates persistence functionalities.
 */
public class PersistenceModule extends AbstractModule {

    private final String jpaUnit;

    /**
     * Constructor of the PersistenceModule class.
     * @param jpaUnit the functionality that has to be incapsulated
     */
    public PersistenceModule(String jpaUnit) {
        this.jpaUnit = jpaUnit;
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(jpaUnit));
        bind(JpaInitializer.class).asEagerSingleton();
    }

}
