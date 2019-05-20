import org.json.CDL;
import org.json.JSONObject;

public class Tests {
    public static void main(String[] args) {
        Room room = new Room("Kitchen");
        Human pilulkin = new Human("Pilulkin", Human.Gender.MALE);
        pilulkin.setCurrentRoom(room);
        room.getHumanIn(pilulkin);
        room.placeInteractable(new Canvas());
        room.placeInteractable(new Flashlight());
        room.placeInteractable(new Cover());
        JSONObject jsonObject = room.getJson();
        System.out.println(jsonObject);
        int  k = 0;
    }
}
