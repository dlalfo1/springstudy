package com.gdu.app11.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.domain.AttachDTO;
import com.gdu.app11.domain.UploadDTO;
import com.gdu.app11.mapper.UploadMapper;
import com.gdu.app11.utill.MyFileUtill;
import com.gdu.app11.utill.PageUtill;

import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@AllArgsConstructor // field @Autowired 처리
public class UploadServiceImpl implements UploadService {
	
	// field
	// 생성자를 이용해서 Bean 주입함. (@Autowired 명시 필요x)
	private UploadMapper uploadMapper;
	private MyFileUtill myFileUtill;
	private PageUtill pageUtill;
	
	// 권장 사항 : Pagination 처리 해 보기
	
	@Override
	public void getUploadListUsingPagination(HttpServletRequest request, Model model) {
		
		// 파라미터 page가 전달되지 않은 경우 page=1로 처리한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		// 전체 레코드 개수를 구한다.
		int totalRecord = uploadMapper.getUploadCount();
		
		// 세션에 있는 recordPerPage를 가져온다. 세션에 없는 경우(첫 목록 - 1페이지 뿌릴 때) recordPerPage를=10으로 처리한다.
		HttpSession session = request.getSession();
		// 세션에 올리는 타입은 Object이다. (int든 String이든 올릴 수 있다.)
		Optional<Object> opt2 = Optional.ofNullable(session.getAttribute("recordPerPage"));
		int recordPerPage = (int)(opt2.orElse(10)); // Object에서 꺼낸걸 int타입으로 캐스팅해준다.
		
		// 파라미터 order가 전달되지 않은 경우 order=ASC로 처리한다.
		Optional<String> opt3 = Optional.ofNullable(request.getParameter("order"));
		String order = opt3.orElse("ASC");
		
		// 파라미터 column이 전달되지 않는 경우 column=EMPLOYEE_ID로 처리한다.
		Optional<String> opt4 = Optional.ofNullable(request.getParameter("column"));
		String column = opt4.orElse("UPLOAD_NO");
		
		
		// PageUtil(Pagination에 필요한 모든 정보) 계산
		pageUtill.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap <String, Object>();
		map.put("begin", pageUtill.getBegin());
		map.put("end", pageUtill.getEnd());
		map.put("order", order);
		map.put("column", column);
		
		
		// begin ~ end 사이의 목록 가져오기
		List<UploadDTO> uploads = uploadMapper.getUploadListUsingPagination(map);
		
		// pagination.jsp로 전달할(forward) 정보 저장하기
		model.addAttribute("uploads", uploads);
		// order의 값을 알고 있는 서비스에서 전달을 해줘야한다.
		model.addAttribute("pagination", pageUtill.getPagination(request.getContextPath() + "/upload/pagination.do?column=" +  column + "&order=" + order));
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		
		switch(order) {
		case "ASC" : model.addAttribute("order", "DESC"); break;	// 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
		case "DESC" : model.addAttribute("order", "ASC"); break;	// 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
		}
		
		model.addAttribute("page", page);
	
		
	}
	
	@Override
	public void getUploadList(Model model) {
		List<UploadDTO> uploadList = uploadMapper.getUploadList();
		model.addAttribute("uploadList",uploadList);
		
	}	

	@Override
	@Transactional(readOnly = true)
	// addUpload메소드에선 INSERT문이 2개 실행되기때문에 트랜잭션이 필수다!
	public int addUpload(MultipartHttpServletRequest multipartRequest) {
		
		/* Upload 테이블에 UploadDTO 넣기 */
		String uploadTitle = multipartRequest.getParameter("uploadTitle");
		String uploadContent = multipartRequest.getParameter("uploadContent");
		
		// DB로 보낼 uploadDTO 만들기
		UploadDTO uploadDTO = new UploadDTO();
		uploadDTO.setUploadTitle(uploadTitle);
		uploadDTO.setUploadContent(uploadContent);
		
		int addResult = uploadMapper.addUpload(uploadDTO);
		// 여기서 sevice는 upload_no가 얼만지 모른다. 왜냐? db가 시퀀스로 알아서 만들어주기 때문이다.
		// <selectkey>에 의해서 uploadDTO 객체의 uploadNo 필드에 UPLOAD_SEQ.NEXTVAL값이 저장된다.
		
		/* Attach 테이블에 AttachDTO 넣기 */
		
		// 첨부된 파일 목록
		List<MultipartFile> files = multipartRequest.getFiles("files"); // <input type="file" name="files"> name속성의 값이 넘어온다.
		
			
			// 첨부된 파일 목록 순회
			// 파일첨부 하나당 쿼리문이 한번 도는데 여러개라면 여러번 돌기 때문에 for문으로 순회해서 찾아준다.
			for(MultipartFile multipartFile : files) { // arrayList files에서 하나씩 꺼내온게 multipartFile이 된다.
				
				// 첨부된 파일이 있는지 체크
				if(multipartFile != null && multipartFile.isEmpty() == false) { // 첨부 파-일이 있고 비어있지 않을 때

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
					
					/* 썸네일 (첨부 파일이 이미지인 경우에만 썸네일이 가능) */
					
					// 첨부 파일의 Content-Type 확인
					// 첨부 파일의 File객체를 이용해서 probeContentType() 메소드에 들어갈 path 매개변수를 구할 수 있다.
					// 이미지 파일의 Content-Type : image/jpeg, image/png, image/gif, .. Content-Type이 image로 시작하면 이미지파일이다.
					String contentType = Files.probeContentType(file.toPath()); 
					
					// 첨부 파일의 Content-Type이 이미지로 확인되면 썸네일을 만듦.
					// contentType이 null이 아니고 "image"문자열로 시작할 때 썸네일을 만든다.
					boolean hasThumbnail = contentType != null && contentType.startsWith("image");
					if(hasThumbnail) { 
						
						// HDD에 썸네일 저장하기 (thumbnailator 디펜던시 사용)
						// 썸네일은 원래 첨부파일의 UUID명 앞에 s_ 를 붙여서 저장한다.
						File thumbnail = new File(dir, "s_" + filesystemName);
						Thumbnails.of(file)
							.size(50, 50)
							.toFile(thumbnail);
						// Thumbnails.of(file).size(50, 50).toFile(new File(dir, "s_" + filesystemName)); 위 코드를 한줄로 푼 것
	
					}
					
					/* DB에 첨부파일 정보 저장하기 */
					
					// DB로 보낼 AttachDTO 만들기
					AttachDTO attachDTO = new AttachDTO();
					attachDTO.setFilesystemName(filesystemName);
					attachDTO.setHasThumbnail(hasThumbnail ? 1 : 0);
					attachDTO.setOriginName(originName);
					attachDTO.setPath(path);
					attachDTO.setUploadNo(uploadDTO.getUploadNo());					
					
					// DB로 AttachDTO 보내기
					uploadMapper.addAttach(attachDTO);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		return addResult;
	}

	@Override
	public void getUploadByNo(int uploadNo, Model model) {
		model.addAttribute("upload", uploadMapper.getUploadByNo(uploadNo));
		model.addAttribute("attachList", uploadMapper.getAttachList(uploadNo));
		
	}
	
	@Override
	public ResponseEntity<byte[]> display(int attachNo) {
		
		AttachDTO attachDTO = uploadMapper.getAttachByNo(attachNo);
		
		ResponseEntity<byte[]> image = null;
		
		try {
			File thumbnail = new File(attachDTO.getPath(), "s_" + attachDTO.getFilesystemName());
			image = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	@Override
	public ResponseEntity<Resource> download(int attachNo, String userAgent) {
		
		// 다운로드 할 첨부 파일의 정보(경로, 원래 이름, 저장된 이름) 가져오기
		AttachDTO attachDTO = uploadMapper.getAttachByNo(attachNo);
		
		// 다운로드 할 첨부 파일의 File 객체 -> Resource 객체 변환
		File file = new File(attachDTO.getPath(), attachDTO.getFilesystemName()); // FilesystemName이 저장된 파일의 이름이다.
		Resource resource = new FileSystemResource(file); // 파일이 Resource 객체로 변환되어 resource 변수에 담긴다.
		
		// 다운로드 할 첨부 파일의 존재 여부 확인(다운로드 실패를 반환);
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);	
		}
		
		// 다운로드 횟수 증가하기
		uploadMapper.increaseDownloadCount(attachNo);
		
		// 다운로드 되는 파일명(첨부 파일의 원래 이름, UserAgent(브라우저)에 따른 인코딩 세팅)
		String originName = attachDTO.getOriginName();
		try {
			
			// IE (UserAgnet에 Trident가 포함되어 있다.
			if(userAgent.contains("Trident")) {
				originName = URLEncoder.encode(originName, "UTF-8").replace("+", ""); // IE는 공백을 +로 처리하기 때문에 빈 문자열로 바꿔준다.
			}
			// Edge (UserAgent에 Edg가 포함되어 있다.
			else if(userAgent.contains("Edg")) {
				originName = URLEncoder.encode(originName, "UTF-8");
			}
			// Other
			else {
				originName = new String(originName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 다운로드 응답 헤더 만들기(Java/Serlvet 코드)
		/*
		MultiValueMap<String, String> responseHeader = new HttpHeaders();
		responseHeader.add("Content-Type", "application/octet-stream");
		responseHeader.add("Content-Disposition", "attachment; filename=" + originName); // header값에 이렇게 넣어줘야 다운로드가 가능하다.
																					     // filename을 사용하여 파일명을 원래 이름으로 정해준다.
		responseHeader.add("Content-Length", file.length() + ""); // String으로 변환하기 위해 빈 문자열을 더해준다.
		*/
		 
		// 다운로드 응답 헤더 만들기 (Spring 코드)
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		responseHeader.setContentDisposition(ContentDisposition
												.attachment()
												.filename(originName)
												.build());
		responseHeader.setContentLength(file.length());
		
		// 응답
		return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Resource> downloadAll(int uploadNo) {
		
		// 모든 첨부 파일을 zip 파일로 다운로드 하는 서비스
		// com.gdu.app11.batch.RemoveTempfileScheduler에 의해서 주기적으로 zip 파일들은 삭제된다.
		
		
		// zip 파일이 저장될 경로 
		String tempPath = myFileUtill.getTempPath();
		File dir = new File(tempPath);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
		// zip 파일의 이름
		String tempfileName = myFileUtill.getTempfileName();
		
		// zip 파일의 File 객체
		File zfile = new File(tempPath, tempfileName);
	
		// zip 파일을 생성하기 위한 Java IO Stream 선언
		BufferedInputStream bin = null;   // 각 첨부파일 읽을 들이는 스트림
		ZipOutputStream zout = null; 	 // zip파일을 만드는 스트림
		
		// 다운로드 할 첨부 파일들의 정보(경로, 원래 이름, 저장된 이름) 가져오기
		List<AttachDTO> attachList = uploadMapper.getAttachList(uploadNo);
		
		try {
			
			// ZipOutputStream zout 객체 생성
			zout = new ZipOutputStream(new FileOutputStream(zfile));
			
			// 첨부 파일을 하나씩 순회하면서 읽어 들인 뒤 zip 파일에 추가하기 + 각 첨부 파일들의 다운로드 횟수 증가시키기 
			for(AttachDTO attachDTO : attachList) {
				
				// zip 파일에 추가할 첨부 파일 이름 등록(첨부 파일의 원래 이름)
				ZipEntry zipEntry = new ZipEntry(attachDTO.getOriginName());
				zout.putNextEntry(zipEntry);
				
				
				// zip 파일에 첨부 파일 추가
				bin = new BufferedInputStream(new FileInputStream(new File(attachDTO.getPath(), attachDTO.getFilesystemName())));
				
				
				byte[] b = new byte[1024]; // 첨부파일을 1KB 단위로 읽겠다.
				int readByte = 0;		   // 실제로 읽어들인 바이트 수
				while((readByte = bin.read(b)) != -1) { // -1이 아니라면 모두 읽지 않은게 된다.(읽을게 남았다는 뜻)
					zout.write(b, 0, readByte);			// byte배열(b)의 인덱스 0부터 실제로 읽은 바이트 수까지 보내기
														// 파일의 크기는 제멋대로이기 때문에 while문으로 처리한다.
				}
				bin.close();
				zout.closeEntry();
				
				// FileCopyUtils.copy(bin, zout); 스프링의 이 코드는 사용하면 안 된다. close에서 문제가 발생함
				// FileCopyUtils을 사용할 경우 여기서 zout.close();를 해버린다.
				
				// 각 첨부 파일들의 다운로드 횟수 증가
				uploadMapper.increaseDownloadCount(attachDTO.getAttachNo());
				
			}
			zout.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 다운로드 할 zip 파일의 File 객체 -> Resource 객체 변환
		Resource resource = new FileSystemResource(zfile);
		
		// 다운로드 응답 헤더 만들기
		MultiValueMap<String, String> responseHeader = new HttpHeaders();
		responseHeader.add("Content-Disposition", "attachment; filename=" + tempfileName); // header값에 이렇게 넣어줘야 다운로드가 가능하다.
																					     // filename을 사용하여 파일명을 원래 이름으로 정해준다.
		responseHeader.add("Content-Length", zfile.length() + ""); // String으로 변환하기 위해 빈 문자열을 더해준다.
		
		// 응답
		return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
		
	}
	
	@Override
	public int removeUpload(int uploadNo) {
		
		// 삭제할 첨부파일들의 정보
		List<AttachDTO> attachList = uploadMapper.getAttachList(uploadNo);

		// 첨부 파일이 있으면 삭제
		if(attachList != null && attachList.isEmpty() == false) {
			// 삭제할 첨부 파일들을 순회하면서 하나씩 삭제
			for(AttachDTO attachDTO : attachList) {
				
				// 삭제할 첨부 파일의 File 객체
				File file = new File(attachDTO.getPath(), attachDTO.getFilesystemName());
				
				// 첨부 파일 삭제
				if(file.exists()) {
					file.delete();
				}
				
				// 첨부 파일이 썸네일을 가지고 있다면 "s_"로 시작하는 썸네일이 함께 존재하므로 함께 제거해야 한다.
				if(attachDTO.getHasThumbnail() == 1) {
					
					// 삭제할 썸네일의 File 객체
					File thumbnail = new File(attachDTO.getPath(), "s_" + attachDTO.getFilesystemName());
					
					// 썸네일 삭제
					if(thumbnail.exists()) {
						thumbnail.delete();
					}
				}
				
			}
		}
		
		// DB에서 uploadNo값을 가지는 데이터를 삭제
		// 외래키 제약조건에 의해서(ON DELETE CASCADE) UPLOAD 테이블의 데이터가 삭제되면 
		// ATTACH 테이블의 데이터도 함께 삭제된다.
		int removeResult = uploadMapper.removeUpload(uploadNo);
		
		return removeResult;
	}
	
	@Override
	public UploadDTO modifyUpload(UploadDTO uploadDTO) {
		
		
		return null;
	}
	
	
	
	
	
}
