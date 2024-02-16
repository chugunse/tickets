package solds.service.impl;

import dto.TicketSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import solds.model.TicketSaveMapper;
import solds.service.TicketSaveService;
import solds.srorage.TicketSaveRepository;

@Service
@RequiredArgsConstructor
public class TicketSaveServiceImpl implements TicketSaveService {
    private final TicketSaveRepository repository;
    private final TicketSaveMapper mapper;

    @Override
    public void addTicket(TicketSaveDto dto) {
        repository.save(mapper.toModel(dto));
    }
}
