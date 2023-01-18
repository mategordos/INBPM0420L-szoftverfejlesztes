package pebble.database.guice;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * A singleton that's responsible for the initialization of {@link PersistService}.
 */
@Singleton
public class JpaInitializer {

    /**
     * Starts a persitent service.
     * @param persistService the persistent service
     */
    @Inject
    public JpaInitializer(PersistService persistService) {
        persistService.start();
    }

}

