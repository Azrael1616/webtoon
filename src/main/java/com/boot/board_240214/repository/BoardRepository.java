package com.boot.board_240214.repository;

import com.boot.board_240214.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//ctrl+shift+enter: 마지막에 세미콜론
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Board> findByTitleContainingOrContentContainingAndWebtoonId(String title, String content, Long id, Pageable pageable);

    Page<Board> findByWebtoonId(Long wbid, Pageable pageable);
}