import org.json.JSONObject;

public class Canvas implements Interactable {

    private String name;

    public Canvas(){
        name = "Canvas";
    }


    @Override
    public void interact(Human human) {
        System.out.println(human.getName() + " draws...");
    }

    @Override
    public String getName() {
        return name;
    }

}
