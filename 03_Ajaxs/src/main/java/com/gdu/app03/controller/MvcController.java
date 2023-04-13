package com.gdu.app03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

	@GetMapping("/")
	public String index() {
		
		return "index";
	}
	
	@GetMapping("/first.do")
	public String first() {
		
		return "first";
	}
	
	@GetMapping("/second.do")
	public String second() {
		
		return "second";
	}
}
