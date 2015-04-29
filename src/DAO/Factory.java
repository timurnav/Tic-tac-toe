package DAO;

/**
 * Created by Тимур Мухитдинов on 17.04.2015.
 */
public class Factory {
    private static UserDAO userDAO = null;
    private static GameDAO gameDAO = null;
    private static Factory instance = null;

    private Factory() {
    }

    public static synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public GameDAO getGameDAO() {
        if (gameDAO == null) {
            gameDAO = new GameDAO();
        }
        return gameDAO;
    }
}
