package com.gdu.app05.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app05.domain.BoardDTO;
import com.gdu.app05.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list.do")
	public String list(Model model){
		
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/list"; 
	}
	
	
	@PostMapping("/add.do")
	public String write(BoardDTO board) {
		
		boardService.addBoard(board);
		return "redirect:/board/list.do"; // 삽입, 수정, 삭제 후엔 리다이렉트 이동.
	}
	
	
	@GetMapping("/write.do")
	public String write() {
		return "board/write"; 
	}
}
