package com.gdu.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.staff.domain.StaffDTO;

@Mapper
public interface StaffMapper {
	public List<StaffDTO> getStaffList();
	public int addStaff(StaffDTO staffDTO);
	public StaffDTO searchStaff(StaffDTO staffDTO);

}