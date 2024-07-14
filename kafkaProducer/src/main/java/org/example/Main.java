package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Slf4j
@RequiredArgsConstructor
public class Main {
    public static void main(String[] args) throws InterruptedException {

        EventGenerator eventGenerator = new EventGenerator();

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9093,localhost:9094");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 10; i++) {
            System.out.println("Generating event #"+ i);

            Event event = eventGenerator.generateEvent();
            String key = extractKey(event);
            String value = extractValue(event);

            ProducerRecord<String, String> record = new ProducerRecord<>("user-tracking", key, value);

            System.out.println("Producing kafka record : " + key+ " : " +value);
            producer.send(record);
            Thread.sleep(1000);
        }
        }

    private static String extractValue(Event event) {
        return  String.format("%s,%s", String.valueOf(event.getEventId()), event.getEventName());
    }

    private static String extractKey(Event event) {
        return event.getEventId().toString();
    }
}