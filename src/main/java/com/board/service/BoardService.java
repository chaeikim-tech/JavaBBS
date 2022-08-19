package com.board.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.board.entity.Board;
import com.board.repository.BoardRepository;

import org.thymeleaf.util.StringUtils;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    // 게시글 리스트 처리
    /* public List<Board> boardList() {

        return boardRepository.findAll();
    } */
    public Page<Board> boardList(Pageable pageable,String searchText) {

        if(StringUtils.isEmpty(searchText)){
            return boardRepository.findAll(pageable);
        } else {
            return boardRepository.findByTitleContaining(pageable, searchText);
        }
    }

    // 글 작성 처리
    public void write(Board board){
        boardRepository.save(board);
    }

    

    // 특정 게시글 삭제

    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }


    // 특정 게시글 불러오기
    public Board boardView(Long id) {
        //BoardRepository.updateView(id); //조회수
        return boardRepository.findById(id).get();
    }


   /*  @Transactional
    public List<Board> searchPost(String keyword) {
        List<Board> boardList = new ArrayList<>();

        if(boardEntities.isEmpty()) return boardList;

        for(Board boardEntity : boardEntities) {
            boardList.add(this.convertEntityToDto(boardEntity));
        }

        return boardList;
    }

    private Board convertEntityToDto(Board boardEntity) {
        return Board.builder()
                .id(boardEntity.getId())
                .name(boardEntity.getName())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .build();
    } */
}