public class Bed implements Interactable {
    public void interact(Human human) {
        System.out.println(human.getName() + " sleeps... zZzZZZz");
        if (Math.random() > 0.5) {
            human.snork();
        }
    }

    public String getName() {
        return "Bed";
    }

}
