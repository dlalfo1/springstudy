package com.gdu.app09.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app09.service.EmployeeListService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeListService employeeListService;
	
	@GetMapping("/employees/pagination.do")
	public String pagination(HttpServletRequest request, Model model) {
		
		employeeListService.getEmployeeListUsingPagination(request, model);
		return "employees/pagination";
		
	}
	/*	
	  - 기존에 사용하던 코드 -
		@GetMapping("/employees/change/record.do")
		public String changeRecord(HttpServletRequest request) { 
			
			
			Optional<String> opt = Optional.ofNullable(request.getParameter("recordPerPage"));
			int recordPerPage = Integer.parseInt(opt.orElse("10"));
			
			HttpSession session = request.getSession();
			session.setAttribute("recordPerPage", recordPerPage);
			return "";
		}
	*/
	
	@GetMapping("/employees/change/record.do")
	
	public String changeRecord(HttpSession session 		  // Session을 사용하고 싶다면 HttpServletRequest에서 가져오지 않아도 바로 선언해서 사용하면 된다.
							, HttpServletRequest request  // 여기에서 HttpServletRequest을 사용하고 싶으면 매개변수에 또 선언해주면 되는거임~!
							, @RequestParam(value="recordPerPage", required = false, defaultValue="10")int recordPerPage) { 
												   // recordPerPage 파라미터를 받기 위해 @RequestParam 애너테이션을 사용한다.
												  
		session.setAttribute("recordPerPage", recordPerPage);
		return "redirect:" + request.getHeader("referer"); // 현재 주소(/employees/change/record.do)의 이전 주소(Referer)로 이동하시오.
														   // /change/record.do 하기전에 니가 보고 있던 페이지로 돌아가라.
		
	}

}