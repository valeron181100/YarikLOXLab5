public class FlyableHuman extends Human implements Flyable {
    public FlyableHuman(String name) { super(name, Gender.COMBAT_HELICOPTER); }

    public void fly() {
        System.out.println(name + "flies above the ground.");
    }

    public void fly(Room room) {
        System.out.println(name + " flies to " + room.getName());
        room.getHumanIn(this);
    }

    @Override
    public void move(Room room) {
        this.fly(room);
    }
}

interface Flyable {
    void fly();

    void fly(Room room);
}
