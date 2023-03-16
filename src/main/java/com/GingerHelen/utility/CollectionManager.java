package com.GingerHelen.utility;

import com.GingerHelen.data.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String collectionInfo() {
        return "Collection type: " + collection.getClass().getName() + "\n"
                + "Date of initialization: " + dateOfInitialization + "\n"
                + "Collection size: " + collection.size();
    }

    public TreeMap<Long, Flat> getCollection() {
        return collection;
    }

    public void addToCollection(Long key, Flat flat) {
        collection.put(key, flat);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        if (collection.size() > 0) {
            collection.forEach((k, v) -> stringJoiner.add(k + ": " + v.toString()));
        } else {
            stringJoiner.add("The collection is empty");
        }
        return stringJoiner.toString();
    }


    public Flat getById(Integer id) {
        return collection.values().stream().filter(v -> v.getId().equals(id)).findFirst().get();
    }


    public boolean containsId(Integer id) {
        return collection.values().stream().anyMatch(v -> v.getId().equals(id));
    }

    public boolean containsKey(Long key) {
        return collection.containsKey(key);
    }


    public Flat getByKey(Long key) {
        return collection.get(key);
    }


    public void remove(Long key) {
        collection.remove(key);
    }


    public void clear() {
        collection.clear();
    }


    public void removeLower(Flat flat) {
        collection.entrySet().removeIf(e -> e.getValue().compareTo(flat) < 0);
    }


    public void removeGreaterKey(Long key) {
        collection.entrySet().removeIf(e -> e.getKey() > key);
    }


    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(Parser.serialize(collection).getBytes());
        fos.close();
    }

    public Flat minByHouse() {
        return collection.values().stream().min(Comparator.comparing(Flat::getHouse)).get();
    }

    public Map<Transport, Long> groupCountingByTransport() {
        return collection.values().stream().filter(e -> e.getTransport() != null).collect(Collectors.groupingBy(Flat::getTransport, Collectors.counting()));
    }

    public LinkedHashMap<Long, Flat> printAscending() {
        return collection.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public int getMaxId() {
        if (collection.size() > 0) {
            return collection.values().stream().max(Comparator.comparing(Flat::getId)).get().getId();
        } else {
            return 0;
        }
    }

    public void update(Integer id, Flat newValue) {
        collection.entrySet().stream().filter(e -> e.getValue().getId().equals(id)).findFirst().get().setValue(newValue);
    }
}
