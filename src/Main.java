import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
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

        Time time = new Time();

        Bed bedFirst = new Bed();
        Bed bedSecond = new Bed();

        Flashlight fl = new Flashlight();
        Cover cover = new Cover();


        Transport spaceShip = new Transport("Spaceship", 4);

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
}
