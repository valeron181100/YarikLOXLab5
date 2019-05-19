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

}