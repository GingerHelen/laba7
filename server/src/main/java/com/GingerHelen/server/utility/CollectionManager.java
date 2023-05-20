package com.GingerHelen.server.utility;

import com.GingerHelen.common.data.Flat;
import com.GingerHelen.common.data.Transport;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * класс, хранящий коллекцию и предоставляющий доступ к данным, хранящимся в ней/модификации коллекции
 */
public class CollectionManager {
    private TreeMap<Long, Flat> collection = new TreeMap<>();
    private final Date dateOfInitialization = new Date();
    private String filePath;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final DatabaseManager databaseManager;

    public CollectionManager(TreeMap<Long, Flat> collection, DatabaseManager databaseManager) {
        this.collection = collection;
        this.databaseManager = databaseManager;
    }

    public CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    /**
     * @return информация о коллекции в строковом формате (Collection type, Date of initialization, Collection size)
     */
    public String collectionInfo() {
        return "Collection type: " + collection.getClass().getName() + "\n"
                + "Date of initialization: " + dateOfInitialization + "\n"
                + "Collection size: " + collection.size();
    }


    /**
     * добавить элемент в коллекцию
     *
     * @param key
     * @param flat
     */
    public boolean addToCollection(Long key, Flat flat) {
        Long id = databaseManager.insert(key, flat);
        if (id != null) {
            flat.setId(id);
            lock.writeLock().lock();
            try {
                collection.put(key, flat);
            } finally {
                lock.writeLock().unlock();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        try {
            StringJoiner stringJoiner = new StringJoiner("\n");
            if (collection.size() > 0) {
                collection.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getName()))
                        .forEach((e) -> stringJoiner.add(e.getKey() + ": " + e.getValue().toString()));
            } else {
                stringJoiner.add("The collection is empty");
            }
            return stringJoiner.toString();
        } finally {
            lock.readLock().unlock();
        }
    }


    /**
     * @return true - в коллекции содержится элемент с заданным id, false - нет
     */
    public boolean containsId(Long id) {
        lock.readLock().lock();
        try {
            return collection.values().stream().anyMatch(v -> v.getId().equals(id));
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * @return true - в коллекции содержится элемент с заданным key, false - нет
     */
    public boolean containsKey(Long key) {
        lock.readLock().lock();
        try {
        return collection.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }


    /**
     * удалить из коллекции элемент с заданным ключом
     */
    public boolean remove(Long key) {
        if (databaseManager.remove(key)) {
        lock.writeLock().lock();
        try {
            collection.remove(key);
        } finally {
            lock.writeLock().unlock();
        } return true;
    } else {
        return false;
        }
    }


    /**
     * удалить все элементы коллекции
     */
    public boolean clear(String username) {
        if (databaseManager.clear(username)) {
            lock.writeLock().lock();
            try {
                collection.clear();
            } finally {
                lock.writeLock().unlock();
            } return true;
        } else {
            return false;
        }
    }


    /**
     * удалить все элементы коллекции, меньше заданного
     */
    public long removeLower(Flat flat, String username) {
        AtomicLong undeletedItems = new AtomicLong();
        List<Long> keys = collection.entrySet().stream().filter(e -> e.getValue().compareTo(flat) < 0
                && e.getValue().getOwner().equals(username)).map(Map.Entry::getKey).collect(Collectors.toList());
        keys.forEach(k -> {
            if (databaseManager.remove(k)) {
                collection.remove(k);
            } else {
                undeletedItems.getAndIncrement();
            }
        });
        return undeletedItems.get();
    }


    /**
     * удалить все элементы коллекции с ключами, больше заданного
     */
    public boolean removeGreaterKey(String username, Long key) {
        if (databaseManager.removeGreater(username, key)) {
            lock.writeLock().lock();
            try {
                collection.entrySet().removeIf(e -> (e.getKey() > key)&&(e.getValue().getOwner().equals(username)));
            } finally {
                lock.writeLock().unlock();
            } return true;
        } else {
            return false;
        }
    }

    /**
     * @return рандомный элемент с минимальным значением поля House
     */
    public Flat minByHouse() {
        lock.readLock().lock();
        try {
            Optional<Flat> minOptional = collection.values().stream().min(Comparator.comparing(Flat::getHouse));
            return minOptional.orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * @return словарь (ключ - значения поля Transport, значения - количество элементов в коллекции с таким значением поля Transport)
     */
    public Map<Transport, Long> groupCountingByTransport() {
        lock.readLock().lock();
        try {
            return collection.values().stream().filter(e -> e.getTransport() != null).collect(Collectors.groupingBy(Flat::getTransport, Collectors.counting()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * @return упорядочивает элементы коллекции (сортировка по значениям)
     */
    public LinkedHashMap<Long, Flat> printAscending() {
        lock.readLock().lock();
        try {
            return collection.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,
                    Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        } finally {
            lock.readLock().unlock();
        }
    }


    /**
     * обновить элемент с заданным id
     */
    public boolean update(Long id, Flat newValue) {
        if (databaseManager.update(id, newValue)) {
            lock.writeLock().lock();
            try {
                newValue.setId(id);
                collection.entrySet().stream().filter(e -> e.getValue().getId().equals(id)).findFirst().ifPresent(e -> e.setValue(newValue));
            } finally {
                lock.writeLock().unlock();
            } return true;
        } else {
            return true;
        }
    }
}
