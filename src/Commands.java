public enum Commands {
    HELP,
    EXIT,
    START,
    INFO,
    SHOW,
    LOAD,
    REMOVE_LAST,
    ADD,
    ADD_IF_MIN,
    REMOVE;

    Room data = null;

    public void setData(Room data){
        this.data = data;
    }

    public Room getData() {
        return data;
    }
}
