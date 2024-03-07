package com.boot.board_240214.controller;

import com.boot.board_240214.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api")
public class BoardApiController {

    @Autowired
    private BoardRepository boardRepository;

    @DeleteMapping("/delete/{id}")
    void deleteBoard(@PathVariable Long id){
        log.info("@#@# delete");
        boardRepository.deleteById(id);
    }
}
