package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.board.entity.Board;
import com.board.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String boardList(Model model,@PageableDefault(size= 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Page<Board> list = boardService.boardList(pageable);

        int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
        int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
        
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("list", list);
        //model.addAttribute("list", boardService.boardList(pageable));
        return "list";



    }

    @GetMapping("/write")
    public ModelAndView boardWriteForm(ModelAndView model) {
        model.setViewName("write");
        return model;
    }

    @PostMapping("/writepro")
    public String boardWritePro(Board board) {

        boardService.write(board);
        return "redirect:/";
    }

    @GetMapping("/view") // localhost:8080/view?id=1
    public String boardView(Model model, Long id) {

        model.addAttribute("board", boardService.boardView(id));
        return "detail";
    }

    @GetMapping("/delete")
    public String boardDelete(Long id) {
        boardService.boardDelete(id);
        return "redirect:/";
    }

    @GetMapping("/modify/{id}")
    public String boardModify(@PathVariable("id") Long id, Model model, Board board) {

        model.addAttribute("board", boardService.boardView(id));
        return "modify";
    }

    @PostMapping("/modify/{id}")
    public String boardUpdate(@PathVariable("id") Long id, Board board)  {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/";

    }

    @GetMapping("/search")
    public String boardSearch(@RequestParam(value = "keyword")String keyword, Model model) {
        List<Board> boardList = boardService.searchPost(keyword);

        System.out.println(boardList);
        model.addAttribute("list", boardList);
        return "list";
    }
}