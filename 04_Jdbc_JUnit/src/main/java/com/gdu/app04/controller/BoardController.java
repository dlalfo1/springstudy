package com.gdu.app04.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app04.domain.BoardDTO;
import com.gdu.app04.service.BoardService;

@RequestMapping("/board")	// 모든 mapping에 /board가 prefix로 추가됩니다.
@Controller
public class BoardController {

	// 로그 확인해보는거 여기 아닌데 써버림,,, 걍 쓰자..
	// pom.xml 파일 Logback에 맞게 수정안돼서 로그 안뜸. 여기까지만 하자.
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list.do")
	public String list(Model model) {	// Model : jsp로 전달(forward)할 데이터(속성, attribute)를 저장한다.
		List<BoardDTO> list = boardService.getBoardList();
		LOGGER.debug(list.toString());		// 목록 결과 확인
		model.addAttribute("boardList", list);	// getBoardList() 메소드 호출 결과가 DAO가 selectBoardList() 메소드를 호출한 결과이다.
		return "/board/list";
	}
	
	@GetMapping("/write.do")
	public String write(Model model) {	
		
		return "/board/write";
	}
	
	// 파라미터 받는 법 세가지 :  HttpServletRequest, @RequestParam, BoadDTO board(커맨드객체)
	// 요청된 파라미터 : title, writer, content
	@PostMapping("/add.do")
	public String add(BoardDTO board) {    // 세가지 파라미터를 받을 수 있는 BoardDTO 객체 
		LOGGER.debug(board.toString());    // 파라미터 확인
		LOGGER.debug(boardService.addBoard(board) + "");	// 결과 확인    
	
										  // addBoard() 메소드의 호출 결과인 int 값(0 또는 1)은 사용하지 않았다.
		return "redirect:/board/list.do"; // 삽입 후엔 목록 보기로 redirect 이동한다.
										  // redirec 이동시엔 절대 jsp 파일의 이름을 적지 말고 mapping값을 적어준다.
	}
	
	@GetMapping("/detail.do") // location 이동이니 GET방식이다.
	public String detail(@RequestParam(value="board_no", required=false, defaultValue="0") int board_no // board_no가 null이라면 0값을 준다.
						
						// 여기선 @RequestParam 사용해서 파라미터 받는게 편한 이유가 null처리를 속성사용해서 간편하게 할 수 있기 때문이다.
						// 요청된 파라미터가 하나뿐인데 커맨드객체 쓸 필요는 없기도 하고,,
						, Model model ) {
		LOGGER.debug(board_no +""); // 파라미터 확인
		BoardDTO b = boardService.getBoardByNo(board_no);
		LOGGER.debug(b.toString());	// 상세 결과 확인
		model.addAttribute("b", b);												
		return "board/detail";
	}
	
	@GetMapping("/remove.do")
	public String remove(@RequestParam(value="board_no", required=false, defaultValue="0") int board_no) {
		
		LOGGER.debug(board_no +""); // 파라미터 확인
		LOGGER.debug(boardService.removeBoard(board_no) + "");
		
		return "redirect:/board/list.do";	 // 결과 확인
	}
	
	@PostMapping("/modify.do")
	public String modify(BoardDTO board) {
		
		LOGGER.debug(board.toString());	// 파라미터 확인
		LOGGER.debug(boardService.modifyBoard(board) + "");	 // 결과 확인
		
		return "redirect:/board/detail.do?board_no=" + board.getBoard_no();
		// 수정을 끝낸후엔 수정된 글을 봐야하니 상세보기로 다시 이동하는 것이다.
		
	}
	 
	
	
	
	
	
	
	
	
	
	
}
