package com.gdu.app11.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
	public int addUpload(MultipartHttpServletRequest multipartRequest); // 첨부시엔 MultipartHttpServletRequest 이걸 사용한다.
		
}
