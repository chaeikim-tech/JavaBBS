package com.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.board.entity.Board;


public interface BoardRepository extends JpaRepository <Board, Long> {
    //void findByBoardEntity(Long id);
    //Page<Board> findByTitleContaining(Pageable pageable, String searchText);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}