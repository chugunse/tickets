package stm.kafka;

import dto.TicketSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String TOPIC = "tickets_topic";
    private final KafkaTemplate<String, TicketSaveDto> kafkaTemplate;


    public void send(TicketSaveDto dto) {
        kafkaTemplate.send(TOPIC, dto);
    }
}
