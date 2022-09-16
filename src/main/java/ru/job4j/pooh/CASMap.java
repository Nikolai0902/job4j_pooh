package ru.job4j.pooh;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Используемые многопоточные коллекции.
 * 1. add if empty
 * 2. put
 * 3. extract
 */
public class CASMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
        String name = "weather";
        queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        queue.get(name).add("value");
        var text = queue.get(name).poll();
    }
}