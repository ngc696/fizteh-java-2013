package ru.fizteh.fivt.students.adanilyak.commands;

import ru.fizteh.fivt.students.adanilyak.modernfilemap.FileMapState;
import ru.fizteh.fivt.students.adanilyak.storeable.StoreableDataBaseGlobalState;

import java.io.IOException;
import java.util.List;

/**
 * User: Alexander
 * Date: 21.10.13
 * Time: 0:27
 */
public class CmdExit implements Cmd {
    private final String name = "exit";
    private final int amArgs = 0;
    private StoreableDataBaseGlobalState workState;

    public CmdExit(StoreableDataBaseGlobalState stateTable) {
        workState = stateTable;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAmArgs() {
        return amArgs;
    }

    @Override
    public void work(List<String> args) throws IOException {
        boolean autoCommitOnExit = false;
        if (autoCommitOnExit) {
            if (workState.currentTable != null) {
                workState.currentTable.commit();
            }
        }
        System.exit(0);
    }
}
