package com.gdu.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.myapp.domain.BoardDTO;
import com.gdu.myapp.service.BoardService;

@Controller
public class BoardController2 {

	// 컨트롤러는 서비스를 사용한다.
	// private BoardService boardService = new BoardServiceImpl(); 실무에서는 가능하다.
	
	// Spring Container에 BoardService 타입의 Bean을 만들어 둔 다음에 가져다 사용한다.
	
	/*
		Spring Container에 BoardService 타입의 Bean 만들기 (Bean 만드는 3가지 방법)
		1. root-context.xml 	 : <bean id="boardService" class="com.gdu.myapp.service.BoardServiceImpl" />
		2. AppConfig.java   	 : @Configuration public AppConfig { @Bean public BoardService boardService() { return new BoardServiceImpl(); } }
		3. BoardServiceImpl.java : @Service public class BoardServiceImpl { }
	*/
	
	/*
		Spring Container에서 BoardService 타입의 Bean 가져오기 (Bean 가져오는 3가지 방법)
		1. 필드에 직접 @Autowired 추가하기
				// @Autowired
				// private BoardService boardService;
				 * 
		2. 생성자(필드를 이용한 생성자) 만들기
		   //  private BoardService boardService;
		    
		   // @Controller 애너테이션에 의해서 BoardController2 Bean이 만들어질 때 생성자를 호출하므로 서비스가 만들어진다.
		   // public BoardController2(BoardService boardService) { // 매개변수명을 board로 해도 Bean을 가져온다. 
																 // 필드에 선언해둔 객체의 타입(BoardService)으로 가져오기때문이다.
																 // 하지만 매개변수명을 타입이랑 일치시키자. 오류발생 방지위해서.
			//	super();
			//	this.boardService = boardService;
			// }				
				
		3. setter 형식의 메소드 만들기 (필드가 많을 때는 주로 이 방식을 사용한다.)
		//	private BoardService boardService;
	
		// 생성자와 다른점은 @Autowired 애너테이션을 생략하면 안 된다는 점이다.
		// @Autowired public void setMethod(BoardService boardService) {
		//	this.boardService = boardService;
  		//	}
	*/
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/detail1.do")
	public String detail1(int boardNo, Model model) {
		BoardDTO board = boardService.detail1(boardNo);
		model.addAttribute("board", board);
		return "board/detail";
	}
	
	
	@GetMapping("/board/detail2.do")
	public String detail2(HttpServletRequest request, Model model) {
		
		model.addAttribute("board", boardService.detail2(request));
		                    
		return "board/detail";
	}
	
	@GetMapping("/board/detail3.do")
	public String detail3(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		boardService.detail3(model);
		return "board/detail";
	}

	@GetMapping("/board/list.do")
	public String list(Model model) {

		model.addAttribute("brdList", boardService.list());
		return "board/list";
	}
	
	
}
