package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String status = "204";
        String text = "";
        if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            text = topics.get(req.getSourceName()).get(req.getParam()).poll();
            if (text == null) {
                text = "";
            } else {
                status = "200";
            }
        } else if ("POST".equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> hashMap = topics.get(req.getSourceName());
            if (hashMap != null) {
                for (ConcurrentLinkedQueue<String> linkedQueue : hashMap.values()) {
                    linkedQueue.add(req.getParam());
                }
                text = req.getParam();
                status = "200";
            }
        }
        return new Resp(text, status);
    }
}