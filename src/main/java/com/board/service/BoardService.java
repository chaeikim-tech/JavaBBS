package com.board.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.entity.Board;
import com.board.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 글 작성 처리
    public void write(Board board){
        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public List<Board> boardList() {

        return boardRepository.findAll();
    }

    // 특정 게시글 삭제

    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }


    // 특정 게시글 불러오기
    public Board boardView(Long id) {
        return boardRepository.findById(id).get();
    } 

    
}