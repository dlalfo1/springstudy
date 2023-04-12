package com.gdu.app02.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app02.domain.Person;

@Controller
public class MvcController {
	
	/*
	  	@RequestMapping을 대체하는 새로운 애너테이션(Spring4)
	  	1. @GetMapping
	  	2. @PostMapping
	  	3. @PutMapping 	  : 수정할 때
	  	4. @DeleteMapping : 삭제할 때
	 */
	
	/*
		요청 파라미터 UTF-8 인코딩 처리
		
		메소드마다 request.setCharcterEncoding("UTF-8")을 작성하는 것은 매우 비효율적이므로,
		모든 요청(contextPath를 가진 모든 요청)마다 동작하도록 filter를 사용한다.
		CharcterEncodingFilter를 통해서 모든 요청마다 자동으로 UTF-8로 인코딩된다.
		참고할 파일 : web.xml
	*/
	               
	/* 1. HttpServletRequest로 요청 파라미터 처리하기 (이거 많이 씀) */
	
	@GetMapping("/detail.do") // 앞에 슬래시(/)는 생략해도 되지만 그냥 적어줄 것.
	public String detail(HttpServletRequest request, Model model) {	// 불편함 : 요청마다 객체 받아서 사용해야 함.
		
		// name의 전달이 없으면 "홍길동"이 사용된다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("name"));
		String name = opt1.orElse("홍길동");
		
		// age의 전달이 없으면 "0"이 사용된다.
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("age"));
		int age = Integer.parseInt(opt2.orElse("0"));
		
		/*
		  	Model model
		  	1. 스프링에서 사용하는 데이터(속성) 전달 객체이다.
		  	2. Model-2(Jsp/Servlet)에서는 HttpServletRequest request 객체를 사용해서 데이터를 전달하지만, 
		  	   스프링에서는 Model model 객체를 사용한다.
		  	3. forward할 데이터를 Model의 addAttribute() 메소드로 저장한다.
		 */
		
		model.addAttribute("name", name);	// 받아온 파라미터의 값이 담긴 name을 addAttribute()메소드를 사용해서 속성에 담는다
		model.addAttribute("age", age);		// 받아온 파라미터의 값이 담긴 age를 addAttribute()메소드를 사용해서 속성에 담는다
		
		// 기본 이동 방식은 forward이다.
		// 삽입, 삭제, 수정시에만 redirec 이동한다.
		return "mvc/detail";	// 실제 처리 경로	:	/WEB-INF/views/mvc/detail.jsp
	
		/*
		  	참고. redirect로 이동하기
		  	return "redirect:이동할경로";
		*/
	}
	
	/*
	  	2. @RequestParam으로 요청 파라미터 처리하기
	  		1) value 		: 요청 파라미터 이름
	  		2) required		: 요청 파라미터의 필수 여부(디폴트는 true)
	  		3) defaultValue : 요청 파라미터가 없을 때 대신 사용할 값
	 */
	
	@GetMapping("/detail.me")
	public String detail(@RequestParam(value="name", required=false, defaultValue="홍길동") String name, // 요청 파라미터 name이 없으면 "홍길동"을 사용한다.
			             @RequestParam(value="age", required=false, defaultValue="0") int age,			 // 요청 파라미터 age가 없으면 "0"을 사용한다.
			             Model model) {
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		
		return "mvc/detail";
	}
	
	
	/*
		@GetMapping("/detail.me")
		
	    // 이렇게 @RequestParam애너테이션을 사용하지 않아도 파라미터가 선언된 매개변수로 들어온다.
		public String detailMe(String name,			// 요청 파라미터 name이 없으면 null이 저장된다. 
		                                            //  즉, name파라미터의 값이 없어도 null값이 넘어오기 때문에 페이지가 뻑나진 않는다. 
		 						  int age,  		// 요청 파라미터 age가 없으면 null을 int로 변환하려고 하기 때문에 오류가 발생한다.
		 						  Model model) { 
			
			model.addAttribute("name", name);
			model.addAttribute("age", age);
			
			
			return "mvc/detail";
			
		}
	
	*/
	
	/*
		3. 커맨드 객체를 이용한 요청 파라미터 처리
			1) 파라미터를 필드로 가진 객체를 커맨드 객체라고 한다.
			2) setter가 사용된다.
			3) 커맨드 객체는 자동으로 Model에 저장된다.
	*/
	@GetMapping("/detail.gdu")
	public String detailGdu(Person p) { // name과 age를 필드로 가진 커맨드 객체 Person p
										// Model에 저장될 때 객체 이름인 p를 사용하지 않고,
										// 객체 타입인 Person을 사용한다. 
										// Model에 저장되는 속성명은 객체 타입 Person을 person으로 수정해서 사용한다. (소문자로 바뀌어서 사용됨)
		return "mvc/detail";		
		
	}
	
	/*
			@GetMapping("/detail.gdu")
			public String detailGdu(@ModelAttribute(value="p") Person p) { // Model에 저장할 속성명을 p로 해주세요.
				return "mvc/detail";									   // detail.jsp에서 ${p.name}, ${p.age}와 같은 형식으로 확인할 수 있다.
		
			}
	*/
	
	
	
	
	
	
	
	
	
	

}
