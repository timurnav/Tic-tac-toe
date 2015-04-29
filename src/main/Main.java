package main;

import entity.User;
import service.ConsoleHelper;
import service.GameService;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;



/**
 * Created by Тимур Мухитдинов on 17.04.2015.
 */
public class Main {

    public static void main(String[] args)
            throws IOException, SQLException, InterruptedException {

        UserService userService = new UserService();
        GameService gameService = new GameService();
        //Вход через id/регистрация нового пользователя
        User user = userService.logInOrRegistration();
        //получили Юзера, приветствуем его
        ConsoleHelper.greeting(user.getName());
        //спрашиваем чего он вообще хочет
        boolean isExit = false;
        while (!isExit){
            String s = ConsoleHelper.chooseOperation();
            if (s.equalsIgnoreCase("exit"))
                isExit=true;
            else if (s.equalsIgnoreCase("s"))
                //даем статистику
                UserService.getStatistic(user);
            else if (s.equalsIgnoreCase("p"))
                //играем
                gameService.letsPlay(user);
            else
                //ругаемся и повторяем цикл
                ConsoleHelper.writeString("Incorrect input");
            //после выполнения одной из операций запускаем цикл снова
            //пока не введут exit
        }
    }
}