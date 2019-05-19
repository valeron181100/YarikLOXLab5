import org.json.JSONObject;

public class Tests {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        Room room = new Room("Kitchen");
        Human pilulkin = new Human("Pilulkin", Human.Gender.MALE);
        pilulkin.setCurrentRoom(room);
        room.getHumanIn(pilulkin);

    }
}
