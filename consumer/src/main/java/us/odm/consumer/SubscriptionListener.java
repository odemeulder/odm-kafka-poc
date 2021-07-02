package us.odm.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import us.odm.producer.Subscription;

public class SubscriptionListener implements MessageListener<Integer, Subscription> {
    @Override
    public void onMessage(ConsumerRecord<Integer, Subscription> data) {
        System.out.println(data.toString());
    }
}
