package ru.fizteh.fivt.students.vlmazlov.multifilemap;

import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.text.ParseException;
import ru.fizteh.fivt.students.vlmazlov.shell.FileUtils;
import ru.fizteh.fivt.students.vlmazlov.filemap.GenericTable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;

public abstract class GenericTableProvider<V, T extends GenericTable<V>> {
    private Map<String, T> tables;
    private final String root;
    private ReadWriteLock providerLock;
    protected final boolean autoCommit;

	public GenericTableProvider(String root, boolean autoCommit) throws ValidityCheckFailedException {
        if (root == null) {
            throw new IllegalArgumentException("Directory not specified");
        }
        
		ValidityChecker.checkMultiTableDataBaseRoot(root);
		
        this.root = root;
        tables = new HashMap<String, T>();
        this.autoCommit = autoCommit;
        providerLock = new ReentrantReadWriteLock();
	}
	
    protected abstract T instantiateTable(String name, Object[] args);

    public T getTable(String name) {
        try {
            ValidityChecker.checkMultiTableName(name);
        } catch (ValidityCheckFailedException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        providerLock.readLock().lock();

        try {
        	return tables.get(name);
        } finally {
            providerLock.readLock().unlock();
        }
    }

    public T createTable(String name, Object[] args) {
    	try {
            ValidityChecker.checkMultiTableName(name);
        } catch (ValidityCheckFailedException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        providerLock.writeLock().lock();
        T newTable = null;

        try { 
        	if (tables.get(name) != null) {
                return null;
            }

            newTable = instantiateTable(name, args);

            tables.put(name, newTable);

            (new File(root, name)).mkdir();
        } finally {
            providerLock.writeLock().unlock();
        }

        return newTable;
    }

    public void removeTable(String name) {
    	try {
            ValidityChecker.checkMultiTableName(name);
        } catch (ValidityCheckFailedException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        providerLock.writeLock().lock();

        try {
        	T oldTable = tables.remove(name);

            if (oldTable == null) {
                throw new IllegalStateException("Table " + name + " doesn't exist");
            } 

            FileUtils.recursiveDelete(new File(root, name));
        } finally {
            providerLock.writeLock().unlock();
        }
    }

    public String getRoot() {
        return root;
    }

    public abstract void read() throws IOException, ValidityCheckFailedException;

    public abstract void write() throws IOException, ValidityCheckFailedException;

    abstract public V deserialize(T table, String value) throws ParseException; 
    
    abstract public String serialize(T table, V value);
}