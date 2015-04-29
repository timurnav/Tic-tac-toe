package service;

import DAO.Factory;
import DAO.GameDAO;
import entity.Game;
import entity.User;

import java.sql.SQLException;

/**
 * Created by Тимур Мухитдинов on 20.04.2015.
 */
public class DBHelper {

    //нам нужно знать можно ли начинать игру
    static boolean isOpponentNotPresent(int n) throws SQLException {
        Game updGame = Factory.getInstance().getGameDAO().getGameById(n);
        return updGame.getX().equals(updGame.getY());
    }

    //сохраняем изменения в статистике Юзера после игры
    public static void saveResultsOfGame(User user, GameDAO.ResultOfGame result){
        user.setBusy(false);
        user.setGames(user.getGames() + 1);
        if (result.equals(GameDAO.ResultOfGame.LOSER)){
            user.setLoses(user.getLoses()+1);
        }
        if (result.equals(GameDAO.ResultOfGame.WINNER)){
            user.setWins(user.getWins() + 1);
        }
        Factory.getInstance().getUserDAO().updateUser(user);
    }

    //сохраняем игру
    public static void saveGame(Game game){
        Factory.getInstance().getGameDAO().updateGame(game);
    }

    //загружаем игру
    public static Game loadGame(int n) throws SQLException {
        return Factory.getInstance().getGameDAO().getGameById(n);
    }

    public static Game getGame(Integer n) {
        Game game = Factory.getInstance().getGameDAO().getCreatedGame();
        if (game==null){//если созданных игр нет, создаем сами
            game = new Game(n);
            //затем сразу сохраняем новую игру
            Factory.getInstance().getGameDAO().addGame(game);
            ConsoleHelper.writeString("The game was created");

        } else {
            //если игра есть
            //изменяем статус игры
            game.setStatus(GameDAO.Status.PLAYING.toString());
            //устанавливаем игрока на вторую позицию
            game.setY(n);
            //и сразу обновляем
            Factory.getInstance().getGameDAO().updateGame(game);
            ConsoleHelper.writeString("Your opponent plays first");
        }
        return game;
    }
}
