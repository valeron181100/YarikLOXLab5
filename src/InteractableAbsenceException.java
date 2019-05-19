public class InteractableAbsenceException extends RuntimeException {
    private String name;

    public InteractableAbsenceException(String msg, String name) {
        super(msg);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
