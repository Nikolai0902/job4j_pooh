package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String status = "";
        String text = "";
        if ("POST".equals(req.httpRequestType())) {
            ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<>();
            linkedQueue.add(req.getParam());
            if (queue.putIfAbsent(req.getSourceName(), linkedQueue) != null) {
                queue.get(req.getSourceName()).add(req.getParam());
            }
            status = "200";
            text = req.getParam();
        } else if ("GET".equals(req.httpRequestType())) {
            text = queue.get(req.getSourceName()).poll();
            status = "200";
            if (text == null) {
                status = "204";
            }
        }
            return new Resp(text, status);
    }
}
