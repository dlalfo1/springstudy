package com.gdu.app11.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app11.domain.AttachDTO;
import com.gdu.app11.domain.UploadDTO;

@Mapper
public interface UploadMapper {
	
	// 통상 매퍼랑 서비스는 일대일 매칭이 되지 않는다. 서비스의 개수가 더 많다. 
	// 헷갈리지 않기 위해 관련 서비스의 메소드명을 적어두는 방법도 좋다.
	
	public int getUploadCount(); // 전체 개수 구하는용도라 전달할 매개변수 X
	
	public List<UploadDTO> getUploadListUsingPagination(Map<String, Object> map);
	 
	// getUploadList
	public List<UploadDTO> getUploadList();
	
	// addUpload
	public int addAttach(AttachDTO attachDTO);
	public int addUpload(UploadDTO uploadDTO);
	
	// getUploadByNo
	public UploadDTO getUploadByNo(int uploadNo);
	public List<AttachDTO> getAttachList(int uploadNo);
	
	// display, download
	public AttachDTO getAttachByNo(int attachNo);
	
	// download
	public int increaseDownloadCount(int attachNo);
	
	// removeUpload
	public int removeUpload(int uploadNo);
	
	public UploadDTO updateUpload(UploadDTO uploadDTO); // modifyUpload로 수정하기
	
	// RemoveWrongfileScheduler
	public List<AttachDTO> getAttachListinYesterday();
	
	
	
	
	

}
