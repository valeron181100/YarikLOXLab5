import java.util.ArrayList;

public class Flashlight implements Interactable {



    public void interact(Human human) {
        System.out.println(human.name + " lights the room up.");
        Room room = human.getCurrentRoom();
        ArrayList<Human> humans = room.getHumans();
        human.see(humans);
    }

    public String getName() {
        return "Flashlight";
    }
}

interface Interactable {
    void interact(Human human);

    String getName();
}