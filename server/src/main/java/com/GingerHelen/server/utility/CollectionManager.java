package com.GingerHelen.server.utility;

import com.GingerHelen.common.data.Flat;
import com.GingerHelen.common.data.Transport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * класс, хранящий коллекцию и предоставляющий доступ к данным, хранящимся в ней/модификации коллекции
 */
public class CollectionManager {
    private TreeMap<Long, Flat> collection = new TreeMap<>();
    private final Date dateOfInitialization = new Date();
    private String filePath;

    public CollectionManager(TreeMap<Long, Flat> collection, String filePath) {
        this.collection = collection;
        this.filePath = filePath;
    }

    public CollectionManager() {
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
     * @param key
     * @param flat
     */
    public void addToCollection(Long key, Flat flat) {
        flat.setId(this.getMaxId() + 1);
        collection.put(key, flat);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        if (collection.size() > 0) {
            collection.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getName()))
                    .forEach((e) -> stringJoiner.add(e.getKey() + ": " + e.getValue().toString()));
        } else {
            stringJoiner.add("The collection is empty");
        }
        return stringJoiner.toString();
    }


    /**
     * @return true - в коллекции содержится элемент с заданным id, false - нет
     */
    public boolean containsId(Integer id) {
        return collection.values().stream().anyMatch(v -> v.getId().equals(id));
    }

    /**
     * @return true - в коллекции содержится элемент с заданным key, false - нет
     */
    public boolean containsKey(Long key) {
        return collection.containsKey(key);
    }


    /**
     * удалить из коллекции элемент с заданным ключом
     */
    public void remove(Long key) {
        collection.remove(key);
    }


    /**
     * удалить все элементы коллекции
     */
    public void clear() {
        collection.clear();
    }


    /**
     * удалить все элементы коллекции, меньше заданного
     */
    public void removeLower(Flat flat) {
        collection.entrySet().removeIf(e -> e.getValue().compareTo(flat) < 0);
    }


    /**
     * удалить все элементы коллекции с ключами, больше заданного
     */
    public void removeGreaterKey(Long key) {
        collection.entrySet().removeIf(e -> e.getKey() > key);
    }


    /**
     * сохранить коллекцию в файл с путем filePath
     * @throws IOException ошибка при записи или закрытии файла
     */
    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(Parser.serialize(collection).getBytes());
        fos.close();
    }

    /**
     * @return рандомный элемент с минимальным значением поля House
     */
    public Flat minByHouse() {
        return collection.values().stream().min(Comparator.comparing(Flat::getHouse)).get();
    }

    /**
     * @return словарь (ключ - значения поля Transport, значения - количество элементов в коллекции с таким значением поля Transport)
     */
    public Map<Transport, Long> groupCountingByTransport() {
        return collection.values().stream().filter(e -> e.getTransport() != null).collect(Collectors.groupingBy(Flat::getTransport, Collectors.counting()));
    }

    /**
     * @return упорядочивает элементы коллекции (сортировка по значениям)
     */
    public LinkedHashMap<Long, Flat> printAscending() {
        return collection.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    /**
     * @return максимальное значение id среди элементов коллекции
     */
    public int getMaxId() {
        if (collection.size() > 0) {
            return collection.values().stream().max(Comparator.comparing(Flat::getId)).get().getId();
        } else {
            return 0;
        }
    }

    /**
     * обновить элемент с заданным id
     */
    public void update(Integer id, Flat newValue) {
        newValue.setId(id);
        collection.entrySet().stream().filter(e -> e.getValue().getId().equals(id)).findFirst().get().setValue(newValue);
    }
}
