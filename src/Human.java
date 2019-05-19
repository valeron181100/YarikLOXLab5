import java.util.ArrayList;

public class Human {
    protected String name;
    protected Gender gender;
    protected boolean isScary;
    private Room currentRoom;
    protected Action action;

    enum Gender {
        MALE,
        FEMALE,
        COMBAT_HELICOPTER
    }

    public Human(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public class Action {
        private int duration;
        private Interactable interactable;

        public Action(int duration, Interactable interactable) {
            this.duration = duration;
            this.interactable = interactable;
        }

        public void act() {
            Human.this.interact(interactable);
            duration--;
            if (duration == 0) {
                try {
                    Human.this.chooseAction();
                } catch (NoInteractablesException e) {
                    System.out.println("Exception happened: " + e.getMessage() + " in " + e.getName());
                }
            }
        }

        public Interactable getInteractable() {
            return interactable;
        }
    }

    public void timeTick() {
        if (action == null) {
            try {
                chooseAction();
                action.act();
            } catch (NoInteractablesException e) {
                System.out.println("Exception happened: " + e.getMessage() + " in " + e.getName());
            }
        } else {
            action.act();
        }
    }

    public void chooseAction(int duration, Interactable interactable) {
        this.action = new Action(duration, interactable);
    }

    public void chooseAction() throws NoInteractablesException {
        if (currentRoom.getInteractables().size() > 0) {
            int randomInteractableNum = (int) Math.floor(Math.random() * currentRoom.getInteractables().size());
            Interactable randomInteractable = currentRoom.getInteractables().get(randomInteractableNum);
            int randomDuration = (int) Math.floor(Math.random() * 3 + 1);
            action = new Action(randomDuration, randomInteractable);
        } else {
            throw new NoInteractablesException("No interactables in room with human", currentRoom.getName());
        }
    }

    public void say(String phrase) {
        System.out.println(name + " says: \"" + phrase + "\"");
        currentRoom.makesSound(this);
    }

    public void think(String thought) {
        System.out.println(name + " thinks: \"" + thought + "\"");
    }

    public void snork() {
        System.out.println(name + " snorks");
        currentRoom.makesSound(this);
    }

    public void move(Room room) {
        System.out.println(name + " goes to " + room.getName());
        room.getHumanIn(this);
    }

    public void see(ArrayList<Human> humans) {
        for (int i = 0; i < humans.size(); i++) {
            if (!humans.get(i).equals(this)) {
                System.out.println(name + " sees " + humans.get(i).getName());
                if (humans.get(i).isScary) {
                    this.flee();
                    break;
                }
            }
        }
    }

    public void noticeNoise(Human human) {
        if (human != this) {
            if (!isScary) {
                if (human.isScary()) {
                    this.flee();
                }
            }

            if (action.getInteractable() instanceof Bed) {
                System.out.println(name + " woke up because of noise made by " + human.getName());
                try {
                    chooseAction();
                } catch (NoInteractablesException e) {
                    System.out.println("Exception happened: " + e.getMessage() + " in " + e.getName());
                }
            }
        }
    }

    public void interact(Interactable inter) throws InteractableAbsenceException {
        boolean isFound = false;
        for (int i = 0; i < currentRoom.getInteractables().size(); i++) {
            if (currentRoom.getInteractables().get(i) == inter) {
                isFound = true;
            }
        }
        if (!isFound) {
            throw new InteractableAbsenceException("There is no such interactable in this room.", inter.getName());
        }
        System.out.println(name + " uses " + inter.getName());
        inter.interact(this);
    }

    public void flee() {
        System.out.println(name + " flees");
        action = null;
        currentRoom.humanLeave(this);
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    public void setIsScary(boolean value) {
        isScary = value;
    }

    public boolean isScary() {
        return isScary;
    }

}
