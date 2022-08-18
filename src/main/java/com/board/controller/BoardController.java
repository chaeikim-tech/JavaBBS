package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());
        return "list";
    }

    /* @GetMapping("/list")
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());

        return "list";
    } */

    /* @GetMapping("/write") //localhost:8080/write
    public String boardWriteForm() {
        return "write";
    } */

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

    @PostMapping("/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id, Board board)  {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/";

    }

    /* @GetMapping("/search")
    public String boardSearch(@RequestParam(value = "keyword")String keyword, Model model) {
        model.addAttribute("board", boardService.searchPost(keyword))
        return "list";
    } */
}