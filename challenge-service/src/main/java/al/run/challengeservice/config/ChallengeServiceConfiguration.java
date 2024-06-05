package al.run.challengeservice.config;

import al.run.challengeservice.business.events.RunSavedEvent;
import al.run.challengeservice.business.events.UserChallengeUpdated;
import al.run.challengeservice.business.events.consumer.RunSavedEventListener;
import al.run.challengeservice.business.events.producer.UserChallengeUpdatedSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ChallengeServiceConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(df);
        return objectMapper;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServersUrl;

    @Value("${spring.kafka.group-id}")
    private String groupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "event:al.run.challengeservice.business.events.RunSavedEvent");
        return props;
    }


    @Bean
    public ConsumerFactory<String, RunSavedEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public RunSavedEventListener runSavedEventListener() {
        return new RunSavedEventListener();
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, RunSavedEvent>> listenerContainerFactory(
            ConsumerFactory<String, RunSavedEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, RunSavedEvent> listenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(consumerFactory);
        return listenerContainerFactory;
    }

//    @Bean
//    public Map<String, Object> consumerConfigsChallenge() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TYPE_MAPPINGS, "event:al.run.notificationservicemodule.events.UserChallengeUpdated");
//        return props;
//    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "event:al.run.challengeservice.business.events.UserChallengeUpdated");
        return props;
    }

    @Bean
    public ProducerFactory<String, UserChallengeUpdated> producerFactory1() {
        return new DefaultKafkaProducerFactory<String, UserChallengeUpdated>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, UserChallengeUpdated> kafkaTemplate1() {
        return new KafkaTemplate<>(producerFactory1());
    }

    @Bean
    public UserChallengeUpdatedSender userChallengeUpdatedSender() {
        return new UserChallengeUpdatedSender();
    }
}
