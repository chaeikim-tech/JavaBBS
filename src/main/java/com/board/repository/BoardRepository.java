package com.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.board.entity.Board;


public interface BoardRepository extends JpaRepository <Board, Long> {
    List<Board> findByTitleContaining(String keyword);
    //Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}