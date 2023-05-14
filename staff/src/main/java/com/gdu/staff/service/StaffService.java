package com.gdu.staff.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.gdu.staff.domain.StaffDTO;

public interface StaffService {
	public List<StaffDTO> getStaffList1();
	public String addStaff1(HttpServletRequest request);
	public ResponseEntity<StaffDTO> searchStaff(HttpServletRequest request);
	
}
