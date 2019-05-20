import org.json.JSONObject;

import java.util.ArrayList;

public class Flashlight implements Interactable {

    private String name;

    public Flashlight(){
        name = "Flashlight";
    }

    public void interact(Human human) {
        System.out.println(human.name + " lights the room up.");
        Room room = human.getCurrentRoom();
        ArrayList<Human> humans = room.getHumans();
        human.see(humans);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return name.equals(((Interactable)obj).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Interactable " + name;
    }
}

interface Interactable {
    void interact(Human human);
    default JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        return jsonObject;
    }


    String getName();
}