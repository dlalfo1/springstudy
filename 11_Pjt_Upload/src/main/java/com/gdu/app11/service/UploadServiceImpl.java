package com.gdu.app11.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.mapper.UploadMapper;
import com.gdu.app11.utill.MyFileUtill;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor // field @Autowired 처리
public class UploadServiceImpl implements UploadService {
	
	// field
	// 생성자를 이용해서 Bean 주입함. (@Autowired 명시 필요x)
	private UploadMapper uploadMapper;
	private MyFileUtill myFileUtill;

	@Override
	public int addUpload(MultipartHttpServletRequest multipartRequest) {
		
		/* Upload 테이블에 UploadDTO 넣기 */
		
		/* Attach 테이블에 AttachDTO 넣기 */
		
		// 첨부된 파일 목록
		List<MultipartFile> files = multipartRequest.getFiles("files"); // <input type="file" name="files"> name속성의 값이 넘어온다.
		
		// 첨부된 파일이 있는지 체크
		if(files != null && files.isEmpty() == false) { // 첨부 파일이 있고 비어있지 않을 때
			
			// 첨부된 파일 목록 순회
			// 파일첨부 하나당 쿼리문이 한번 도는데 여러개라면 여러번 돌기 때문에 for문으로 순회해서 찾아준다.
			for(MultipartFile multipartFile : files) { // arrayList files에서 하나씩 꺼내온게 multipartFile이 된다.
				
				// 예외 처리
				try {
					
					// 첨부 파일의 저장 경로 
					String path = myFileUtill.getPath();
					
					// 첨부 파일의 저장 경로가 없으면 만들기
					File dir = new File(path);
					if(dir.exists() == false) { // 경로가 존재하지 않으면
						dir.mkdirs();			// 만들어라.
					}
					
					// 첨부 파일의 원래 이름 
					String originName = multipartFile.getOriginalFilename();
					// -\-\-\-\-\파일 이름 
					// IE는 전체 경로가 오기 때문에 마지막 역슬래시 뒤에 있는 파일명만 사용한다.
					originName = originName.substring(originName.lastIndexOf("\\") + 1); 
					
					// 첨부 파일의 저장 이름
					String filesystemName = myFileUtill.getFilesystemName(originName);
					
					// 첨부 파일의 File 객체 (HDD(하드디스크)에 저장할 첨부 파일)
					File file = new File(dir, filesystemName); // dir 경로에 filesystemName이 파일명인 객체
					
					// 첨부 파일을 HDD에 저장
					multipartFile.transferTo(file); // 실제로 서버에 저장된다.
													// 여기까지 진행시 DB엔 저장되지 않지만 하드디스크엔 저장된다.
					
					// 썸네일 
					
					/* DB에 첨부정보 저장하기 */
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		return 0;
	}

}
