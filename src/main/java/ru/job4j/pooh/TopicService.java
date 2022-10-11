package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Реализация режима Topic.
 *
 * Отправитель посылает запрос на добавление данных с указанием топика (weather) и значением параметра (temperature=18).
 * Сообщение помещается в конец каждой индивидуальной очереди получателей.
 * Если топика нет в сервисе, то данные игнорируются.
 * Получатель посылает запрос на получение данных с указанием топика. Если топик отсутствует, то создается новый.
 * А если топик присутствует, то сообщение забирается из начала индивидуальной очереди получателя и удаляется.
 * Когда получатель впервые получает данные из топика – для него создается индивидуальная пустая очередь.
 * Все последующие сообщения от отправителей с данными для этого топика помещаются в эту очередь тоже.
 */
public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String status = "501";
        String text = "";
        if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            text = topics.get(req.getSourceName()).get(req.getParam()).poll();
            if (text == null) {
                text = "";
                status = "204";
            } else {
                status = "200";
            }
        } else if ("POST".equals(req.httpRequestType())) {
            var hashMap = topics.get(req.getSourceName());
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