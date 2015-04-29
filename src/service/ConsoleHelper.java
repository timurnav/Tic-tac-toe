package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Тимур Мухитдинов on 18.04.2015.
 */
public class ConsoleHelper {
    static BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    public static int getNumber(int[] canvas){
        ConsoleHelper.writeString("Enter the number of square");
        while (true){
            try {
                int n = Integer.parseInt(reader.readLine());
                if (n >= 0 && n < canvas.length && canvas[n]==0){
                    return n;
                }
                System.out.println("Choose free cell and enter its number");
            } catch (NumberFormatException e) {
                System.out.println("Please enter the number");
            } catch (IOException e) {
            }
        }
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static void greeting(String name){
        System.out.println("\nhello, " + name + "!\n");
    }

    public static void writeString(String string){
        System.out.println(string);
    }

    public static String chooseOperation() throws IOException {
            System.out.println("To play enter 'p'\n" +
                    "To show your statistics enter 's'\n" +
                    "To exit enter 'exit'\n");
            return readString();
    }

    public static void drawCanvas(int[] canvas){

        System.out.println("     |     |     ");
        for (int i = 0; i < canvas.length; i++) {
            if (i!=0){
                if (i%3==0) {
                    System.out.println();
                    System.out.println("_____|_____|_____");
                    System.out.println("     |     |     ");
                }
                else
                    System.out.print("|");
            }

            if (canvas[i]==0) System.out.print("  " + i + "  ");
            if (canvas[i]==1) System.out.print("  X  ");
            if (canvas[i]==2) System.out.print("  O  ");
        }
        System.out.println();
        System.out.println("     |     |     ");
    }
}
