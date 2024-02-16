package stm.kafka;

import dto.TicketSaveDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String TOPIC = "tickets_topic";
    private final KafkaTemplate<String, TicketSaveDto> kafkaTemplate;


    public void send(TicketSaveDto dto) {
        kafkaTemplate.send(TOPIC, dto);
    }

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(TOPIC)
                .partitions(1)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(Duration.ofHours(1).toMillis()))
                .build();
    }
}
