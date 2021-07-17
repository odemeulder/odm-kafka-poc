package us.odm.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import us.odm.producer.Subscription;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	@Bean
	public Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-service:9092");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return props;
	}

	private JsonDeserializer deserializer() {
		JsonDeserializer deserializer = new JsonDeserializer<Subscription>();
		deserializer.addTrustedPackages("us.odm.producer");
		return deserializer;
	}

	@Bean
	public KafkaMessageListenerContainer<Integer, Subscription> messageListenerContainer() {
		ContainerProperties containerProperties = new ContainerProperties("sub-events");
		containerProperties.setMessageListener(new SubscriptionListener());
		containerProperties.setGroupId("SubsConsumer2");

		DefaultKafkaConsumerFactory<Integer, Subscription> cf =
				new DefaultKafkaConsumerFactory<Integer, Subscription>(
						consumerProps(),
						IntegerDeserializer::new,
						() -> deserializer());
		return new KafkaMessageListenerContainer<>(cf, containerProperties);
	}


}
