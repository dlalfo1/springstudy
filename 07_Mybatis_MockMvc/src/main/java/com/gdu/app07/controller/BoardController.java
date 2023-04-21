package com.gdu.app07.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app07.service.BoardService;

@RequestMapping("/board")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	/*
		데이터(속성) 저장 방법
		1. forward	: Model에 attribute로 저장한다. (그러니 사용하려면 메소드의 매개변수에 Model model을 선언해줘야한다.)
		2. redirect : RedirectAttributes에 flashAttribute로 저장한다. (redirect이동시 애트리뷰트 저장하는 방법.)
	 
	*/
	// getBoardList 서비스가 반환한 List<BoardDTO>를 /WEB-INF/veiws/board/list.jsp로 전달한다.
	@GetMapping("/list.do")
	public String list(Model model){ // index.jsp에서 /list.do로 요청할 때 넘긴 파라미터가 없기 떄문에 받아올 필요도 없는거. request 생략하는 이유.
		 model.addAttribute("boardList", boardService.getBoardList());
		 return "board/list";
	}
	
	// 호랑이 시절 ModelAndView 클래스
	/*
		@GetMapping("list.do")
		public ModelAndView list() {
			ModelAndView mav = new ModelAndView();
			mav.addObject("boardList", boardService.getBoardList());
			mav.setViewName("board/list");
			
			return mav;
		}
	*/
	
	// getBoardByNo() 서비스가 반환한 BoardDTO를 /WEB-INF/veiws/board/detail.jsp로 전달한다.
	@GetMapping("/detail.do")
	public String detail(HttpServletRequest request, Model model) {
		
		model.addAttribute("board", boardService.getBoardByNo(request));
		return"board/detail";
	}
	
	@GetMapping("/write.do")
	public String write() {
		return "board/write";
	}

	// addBoard() 서비스가 반환한 0 또는 1을 가지고 /board/list.do으로 이동(redirect)한다.
	// list.do 매핑값을 받는 곳까지 가서 값을 가지고 또 board/list.jsp로 넘어간다.
	// 최종적으로 addBoard() 서비스가 반환한 0 또는 1은 /WEB-INF/veiws/board/list.jsp에서 확인한다.
	@PostMapping("/add.do")
	public String add(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("addResult", boardService.addBoard(request));
		return "redirect:/board/list.do";
		
	}
	
	// modifyBoard() 서비스가 반환한 0 또는 1을 가지고 /board/detail.do으로 이동(redirect)한다.
	// list.do 매핑값을 받는 곳까지 가서 값을 가지고 또 board/list.jsp로 넘어간다.
	// modifyBoard() 서비스가 반환한 0 또는 1은 /WEB-INF/veiws/board/detail.jsp에서 확인한다
	@PostMapping("/modify.do")
	public String modify(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("modifyResult", boardService.modifyBoard(request));
		return "redirect:/board/detail.do?boardNo=" + request.getParameter("boardNo");	// 상세보기 요청을 하고 싶으면 boardNo값을 파라미터로 보내줘야 한다.
	}
	
	@PostMapping("/remove.do")
	public String remove(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("removeResult", boardService.removeBoard(request));
		
		return "redirect:/board/list.do";
	}
	
	// 트랜잭션 테스트 
	@GetMapping("/tx.do")
	public void text() {
		boardService.testTx();
	}
	
	
	
	
	

}
