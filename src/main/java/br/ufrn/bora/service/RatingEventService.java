package br.ufrn.bora.service;

import br.ufrn.bora.domain.RatingEvent;
import br.ufrn.bora.repository.RatingEventRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RatingEventService {

    //TODO: add Logger

    RatingEventRepository ratingEventRepository;

    public RatingEventService(RatingEventRepository ratingEventRepository) {
        this.ratingEventRepository = ratingEventRepository;
    }

    public RatingEvent save(RatingEvent ratingEvent) {
        return ratingEventRepository.save(ratingEvent);
    }

    public List<RatingEvent> findAll() {
        return ratingEventRepository.findAll();
    }
    //TODO: add methods to get RatingEvent by event id and user id
}
