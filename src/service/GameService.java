package service;

import DAO.Factory;
import DAO.GameDAO;
import DAO.UserDAO;
import entity.Game;
import entity.User;
import logic.GameLogic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Тимур Мухитдинов on 18.04.2015.
 */
public class GameService {

    public void letsPlay(User user) throws SQLException, IOException, InterruptedException {
        Game game;
        //проверяем занят ли Юзер
        if(!user.getBusy()){
            //если нет, находим ему занятие
            UserService.setUserBusy(user);
            ConsoleHelper.writeString("You have no active games");
            //получаем игру, здесь нам не важно есть ли уже созданная игра или нет
            //если она есть то Юзер становится вторым игроком, если нет то первым
            game = DBHelper.getGame(user.getId());
        }
        else {
            ConsoleHelper.writeString("You have active game");
            game = Factory.getInstance().getGameDAO().getActiveGame(user.getId());
        }
        if (DBHelper.isOpponentNotPresent(game.getId())) {
            ConsoleHelper.writeString("You have to wait your opponent");
            ConsoleHelper.writeString("5 to update");
            TimeUnit.SECONDS.sleep(5);
            letsPlay(user);
        } else GameLogic.playing(game, user);

        //в любом случае попадаем сюда, запускаем игру,
        //указывая кто именно играет

    }


}
