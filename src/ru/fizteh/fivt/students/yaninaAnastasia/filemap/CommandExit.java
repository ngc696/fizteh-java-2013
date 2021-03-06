package ru.fizteh.fivt.students.yaninaAnastasia.filemap;

import ru.fizteh.fivt.students.yaninaAnastasia.shell.Command;

import java.io.IOException;

public class CommandExit extends Command {
    public boolean exec(String[] args, State curState) throws IOException {
        MultiDBState myState = MultiDBState.class.cast(curState);
        if (myState.table == null) {
            System.exit(0);
        }
        if (args.length != 0) {
            System.err.println("Invalid arguments");
            return false;
        }
        if (MultiFileMapUtils.save(myState)) {
            return true;
        } else {
            System.err.println("File has not been saved");
            return false;
        }
    }

    public String getCmd() {
        return "exit";
    }
}
