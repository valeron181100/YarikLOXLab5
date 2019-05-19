public class Transport extends House {
    private String name;

    public Transport(String name, int num) {
        this.name = name;
        rooms = new Cabin[num];
        for (int i = 0; i < num; i++) {
            rooms[i] = new Cabin("Cabin " + i);
        }
    }

    public class Cabin extends Room {
        public Cabin(String name) {
            super(name);
        }

        @Override
        public void makesSound(Human human) {
            for (int i = 0; i < Transport.this.getRooms().length; i++) {
                for (int j = 0; j < Transport.this.rooms[i].getHumans().size(); j++) {
                    if (Math.random() > 0.75) {
                        Transport.this.rooms[i].getHumans().get(j).noticeNoise(human);
                    }
                }
            }
        }

        @Override
        public void humanLeave(Human human) {
            Room randomCabin = Transport.this.rooms[(int) Math.floor( Math.random() * Transport.this.rooms.length )];
            super.humanLeave(human);
            randomCabin.getHumanIn(human);
        }
    }

    @Override
    protected void enterNotify(Human human) {
        System.out.println(human.getName() + " enters the transport " + name);
    }
}