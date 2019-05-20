import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

public class CommandParser {

    private String input;

    public CommandParser(String input){
        this.input = input;
    }

    public Commands parse() throws EmptyInputException, JSONException, NoSuchElementException {
        String[] args = input.split(" ");
        if(args.length == 0)
            throw new EmptyInputException(input);
        else if(args.length == 1){
            if(args[0].trim().matches("help|exit|start|info|show|load|remove_last|deepshow")) {
                Commands command = Commands.valueOf(args[0].trim().toUpperCase());
                if (command == Commands.ADD_IF_MIN ||
                        command == Commands.ADD ||
                        command == Commands.REMOVE)
                    throw new NoSuchElementException();
                return command;
            }
        }
        else if(args.length == 2){
            if(args[0].trim().matches("add|add_if_min|remove")) {
                String jsonData = args[1].trim().substring(1, args[1].trim().length() - 1);
                JSONObject jsonObject = new JSONObject(jsonData);
                Room room = new Room(jsonObject);
                Commands command = Commands.valueOf(args[0].trim().toUpperCase());
                command.setData(room);
                return command;
            }
        }
        return null;
    }
}
