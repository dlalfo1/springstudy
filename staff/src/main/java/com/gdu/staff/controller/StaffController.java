package com.gdu.staff.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.staff.domain.StaffDTO;
import com.gdu.staff.service.StaffService;

@Controller
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	@ResponseBody
	@GetMapping(value="/list.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<StaffDTO> list1() {
		
		return staffService.getStaffList1();
	}


	@ResponseBody // String타입의 return값이 jsp의 이름이 아니라 응답데이터라는걸 알려주는 애너테이션. 
	@PostMapping(value="/add.do", produces="text/plain; charset=UTF-8")
	public String add1(HttpServletRequest request) {
		return staffService.addStaff1(request);
	}
	
	
	@GetMapping(value="/query.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StaffDTO> searchStaff(HttpServletRequest request){
		return staffService.searchStaff(request);
		
	}
	
	
	

}
