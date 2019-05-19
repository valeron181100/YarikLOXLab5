import java.util.ArrayList;

public class Cover implements Interactable {
    private ArrayList<Human> humansUnder;

    public Cover() {
        humansUnder = new ArrayList<Human>();
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
        return "Cover";
    }
}