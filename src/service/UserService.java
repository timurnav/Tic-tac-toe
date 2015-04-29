package service;

import DAO.Factory;
import DAO.GameDAO;
import DAO.UserDAO;
import entity.Game;
import entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * Created by Тимур Мухитдинов on 18.04.2015.
 */
public class UserService {

    public static User logInOrRegistration() throws IOException, SQLException {
        User user;
        while (true){
            //ветвление id или 'R'
            ConsoleHelper.writeString("Enter your ID or " +
                    "\"R\" for registration");
            String s = ConsoleHelper.readString();
            //если всё таки 'r'
            if (s.trim().equalsIgnoreCase("r")) {
                ConsoleHelper.writeString("Enter your name");
                //создаем пользователя с введенным именем.
                user = new User(ConsoleHelper.readString());
                //сохраняем Юзера в БД
                Factory.getInstance().getUserDAO().addUser(user);
                //выводим в консоль id
                ConsoleHelper.writeString("Please remember your ID "
                        + user.getId());
                break;
            }
            //если ввели не 'r'
            else {
                //пробуем парсить
                try {
                    int id = Integer.parseInt(s);
                    //проверяем имеется ли такой id в базе
                    if (!Factory.getInstance().getUserDAO().consistsId(id)){
                        //если нет, то спрашиваем снова
                        ConsoleHelper.writeString("Incorrect id");
                        continue;
                    }
                    //получаем Юзера по указанному id
                    user = Factory.getInstance().getUserDAO().getUserById(id);
                    break;
                //если не парсится ругаемся и просив ввести id снова
                } catch (NumberFormatException e){
                    ConsoleHelper.writeString("Incorrect input");
                }
            }
        }
        //возвращаем Юзера, он не null
        return user;
    }

    public static void getStatistic(User user){
        ConsoleHelper.writeString("games = " + user.getGames() +
                "\nwins = " + user.getWins() +
                "\nloses = " + user.getLoses()+"\n\n");
    }

    static void setUserBusy(User user){
        user.setBusy(true); //делаем его занятым
        //и обновляем его в БД
        Factory.getInstance().getUserDAO().updateUser(user);
    }

}
