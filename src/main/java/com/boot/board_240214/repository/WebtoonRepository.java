package com.boot.board_240214.repository;

import com.boot.board_240214.model.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
}
