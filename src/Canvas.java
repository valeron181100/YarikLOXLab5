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
