package al.run.notificationservicemodule.config;

import al.run.notificationservicemodule.events.OrderPlacedEvent;
import al.run.notificationservicemodule.events.UserChallengeUpdated;
import al.run.notificationservicemodule.receivers.ChallengeUpdateReceiver;
import al.run.notificationservicemodule.receivers.OrderPlacedEventReceiver;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class NotificationServiceConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServersUrl;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "event:al.run.notificationservicemodule.events.OrderPlacedEvent");
        return props;
    }


    @Bean
    public ConsumerFactory<String, OrderPlacedEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public OrderPlacedEventReceiver orderPlacedEventReceiver() {
        return new OrderPlacedEventReceiver();
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderPlacedEvent>> listenerContainerFactory(
            ConsumerFactory<String, OrderPlacedEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> listenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(consumerFactory);
        return listenerContainerFactory;
    }

    @Bean
    public Map<String, Object> consumerConfigsChallenge() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "event:al.run.notificationservicemodule.events.UserChallengeUpdated");
        return props;
    }


    @Bean
    public ConsumerFactory<String, UserChallengeUpdated> consumerFactoryChallenge() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigsChallenge());
    }

    @Bean
    public ChallengeUpdateReceiver challengeUpdateReceiver() {
        return new ChallengeUpdateReceiver();
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserChallengeUpdated>> listenerContainerFactoryChallenge(
            ConsumerFactory<String, UserChallengeUpdated> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserChallengeUpdated> listenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(consumerFactory);
        return listenerContainerFactory;
    }
}
