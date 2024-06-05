package al.run.notificationservicemodule.services;

import al.run.notificationservicemodule.dto.CriteriaType;
import al.run.notificationservicemodule.dto.UserChallengeStatus;
import al.run.notificationservicemodule.events.OrderPlacedEvent;
import al.run.notificationservicemodule.events.UserChallengeUpdated;
import al.run.notificationservicemodule.services.dto.Gender;
import al.run.notificationservicemodule.services.dto.OrderDto;
import al.run.notificationservicemodule.services.dto.RaceDto;
import al.run.notificationservicemodule.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RestTemplate restTemplate;

    private final JavaMailSender emailSender;

    @Override
    public void sendMail(OrderPlacedEvent orderPlacedEvent) {
        String subject = "Order Confirmation";
        String receiverMail = "";
        String body = "";
        StringBuilder bodyBuilder = new StringBuilder();

        ResponseEntity<UserDto> userDtoResponseEntity = restTemplate.getForEntity("http://user-service/api/user/" + orderPlacedEvent.getUserId(), UserDto.class);
        if (userDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            UserDto userDto = userDtoResponseEntity.getBody();
            receiverMail = userDto.getEmail();
            bodyBuilder.append("Hello ");
            bodyBuilder.append(getContractionBasedOnGender(userDto.getGender()));
            bodyBuilder.append(userDto.getFirstName() + " " + userDto.getLastName());
            bodyBuilder.append("\nYour order has been processed successfully!\nThese are the items you purchased: \n");

        }

        ResponseEntity<OrderDto> orderDtoResponseEntity = restTemplate.getForEntity("http://order-service/api/order/" + orderPlacedEvent.getOrderId(), OrderDto.class);
        if (orderDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            OrderDto orderDto = orderDtoResponseEntity.getBody();
            orderDto.getOrderLineItems().forEach(orderLineItemDto -> {
                ResponseEntity<RaceDto> raceDtoResponseEntity = restTemplate.getForEntity("http://race-service/api/race/" + orderLineItemDto.getRaceId(), RaceDto.class);
                if (raceDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
                    RaceDto raceDto = raceDtoResponseEntity.getBody();
                    bodyBuilder.append("\nRace: " + raceDto.getName());
                    bodyBuilder.append("\nLocation: " + raceDto.getLocation());
                    bodyBuilder.append("\nThis race has a distance of " + raceDto.getDistance() + " kilometers");
                    bodyBuilder.append("\nThis race costed: $" + raceDto.getPrice());
                }
                bodyBuilder.append("\nYou got " + orderLineItemDto.getQuantity() + " bibs for this race\n");
            });
        }

        bodyBuilder.append("\n\nThank you for choosing us!");
        body = bodyBuilder.toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("konomiservice@gmail.com");
        message.setTo(receiverMail);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    @Override
    public void sendMail(UserChallengeUpdated userChallengeUpdated) {
        String subject = "";
        String receiverMail = "";
        String body = "";
        StringBuilder bodyBuilder = new StringBuilder();

        if (UserChallengeStatus.COMPLETED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
            subject = "Challenge completed successfully";
        } else if (UserChallengeStatus.FAILED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
            subject = "Challenge failed!";
        } else if (UserChallengeStatus.IN_PROGRESS.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
            subject = "Challenge update!";
        } else {
            subject = "Upcoming challenge for you!";
        }

        ResponseEntity<UserDto> userDtoResponseEntity = restTemplate.getForEntity("http://user-service/api/user/" + userChallengeUpdated.getUserChallengeDto().getUserId(), UserDto.class);
        if (userDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            UserDto userDto = userDtoResponseEntity.getBody();
            receiverMail = userDto.getEmail();
            bodyBuilder.append("Hello ");
            bodyBuilder.append(getContractionBasedOnGender(userDto.getGender()));
            bodyBuilder.append(userDto.getFirstName() + " " + userDto.getLastName());
            if (UserChallengeStatus.COMPLETED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                bodyBuilder.append("\nYou have successfully completed the " + userChallengeUpdated.getUserChallengeDto().getChallenge().getName() + " challenge! \n");
            } else if (UserChallengeStatus.FAILED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                bodyBuilder.append("\nUnfortunately, You have failed to complete the " + userChallengeUpdated.getUserChallengeDto().getChallenge().getName() + " challenge! \n");
            } else if (UserChallengeStatus.IN_PROGRESS.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                bodyBuilder.append("\nYou have an update for the " + userChallengeUpdated.getUserChallengeDto().getChallenge().getName() + " challenge! \n");
            } else {
                bodyBuilder.append("\nYou have are successfully registered for the " + userChallengeUpdated.getUserChallengeDto().getChallenge().getName() + " challenge! \n");
            }
            bodyBuilder.append("\nHere are more details for you: \n");
            if (UserChallengeStatus.FAILED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                bodyBuilder.append("\nThe challenge should have been completed from " + userChallengeUpdated.getUserChallengeDto().getChallenge().getStartDate() + " until " + userChallengeUpdated.getUserChallengeDto().getChallenge().getEndDate() + "\n");
            } else {
                bodyBuilder.append("\nThe challenge should be completed from " + userChallengeUpdated.getUserChallengeDto().getChallenge().getStartDate() + " until " + userChallengeUpdated.getUserChallengeDto().getChallenge().getEndDate() + "\n");
            }
            bodyBuilder.append("\nChallenge criterias:\n");
            userChallengeUpdated.getUserChallengeDto().getCriteriaProgress().forEach(criteriaProgressDto -> {
                if (CriteriaType.DISTANCE.equals(criteriaProgressDto.getCriteria().getType())) {
                    if (UserChallengeStatus.FAILED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou should have completed " + criteriaProgressDto.getCriteria().getQuantity() + " kilometers.\n");
                    } else {
                        bodyBuilder.append("\nYou should complete " + criteriaProgressDto.getCriteria().getQuantity() + " kilometers.\n");
                    }
                    if (!UserChallengeStatus.REGISTERED_NOT_STARTED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou have completed " + criteriaProgressDto.getQuantity() + " kilometers. That is " + criteriaProgressDto.getProgressValue() + "% of the requirement\n");
                    }
                }
                if (CriteriaType.TIME.equals(criteriaProgressDto.getCriteria().getType())) {
                    if (UserChallengeStatus.FAILED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou should have completed " + criteriaProgressDto.getCriteria().getQuantity() + " minutes.\n");
                    } else {
                        bodyBuilder.append("\nYou should complete " + criteriaProgressDto.getCriteria().getQuantity() + " minutes.\n");
                    }
                    if (!UserChallengeStatus.REGISTERED_NOT_STARTED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou have completed " + criteriaProgressDto.getQuantity() + " minutes. That is " + criteriaProgressDto.getProgressValue() + "% of the requirement\n");
                    }
                }
                if (CriteriaType.FREQUENCY.equals(criteriaProgressDto.getCriteria().getType())) {
                    if (UserChallengeStatus.FAILED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou should have run " + criteriaProgressDto.getCriteria().getQuantity() + " times.\n");
                    } else {
                        bodyBuilder.append("\nYou have run " + criteriaProgressDto.getCriteria().getQuantity() + " times.\n");
                    }
                    if (!UserChallengeStatus.REGISTERED_NOT_STARTED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou have run " + criteriaProgressDto.getQuantity() + " times. That is " + criteriaProgressDto.getProgressValue() + "% of the requirement\n");
                    }
                }
                if (CriteriaType.ELEVATION.equals(criteriaProgressDto.getCriteria().getType())) {
                    if (UserChallengeStatus.FAILED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou should have ascended " + criteriaProgressDto.getCriteria().getQuantity() + " kilometers.\n");
                    } else {
                        bodyBuilder.append("\nYou should ascend " + criteriaProgressDto.getCriteria().getQuantity() + " kilometers.\n");
                    }
                    if (!UserChallengeStatus.REGISTERED_NOT_STARTED.equals(userChallengeUpdated.getUserChallengeDto().getStatus())) {
                        bodyBuilder.append("\nYou have ascended " + criteriaProgressDto.getQuantity() + " kilometers. That is " + criteriaProgressDto.getProgressValue() + "% of the requirement\n");
                    }
                }
            });
        }

        bodyBuilder.append("\n\nAspire to run, run to inspire!");
        body = bodyBuilder.toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("konomiservice@gmail.com");
        message.setTo(receiverMail);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);

    }

    public String getContractionBasedOnGender(Gender gender) {
        return switch (gender) {
            case MALE -> "Mr. ";
            case FEMALE -> "Mrs. ";
            default -> "";
        };
    }

}
