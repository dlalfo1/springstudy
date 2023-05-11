package com.gdu.app11.batch;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.app11.domain.AttachDTO;
import com.gdu.app11.mapper.UploadMapper;
import com.gdu.app11.utill.MyFileUtill;

import lombok.AllArgsConstructor;

@AllArgsConstructor		// field의 @Autowired 처리
@EnableScheduling
@Component
public class RemoveWrongfileScheduler {
	
	// field
	private MyFileUtill myFileUtill;
	private UploadMapper uploadMapper;
	
	@Scheduled(cron="0 54 17 1/1 * ?") // www.cronmaker.com에서 생성한 매일 새벽 2시 정보에서 마지막
	public void execute() {		// 메소드명은 아무 의미 없다.
		
		// 어제 업로드 된 첨부 파일들의 정보 (DB에서 가져오기)
		List<AttachDTO> attachList = uploadMapper.getAttachListinYesterday();
		
		// List<AttachDTO> -> List<Path>로 변환하기 (Path : 경로 + 파일명)
		List<Path> pathList = new ArrayList<Path>();;
		if(attachList != null && attachList.isEmpty() == false) {
			for(AttachDTO attachDTO : attachList) {
				pathList.add(new File(attachDTO.getPath(), attachDTO.getFilesystemName()).toPath());	// Path 만들기 : File객체.toPatho()
				if(attachDTO.getHasThumbnail() == 1) {
					pathList.add(new File(attachDTO.getPath(), "s_" + attachDTO.getFilesystemName()).toPath());
				}
			}
		}
		
		// 어제 업로드 된 경로
		String yesterdayPath = myFileUtill.getYesterdayPath();
		
		// 어제 업로드 된 파일 목록 (HDD에서 확인) 중에서 DB에는 정보가 없는 파일 목록
		File dir = new File(yesterdayPath);
		File[] wrongFiles = dir.listFiles(new FilenameFilter() { // 인터페이스가 가지고 있는 메소드를 재정의 할 때 new를 사용할 수 있다.
			
			@Override
			public boolean accept(File dir, String name) {	// true를 반환하면 File[] wrongFiles에 포함된다. 매개변수 File dir, String name은 HDD에 저장된 파일을 의미한다.
				// DB에 있는 목록  : pathList				- 이미 Path
				// HDD에 있는 파일 : File dir, String name  - File.toPath() 처리해서 Path로 변경
				return pathList.contains(new File(dir,name).toPath()) == false;
			}
		});
		
		// File[] wrongFiles 모두 삭제
		if(wrongFiles != null && wrongFiles.length != 0) { // 배열이니까 이렇게 체크해주기
			for(File wrongFile : wrongFiles) {
				wrongFile.delete();
			}
		}
		
	}
}
