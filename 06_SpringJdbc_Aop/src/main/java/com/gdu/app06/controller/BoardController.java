package com.gdu.app06.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.gdu.app06.domain.BoardDTO;
import com.gdu.app06.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired 	// BoardService 객체를 주입하기 위한 애너테이션
	private BoardService boardService; 
	
	// ParameterCheckAOP에 의해서 파라미터를 체크할 메소드의 이름은 모두 PramCheck로 끝난다.
	@GetMapping("/list.do")
	public String list(Model model) {
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/list";
	}
	
	@GetMapping("/write.do")
	public String write() {
		
		return "board/write";
	}
	
	@PostMapping("/add.do")	// 삽입 후엔 리다이렉트 이동한다.
	public String addParamCheck(BoardDTO board) {	// 요청 파라미터를 전부 받을 수 있는 객체 BoardDTO선언
		boardService.addBoardList(board);
		return "redirect:/board/list.do"; // 리다이렉트 이동시엔 반드시 매핑값으로 이동할 것.
	}
	
	@GetMapping("/detail.do")	// board_no 파라미터가 없을시 null값을 처리하기 위해서 사용하는 @RequestParam 애너테이션
	public String detailParamCheck(@RequestParam(value="board_no", required=false, defaultValue="0") int board_no, Model model) {
		
		model.addAttribute("board", boardService.getBoardByNo(board_no));
		
		return "board/detail";
	}
	
	
	@GetMapping("/remove.do")
	public String removeParamCheck(@RequestParam(value="board_no", required=false, defaultValue="0") int board_no) {
		
		boardService.removeBoard(board_no);
		return "redirect:/board/list.do"; // 삭제 후엔 리다이렉트 이동한다.
	}
	
	@PostMapping("/modify.do")
	public String modify(BoardDTO board) {
		
		boardService.modifyBoard(board);
		return "redirect:/board/detail.do?board_no=" + board.getBoard_no();
	}
	
	// 트랜잭션 처리 확인을 위한 textTx() 메소드 호출하기
	@GetMapping("/tx.do")	// http://localhost:9090/app06/board/tx.do
	public String tx() {
		boardService.testTx();
		return "redirect:/board/list.do";
	}
	

}
