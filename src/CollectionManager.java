
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

public class CollectionManager {

    private Stack<Room> stackCollection;
    private boolean isCollectionEmpty;
    private String filePath;
    private boolean isFileExists;
    public CollectionManager(String filePath){
        this.filePath = filePath;
        File file = new File(filePath);
        isFileExists = file.exists();
        stackCollection = new Stack<>();
        if (isFileExists){
            readCollection();
            isCollectionEmpty = false;
        }
        else{
            isCollectionEmpty = true;
            System.err.println("Ошибка: файл не существует!");
        }

    }

    public void saveCollection(Stack<Room> stackCollection){
        if(stackCollection.size() == 0)
            System.out.println("Не удалось сохранить коллекцию так как коллекция пуста(");
        JSONArray array = new JSONArray();
        stackCollection.forEach(p -> array.put(p.getJson()));
        if(isFileExists){
            try(PrintWriter printWriter = new PrintWriter(filePath)) {
                printWriter.print(CDL.toString(array));
            } catch (IOException e) {
                System.err.println("Произошло что-то ужасное при записи файла!");
            }
        }
        else{
            System.out.println("Файл создан!");
            try(PrintWriter printWriter = new PrintWriter(filePath)) {
                printWriter.print(CDL.toString(array));
            } catch (IOException e) {
                System.err.println("Произошло что-то ужасное при записи файла!");
            }
        }
    }

    public void updateCollection(){
        stackCollection.clear();
        readCollection();
    }

    public void readCollection(){
        if(isFileExists){
            Scanner reader = null;
            try {
                reader = new Scanner(new File(filePath));
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
            StringBuilder builder = new StringBuilder();
            while(reader.hasNextLine()){
                builder.append(reader.nextLine()).append("\n");
            }
            String str = builder.toString();
            if(str.length() != 0) {
                JSONArray array = CDL.toJSONArray(str);
                array.forEach(p ->
                        stackCollection.push(new Room((JSONObject) p)));
                isCollectionEmpty = false;
            }
            else{
                isCollectionEmpty = true;
                System.out.println("Файл с коллекцией пуст!");
            }
        }
    }

    public Stack<Room> getStackCollection() {
        return stackCollection;
    }

    public void runCommand(Commands command){
        switch (command){
            case HELP: help(); break;
            case SHOW: show(); break;
            case EXIT: exit(); break;
            case INFO: info(); break;
            case ADD: add(command.getData()); break;
            case REMOVE: remove(command.getData()); break;
            case ADD_IF_MIN: add_if_min(command.getData()); break;
            case LOAD: load(); break;
            case REMOVE_LAST: remove_last(); break;
            case DEEPSHOW: deepshow(); break;
            default:
                System.out.println("Unknown command!");
        }
    }

    public boolean isFileExists() {
        return isFileExists;
    }

    public String getFilePath() {
        return filePath;
    }

    private void show(){
        updateCollection();
        stackCollection.forEach(System.out::println);
    }

    private void help(){
        System.out.println("info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "load: перечитать коллекцию из файла\n" +
                "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove {element}: удалить элемент из коллекции по его значению\n" +
                "remove_last: удалить последний элемент из коллекции\n" +
                "help: помощь\n" +
                "start: начинает работу основной программы\n" +
                "exit: выходит из программы\n" +
                "deepshow: вывести в стандартный поток вывода все элементы коллекции в строковом представлении со всеми вложенностями \n" +
                "Пример json: \n" +
                "{\"name\":\"Kitchen\",\"interactables\":[{\"name\":\"Canvas\"},{\"name\":\"Flashlight\"},{\"name\":\"Cover\"}]}");
    }

    private void exit(){
        System.out.println("Shutdown");
        System.exit(0);
    }

    private void info(){
        updateCollection();
        System.out.println(
                "Тип коллекции: " + stackCollection.getClass().getSimpleName() + "\n" +
                        "Дата инициализации коллекции: " + new Date().toString() + "\n" +
                        "Тип элементов коллекции: " + "Room" + "\n" +
                        "Количество элементов коллекции: " + stackCollection.size()
        );
    }

    private void load(){
        updateCollection();
        System.out.println("Done!");
    }

    private void remove_last(){
        stackCollection.pop();
        saveCollection(stackCollection);
        System.out.println("Done!");
    }

    private void add(Room room){
        stackCollection.push(room);
        saveCollection(stackCollection);
        System.out.println("Done!");
    }

    private void add_if_min(Room room){
        Room min = Collections.min(stackCollection);

        if(room.compareTo(min) < 0){
            stackCollection.push(room);
            System.out.println("Element was added!");
        }
        else {
            System.out.println("Element wasn't added");
        }
        saveCollection(stackCollection);
        System.out.println("Done!");
    }

    private void remove(Room room){
        Stack<Room> tempStack = new Stack<>();
        while(stackCollection.size() != 0){
            if(!stackCollection.peek().equals(room)){
                tempStack.push(stackCollection.pop());
            }
            else{
                stackCollection.pop();
                break;
            }
        }
        while(tempStack.size() != 0)
            stackCollection.push(tempStack.pop());

        saveCollection(stackCollection);

        System.out.println("Done!");
    }

    private void deepshow(){
            System.out.printf("|%16s|%16s|\n", "Room", "Interactables");
        stackCollection.forEach(p -> {
            StringBuilder iters = new StringBuilder();
            System.out.println("___________________________________");
            p.getInteractables().forEach(j -> iters.append(String.format("|%16s|%16s|\n", p.getName(), j.getName())));
            System.out.println(iters.toString());
        });
    }
}