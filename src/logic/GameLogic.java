package logic;

import DAO.GameDAO;
import entity.Game;
import entity.User;
import service.ConsoleHelper;
import service.DBHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Тимур Мухитдинов on 18.04.2015.
 */
public class GameLogic {

    /*
    int[][] canvas = {{00, 01, 02},
                      {10, 11, 12},
                      {20, 21, 22}}
    //00-01-02
    //10-11-12
    //20-21-22
    //00-10-20
    //01-11-21
    //02-12-22
    //00-11-22
    //20-11-02

    int [] canvas = {0,0,0, // 0 1 2
                       0,0,0, // 3 4 5
                       0,0,0};// 6 7 8
    //012, 345, 678, 036, 147, 258, 048, 246*/

    public static void playing(Game game, User user)
            throws SQLException, IOException, InterruptedException {
        //обновляем игру, актуально при повторном входе
        Game updGame = DBHelper.loadGame(game.getId());
        //проверяем не проиграл ли игрок:
        //если последний ход соперника был победным,
        //то игра имеет статус "GAME_OVER"
        //получаем поле в виде массива
        int [] canvas = decrypt(updGame.getField());
        //проверяем наличие свободных ячеек
        if (updGame.getCurrent().equals(GameDAO.CurrentPlayer.D.toString())){
            updGame.setStatus(GameDAO.Status.GAME_OVER.toString());
            DBHelper.saveResultsOfGame(user, GameDAO.ResultOfGame.DRAW);
            //сохраняем все изменения на поле
            DBHelper.saveGame(updGame);
            ConsoleHelper.drawCanvas(canvas);
            ConsoleHelper.writeString("Draw");
            return;
        }
        if (updGame.getStatus().equals(GameDAO.Status.GAME_OVER.toString())){
            //обновляем и сохраняем статистику игрока
            DBHelper.saveResultsOfGame(user, GameDAO.ResultOfGame.LOSER);
            ConsoleHelper.drawCanvas(canvas);
            ConsoleHelper.writeString("You lose");
            return;
        }
        //ура-ура игрок не проиграл!

        //рисуем текущее поле
        ConsoleHelper.drawCanvas(canvas);
        //проверяем чей ход:
        if ((       updGame.getX().equals(user.getId())
                &&  updGame.getCurrent().equals(GameDAO.CurrentPlayer.X.toString()))
                || (updGame.getY().equals(user.getId())
                &&  updGame.getCurrent().equals(GameDAO.CurrentPlayer.Y.toString()))){
            //если право хода у игрока
            int n = ConsoleHelper.getNumber(canvas);
            canvas[n] = updGame.getX().equals(user.getId()) ? 1 : 2;
            //обновляем поле игры
            updGame.setField(crypt(canvas));
            //сохраняем все изменения на поле
            //проверяем был ли ход победным или ничья
            if (isGameOver(n, canvas)){
                //меняем значение статуса игры на GAME_OVER, сохраним в конце хода
                updGame.setStatus(GameDAO.Status.GAME_OVER.toString());
            } else if (isDraw(canvas)){
                updGame.setCurrent(GameDAO.CurrentPlayer.D.toString());
            } else {
                //если ход не был победным
                //меняем значение CurrentPlayer на противоположное
                if(updGame.getCurrent().equals(GameDAO.CurrentPlayer.Y.toString()))
                    updGame.setCurrent(GameDAO.CurrentPlayer.X.toString());
                else updGame.setCurrent(GameDAO.CurrentPlayer.Y.toString());
            }
            //сохраняем все изменения на поле
            DBHelper.saveGame(updGame);
            if (updGame.getStatus().equals(GameDAO.Status.GAME_OVER.toString())){
                //обновляем и сохраняем статистику игрока
                DBHelper.saveResultsOfGame(user, GameDAO.ResultOfGame.WINNER);
                ConsoleHelper.writeString("You win");
                return;
            }

        } else {
            //если право хода у соперника
            ConsoleHelper.writeString("Waiting for 5 sec");
            TimeUnit.SECONDS.sleep(5);
        }
        //запускаем рекурсию
        playing(game, user);
        /**
         * если игрок сейчас делал ход, то происходит смена хода: CurrentPlayer меняя свое
         * значение, меняет так же и порядок ветвления в методе. Если игрок был в ожидании
         * хода противника, то всё зависит от того, успел ли противник сделать ход.
         * Если проитвник не успел, то повторяется ожидание.
         * Если успел, то CurrentPlayer в БД изменяет свое значение и в следующей итерации
         * рекурсии игрока метод предлагает уже игроку сделать ход.
         * У противника в свою очередь метод так же подходит к рекурсивному вызову,
         * но в следующей итерации он натыкается на ожидание хода игрока.
         */
    }

    //получаем массив из инта
    static Integer crypt(int[] canvas){
        int res = 0;
        for (int i = 0; i < 9; i++) {
            res = res * 10 + canvas[i];
        }
        return res;
    }

    //получаем инт из массива
    static int [] decrypt(int n){
        int [] res = new int[9];
        for (int i = 8; i >-1; i--) {
            res[i] = n%10;
            n /= 10;
        }
        return res;
    }

    //проверяем есть ли свободные ячейки
    static boolean isDraw(int [] canvas){
        for (int i : canvas){
            if (i==0) return false;
        }
        return true;
    }

    //проверяем был ли ход победным
    static boolean isGameOver(int n, int[] canvas){
        // 0 1 2
        // 3 4 5
        // 6 7 8
        //поиск совпадений по горизонтали
        int row = n-n%3; //номер строки - проверяем только её
        if (canvas[row]==canvas[row+1] &&
                canvas[row]==canvas[row+2]) return true;
        //поиск совпадений по вертикали
        int column = n%3; //номер столбца - проверяем только его
        if (canvas[column]==canvas[column+3])
            if (canvas[column]==canvas[column+6]) return true;
        //мы здесь, значит, первый поиск не положительного результата
        //если значение n находится на одной из граней - возвращаем false
        if (n%2!=0) return false;
        //проверяем принадлежит ли к левой диагонали значение
        if (n%4==0){
            //проверяем есть ли совпадения на левой диагонали
            if (canvas[0] == canvas[4] &&
                    canvas[0] == canvas[8]) return true;
            if (n!=4) return false;
        }
        return canvas[2] == canvas[4] &&
                canvas[2] == canvas[6];
    }
}
