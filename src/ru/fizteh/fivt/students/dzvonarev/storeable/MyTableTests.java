package ru.fizteh.fivt.students.dzvonarev.storeable;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.storage.structured.TableProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyTableTests {

    private Table table;
    private TableProvider provider;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void test() throws IOException {
        MyTableProviderFactory factory = new MyTableProviderFactory();
        provider = factory.create(folder.newFolder().getCanonicalPath());
        List<Class<?>> cl = new ArrayList<>();
        cl.add(Integer.class);
        table = provider.createTable("testTable", cl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValue() {
        table.put("key", null);
    }


}
