package us.odm.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class SubscriptionService {

    @Autowired
    KafkaTemplate<Integer, Subscription> template;

    public boolean createSub(Subscription sub) {
        ListenableFuture<SendResult<Integer, Subscription>> future =
                template.send("sub-events", sub.getId(), sub);
        KafkaSendCallback<Integer, Subscription> callback =
                new KafkaSendCallback<Integer, Subscription>() {
            @Override
            public void onFailure(KafkaProducerException ex) {

                System.out.println(ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Integer, Subscription> result) {
                System.out.println(result.getRecordMetadata().toString());
            }
        };
        future.addCallback(callback);

        return true;
    }
}
