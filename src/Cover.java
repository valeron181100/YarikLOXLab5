import org.json.JSONObject;

import java.util.ArrayList;

public class Cover implements Interactable {
    private ArrayList<Human> humansUnder;

    private String name;


    public Cover() {
        humansUnder = new ArrayList<Human>();
        name = "Cover";
    }

    public void interact(Human human) {
        if (!humansUnder.contains(human)) {
            humansUnder.add(human);
            human.setIsScary(true);
            System.out.println(human.name + " is now covered.");
        } else {
            int index = humansUnder.indexOf(human);
            if (index > 0) {
                humansUnder.remove(index);
                System.out.println(human.name + " is now uncovered.");
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
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
    public String toString() {
        return "Interactable " + name;
    }
}