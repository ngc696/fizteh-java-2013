package ru.fizteh.fivt.students.yaninaAnastasia.filemap;

import ru.fizteh.fivt.students.yaninaAnastasia.shell.Command;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CommandCreate extends Command {
    public boolean exec(String[] args, State curState) throws IOException {
        MultiDBState myState = MultiDBState.class.cast(curState);
        if (args.length != 1) {
            System.err.println("Invalid arguments");
            return false;
        }
        String path = myState.getProperty(myState);
        if (myState.myDatabase.database.containsKey(args[0])) {
            System.out.println(args[0] + " exists");
            System.getProperty("line.separator");
            return false;
        }
        File temp = new File(path, args[0]);
        if (!temp.exists()) {
            temp.mkdir();
        } else {
            System.out.println(args[0] + " exists");
            return false;
        }
        myState.myDatabase.database.put(args[0], new HashMap<String, String>());
        System.out.println("created");
        return true;
    }

    public String getCmd() {
        return "create";
    }
}
