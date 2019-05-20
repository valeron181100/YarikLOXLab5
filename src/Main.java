import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void start(Stack<Room> roomStack){
        class Time {
            private int time;

            public Time() {
                time = 0;
            }

            public void run(House house, int maxTime) {
                for (int i = 0; i < maxTime; i++) {
                    time++;
                    System.out.println("\nCurrent time: " + time);
                    house.broadcastTimeTick();
                }
            }
        }

        if(roomStack.size() < 4) {
            System.err.println("Unable to start the program: the number of elements in the collection is at least 4!");
            return;
        }

        Time time = new Time();

        Bed bedFirst = new Bed();
        Bed bedSecond = new Bed();

        Flashlight fl = new Flashlight();
        Cover cover = new Cover();


        Transport spaceShip = new Transport("Spaceship", roomStack.pop(),roomStack.pop(),roomStack.pop(),roomStack.pop());

        Human pilulkin = new Human("Pilulkin", Human.Gender.MALE);
        Human tubik = new Human("Tubik", Human.Gender.MALE);
        Human jenshina = new Human("Jenshina", Human.Gender.FEMALE);
        Human korotishka = new Human("Korotishka", Human.Gender.MALE);

        spaceShip.place(0, fl, cover);
        spaceShip.place(1, fl, cover, new Canvas());
        spaceShip.enter(0, pilulkin);
        spaceShip.enter(1, tubik, jenshina, korotishka);

        time.run(spaceShip, 5);
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Введите путь к файлу!");
            System.exit(0);
        }

        CollectionManager manager = new CollectionManager(args[0]);
        if(!manager.isFileExists()){
            System.exit(0);
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("If you don't know how to use this program, enter 'help'");

        while(true){

            String line = "";

            try{
                line = scanner.nextLine();
            }catch (NoSuchElementException e){
                System.err.println("Sudden shutdown");
                System.exit(0);
            }
            try {
                Commands command = new CommandParser(line).parse();
                if(command != null) {
                    if(command != Commands.START)
                        manager.runCommand(command);
                    else
                        start(manager.getStackCollection());
                }
                else{
                    System.err.println("Error: invalid command!");
                }
            }catch (EmptyInputException e){
                System.err.println("Error: empty input!");
            }catch (NoSuchElementException e){
                System.err.println("Error: there is no json element!");
            }catch (JSONException e){
                System.err.println("Error: invalid json element!");
            }
        }
    }
}
