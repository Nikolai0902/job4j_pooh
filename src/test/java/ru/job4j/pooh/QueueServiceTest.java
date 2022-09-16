package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QueueServiceTest {
    /**
     * 1.Добавляем данные в очередь weather. Режим queue
     * 2.Забираем данные из очереди weather. Режим queue
     */
    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";

        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );

        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

    @Test
    public void whenPostThenGetQueueTwo() {
        QueueService queueService = new QueueService();
        String paramForPostMethodOne = "temperature=18";
        String paramForPostMethodTwo = "temperature=19";

        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethodOne)
        );
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethodTwo)
        );

        queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp resultTwo = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(resultTwo.text(), is("temperature=19"));
    }
}