package solds.kafka;

import dto.TicketSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import solds.service.TicketSaveService;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final TicketSaveService service;

    @KafkaListener(topics = "tickets-topic", groupId = "group_id")
    public void lesten(TicketSaveDto dto) {
        log.info("сохранение билета =" + dto);
        service.addTicket(dto);
    }
}
