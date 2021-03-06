package ru.fizteh.fivt.students.sterzhanovVladislav.fileMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import ru.fizteh.fivt.students.sterzhanovVladislav.shell.Command;
import ru.fizteh.fivt.students.sterzhanovVladislav.shell.ShellUtility;

public class Wrapper {

    private static HashMap<String, Command> cmdMap = new HashMap<String, Command>();
    
    public static void main(String[] args) {
        String dbDir = System.getProperty("fizteh.db.dir");
        if (dbDir == null) {
            System.out.println("fizteh.db.dir not set");
            System.exit(-1);
        }
        Path dbPath = Paths.get(dbDir);
        try (DatabaseContext context = new DatabaseContext(dbPath)) {
            cmdMap.put("put", new FileMapCommands.Put().setContext(context));
            cmdMap.put("get", new FileMapCommands.Get().setContext(context));
            cmdMap.put("remove", new FileMapCommands.Remove().setContext(context));
            cmdMap.put("create", new FileMapCommands.Create().setContext(context));
            cmdMap.put("drop", new FileMapCommands.Drop().setContext(context));
            cmdMap.put("use", new FileMapCommands.Use().setContext(context));
            cmdMap.put("exit", new FileMapCommands.Exit().setContext(context));

            ShellUtility.execShell(args, cmdMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        System.exit(0);
    }
}
