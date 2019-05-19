import org.json.JSONObject;

public class Canvas implements Interactable {

    private String name;

    public Canvas(){
        name = "Canvas";
    }

    public Canvas(JSONObject json){
        name = json.getString("name");
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
