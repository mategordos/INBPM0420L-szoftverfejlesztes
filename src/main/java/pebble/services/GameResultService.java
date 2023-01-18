package pebble.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import pebble.database.guice.PersistenceModule;
import pebble.model.gameresults.GameResult;
import pebble.model.gameresults.GameResultDao;

import java.util.List;

/**
 *  A class that contains a Singleton that provides high level persisted functionality for class GameResult.
 */
public class GameResultService {
    private static GameResultService instance;
    private final GameResultDao gameResultDao;

    private GameResultService() {
        Injector injector = Guice.createInjector(new PersistenceModule("test"));
        this.gameResultDao = injector.getInstance(GameResultDao.class);
    }

    /**
     * Returns a persisted instance of a GameResult.
     * @return an instance of a GameResult.
     */
    public static GameResultService getInstance() {
        if (instance == null) {
            instance = new GameResultService();
        }

        return instance;
    }

    /**
     * Persists a GameResult.
     * @param gameResult the persisted GameResult
     */
    public void save(GameResult gameResult) {
        gameResultDao.persist(gameResult);
    }

    /**
     * Returns a list of all persisted GameResults.
     * @return a persisted list of GameResults
     */
    public List<GameResult> findAll() {
        return gameResultDao.findAll();
    }
}
