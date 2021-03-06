package ru.fizteh.fivt.students.irinaGoltsman.multifilehashmap;

import ru.fizteh.fivt.storage.strings.*;

import java.io.File;

public class DBTableProviderFactory implements TableProviderFactory {

    public TableProvider create(String rootDir) throws IllegalArgumentException {
        if (rootDir == null) {
            throw new IllegalArgumentException("Directory name: can not be null");
        }
        TableProvider newTableProvider = null;
        try {
            File file = new File(rootDir);
            newTableProvider = new DBTableProvider(file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return newTableProvider;
    }
}
