package com.gdu.staff.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.staff.domain.StaffDTO;
import com.gdu.staff.mapper.StaffMapper;

@Service
public class StaffServiceImpl implements StaffService {
	

	@Autowired
	private StaffMapper staffMapper;
	
	@Override
	public List<StaffDTO> getStaffList1() {
		List<StaffDTO> staffList = staffMapper.getStaffList();
		return staffList;
	}

	
	@Override
	public String addStaff1(HttpServletRequest request) {
		try {
			String sno = request.getParameter("sno");
			String name = request.getParameter("name");
			String dept = request.getParameter("dept");
			StaffDTO staffDTO = new StaffDTO();
			staffDTO.setSno(sno);
			staffDTO.setName(name);
			staffDTO.setDept(dept);
			staffMapper.addStaff(staffDTO);	// 예시) 사원번호가 5 BYTE 초과되거나 중복되거나 NULL인 경우 예외 발생
			return "사원 등록이 성공했습니다.";
		} catch (Exception e) {
			return "사원 등록이 실패했습니다.";	//	$.ajax의 error로 전달
		}
	}
	
	@Override
	public ResponseEntity<StaffDTO> searchStaff(HttpServletRequest request) {

		String sno = request.getParameter("query");
		StaffDTO staffDTO = new StaffDTO();
		staffDTO.setSno(sno);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<StaffDTO>(staffMapper.searchStaff(staffDTO), HttpStatus.OK);
		
	}
	

}
