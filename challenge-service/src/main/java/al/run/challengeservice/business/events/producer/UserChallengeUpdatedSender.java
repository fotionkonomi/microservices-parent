package al.run.challengeservice.business.events.producer;

import al.run.challengeservice.business.dto.UserChallengeDto;
import al.run.challengeservice.business.events.UserChallengeUpdated;
import al.run.challengeservice.persistence.entity.UserChallenge;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class UserChallengeUpdatedSender {

    @Autowired
    private KafkaTemplate<String, UserChallengeUpdated> kafkaTemplate;

    @Autowired
    private ModelMapper modelMapper;

    public void send(String kafkaTopic, UserChallenge userChallenge) {
        kafkaTemplate.send(kafkaTopic, new UserChallengeUpdated(modelMapper.map(userChallenge, UserChallengeDto.class)));
    }

}
