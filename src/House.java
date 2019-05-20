import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.lang.Math;
import java.util.List;

public class House {
    protected Room[] rooms;

    public House() {
        rooms = new Room[1];
        rooms[0] = new Room("Chulan");
    }

    public House(Room... rooms) {
        this.rooms = rooms;
    }

    protected void enterNotify(Human human) {
        System.out.println(human.getName() + " enters the house.");
    }

    public void broadcastTimeTick() {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].getHumans().size(); j++) {
                rooms[i].getHumans().get(j).timeTick();
            }
        }
    }

    public void place(int num, Interactable... interactables) {
        for (int i = 0; i < interactables.length; i++) {
            rooms[num].placeInteractable(interactables[i]);
        }
    }

    public void enter(int num, Human... humans) {
        for (int i = 0; i < humans.length; i++) {
            enterNotify(humans[i]);
            rooms[num].getHumanIn(humans[i]);
        }
    }

    public Room[] getRooms() {
        return rooms;
    }
}

class Room implements Comparable {
    private ArrayList<Human> humans;
    private ArrayList<Interactable> interactables;
    private String name;

    public Room(String name) {
        humans = new ArrayList<Human>();
        interactables = new ArrayList<Interactable>();
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    public Room(JSONObject json){
        interactables = new ArrayList<>();
        JSONArray jsonInteractables;
        try {
            jsonInteractables = new JSONArray(json.getString("interactables"));
        }catch (JSONException e){
            jsonInteractables = json.getJSONArray("interactables");
        }

        jsonInteractables.forEach(p -> {
            String name = ((JSONObject)p).getString("name");
            try {
                Interactable object = (Interactable) Class.forName(name).newInstance();
                interactables.add(object);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                System.err.println("Error: Invalid Json");
            }
        });
        name = json.getString("name");
    }

    public void placeInteractable(Interactable interactable) {
        interactables.add(interactable);
        System.out.println(interactable.getName() + " placed in " + name);
    }

    public void getHumanIn(Human human) {
        humans.add(human);
        human.setCurrentRoom(this);
        try {
            human.chooseAction();
        } catch (NoInteractablesException e) {
            System.out.println("Exception happened: " + e.getMessage() + " in " + e.getName());
        }
        System.out.println(human.getName() + " enters " + name);
    }

    public String getName() {
        return name;
    }

    public void makesSound(Human human) {
        for (int i = 0; i < humans.size(); i++) {
            if (Math.random() > 0.75) {
                humans.get(i).noticeNoise(human);
            }
        }
    }

    public void humanLeave(Human human) {
        int index = humans.indexOf(human);
        if (index >= 0) {
            humans.remove(index);
            System.out.println(human.getName() + " leaves " + name);
        }
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public ArrayList<Interactable> getInteractables() { return interactables; };

    public JSONObject getJson(){
        JSONObject jsonObject = new JSONObject();

        JSONArray array = new JSONArray();

        interactables.forEach(p -> array.put(p.getJson()));

        jsonObject.put("interactables", array);
        jsonObject.put("name", name);

        return jsonObject;
    }

    @Override
    public String toString() {
        return "Комната " + name;
    }

    @Override
    public int hashCode() {
        int prime = 1;
        int result = 1;
        result = (int)(result * prime + interactables.size() * Math.pow(prime, 2) + name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        if (!interactables.equals(other.interactables))
            return false;
        if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return this.hashCode() - o.hashCode();
    }
}
