package com.boot.board_240214.service;

import com.boot.board_240214.model.Webtoon;
import com.boot.board_240214.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebtoonService {
    private final WebtoonRepository webtoonRepository;

    @Autowired
    public WebtoonService(WebtoonRepository webtoonRepository) {
        this.webtoonRepository = webtoonRepository;
    }

    public List<Webtoon> getAllWebtoons() {

        return webtoonRepository.findAll();
    }
    public Webtoon getWebtoonById(Long id) {
        return webtoonRepository.findById(id).orElse(null);
    }
}
