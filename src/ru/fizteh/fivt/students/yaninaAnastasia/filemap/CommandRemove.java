package ru.fizteh.fivt.students.yaninaAnastasia.filemap;

import ru.fizteh.fivt.students.yaninaAnastasia.shell.Command;

import java.io.IOException;

public class CommandRemove extends Command {
    public boolean exec(String[] args, State curState) throws IOException {
        DBState myState = DBState.class.cast(curState);
        if (myState.table == null) {
            System.out.println("no table");
            return false;
        }
        if (args.length != 1) {
            System.err.println("Invalid arguments");
            return false;
        }
        if (myState.table.containsKey(args[0])) {
            myState.table.remove(args[0]);
            System.out.println("removed");
        } else {
            System.out.println("not found");
        }
        return true;
    }

    public String getCmd() {
        return "remove";
    }
}
