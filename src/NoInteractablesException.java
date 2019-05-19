public class NoInteractablesException extends Exception {
    private String name;

    public NoInteractablesException(String msg, String name) {
        super(msg);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
