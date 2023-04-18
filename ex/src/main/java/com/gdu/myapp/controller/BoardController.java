package com.gdu.myapp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.myapp.domain.BoardDTO;

@Controller
public class BoardController {

	
	@GetMapping({"/", "/index.do"})		// 여러개의 매핑값을 받고 싶을 땐 이렇게 객체로 사용하면 된다.
										// "/"			http://localhost:9090/myapp 요청인 경우에 동작한다. (context path 경로)
										// "/index.do"			http://localhost:9090/myapp 요청인 경우에 동작한다. 
	
	/*
		@GetMapping("/board")	// http://localhost:9090/myapp/board 요청인 경우에 동작한다.
		@GetMapping("board")	// http://localhost:9090/myapp/board 요청인 경우에 동작한다.
	*/
	
	// 반환타입 String : 이동할 jsp 이름을 반환한다. 반환된 이름은 servlet-context.xml의 ViewResolver에 의해서 rendering 된다. (prefix + 반환값 + suffix)
	public String index() { // 메소드명은 아무 의미없다.
		return "index";
	}
	
	// <a>, location
	/*
	
    @GetMapping("/detail.do")
	public void getdetail(HttpServletRequest request) {	// 파라미터 넘겨받을 때 결국은 HttpServletRequest request로 돌아오게 되어있음,,
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String title = request.getParameter("title");
		System.out.println(boardNo + "," + title);
	}
	*/
	
	// <form>
	/*
	@PostMapping("/detail.do")
	public void postDetail(HttpServletRequest request) {
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String title = request.getParameter("title");
		System.out.println(boardNo + "," + title);
	}
	*/

	/*
	@GetMapping("/detail.do")	// 요청 보낼 때 name(파라미터)이 없거나 input이 없거나 할 때 디폴트 처리할 때 사용하는 코드.
	public void getDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo,
						  @RequestParam (value="title", required=false, defaultValue="빈제목") String title) {	
		System.out.println(boardNo + "," + title);
	}
	
	@PostMapping("/detail.do")
	public void postDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo,
						  @RequestParam (value="title", required=false, defaultValue="빈제목") String title) { 
		System.out.println(boardNo + "," + title);
	}
	*/
	
	/*
	@GetMapping("/detail.do")
	public void getDetail(BoardDTO board) {	 // board.setBoardNo()와 board.setTitle()이 여기서 사용된다.
		System.out.println(board); // BoardDTO 객체를 출력하면 toString()이 동작한다. BoardDTO클래스의 롬복 @Data 애너테이션에 포함되어 있다.
	}
	
	@PostMapping("/detail.do")
	public void postDetail(BoardDTO board) {  // board.setBoardNo()와 board.setTitle()이 여기서 사용된다.
		System.out.println(board); // BoardDTO 객체를 출력하면 toString()이 동작한다. BoardDTO클래스의 롬복 @Data 애너테이션에 포함되어 있다.
								   // 넘어온 파라미터가 없을 경우 BoardDTO객체의 필드변수의 초기값이 출력된다. 0과 null.
	}
	*/
	
	// Model
	// 1. 주 목적 : jsp로 forward 할 데이터(attribute)를 저장하는 용도
	// 2. 속성(attribute) 저장소 4개(pageContext, request, session, application) 중에서 request를 이용한다.
	// 3. 컨트롤러에서만 선언할 수 있다.
	// request, response, session, model 이 네개는 쓰고 싶으면 컨트롤러에서 선언하고 넘겨주면 된다.
	
/*	
	@GetMapping("/detail.do")
	public String getdetail(HttpServletRequest request, Model model) {	// 파라미터 넘겨받을 때 결국은 HttpServletRequest request로 돌아오게 되어있음,,
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("boardNo"));
		int boardNo = Integer.parseInt(opt1.orElse("0"));
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("title"));
		String title = opt2.orElse("빈제목");
		model.addAttribute("boradNo", boardNo);	// request.setAttribute("boardNo", boardNo); 이것도 가능하긴한데.. 왜요..?
		model.addAttribute("title", title);
		return "detail";
	}
	
	@PostMapping("/detail.do")
	public String postDetail(HttpServletRequest request,  Model model) {
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("boardNo"));
		int boardNo = Integer.parseInt(opt1.orElse("0"));
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("title"));
		String title = opt2.orElse("빈제목");
		model.addAttribute("boradNo", boardNo);	// request.setAttribute("boardNo", boardNo); 이것도 가능하긴한데.. 왜요..?
		model.addAttribute("title", title);
		return "detail";
	}

*/
	
/*
	@GetMapping("/detail.do")	
	public String getDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo,
						    @RequestParam (value="title", required=false, defaultValue="빈제목") String title,
						    Model model) {	
		model.addAttribute("boradNo", boardNo);	
		model.addAttribute("title", title);
		return "detail";
	}
	
	@PostMapping("/detail.do")
	public String postDetail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo,
			  				 @RequestParam (value="title", required=false, defaultValue="빈제목") String title,
			  				 Model model) { 
		model.addAttribute("boradNo", boardNo);	
		model.addAttribute("title", title);
		return "detail";
	
	}
*/
	
	@GetMapping("/detail.do")
	public String getDetail(BoardDTO board) {	 // 파라미터를 객체로 받으면 자동으로 Model에 저장이 된다! 속성명은 boardDTO로 저당이 된다.! (클래스로 이용해서 속성명을 만든다!)
		
		return "detail";
		
	}
	
	@PostMapping("/detail.do")
	public String postDetail(@ModelAttribute("board") BoardDTO board) { 	// Model에 저장하는 속성명을 "board"로 하겠다!
		
		
		return "detail";
								 
	}
	
	
	
}
