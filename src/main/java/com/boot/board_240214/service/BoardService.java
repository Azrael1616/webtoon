package com.boot.board_240214.service;

import com.boot.board_240214.model.Board;
import com.boot.board_240214.model.User;
import com.boot.board_240214.repository.BoardRepository;
import com.boot.board_240214.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);
        board.setUser(user);

        return boardRepository.save(board);
    }
}
