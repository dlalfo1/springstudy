package com.gdu.app11.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app11.service.UploadService;

@RequestMapping("/upload")
@Controller
public class UploadController {
	
	// field
	@Autowired
	private UploadService uploadService;

	@GetMapping("/pagination.do")
	public String pagination(HttpServletRequest request, Model model) {
		uploadService.getUploadListUsingPagination(request, model);
		return "upload/pagination";
		
	}
	
	@GetMapping("/change/record.do")
	
	public String changeRecord(HttpSession session 		  // Session을 사용하고 싶다면 HttpServletRequest에서 가져오지 않아도 바로 선언해서 사용하면 된다.
							, HttpServletRequest request  // 여기에서 HttpServletRequest을 사용하고 싶으면 매개변수에 또 선언해주면 되는거임~!
							, @RequestParam(value="recordPerPage", required = false, defaultValue="10")int recordPerPage) { 
												   // recordPerPage 파라미터를 받기 위해 @RequestParam 애너테이션을 사용한다.
												  
		session.setAttribute("recordPerPage", recordPerPage);
		return "redirect:" + request.getHeader("referer"); // 현재 주소(/employees/change/record.do)의 이전 주소(Referer)로 이동하시오.
														   // /change/record.do 하기전에 니가 보고 있던 페이지로 돌아가라.
		
	}
	
	@GetMapping("/list.do")
	String list(Model model) {
		uploadService.getUploadList(model);
		return "upload/list";
	}
	
	@GetMapping("/write.do")
	String write() {
		return "upload/write";
	}
	
	@PostMapping("/add.do") // 반환타입이 void인 경우 mapping값을 jsp파일의 이름으로 해석한다. : add.jsp
	public String add(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) {
		int uploadResult = uploadService.addUpload(multipartRequest);
		redirectAttributes.addFlashAttribute("uploadResult", uploadResult);
		return "redirect:/upload/list.do";
		
	}
	
	@GetMapping("/detail.do")
	public String detail(@RequestParam(value="uploadNo", required=false, defaultValue="0") int uploadNo 
						, Model model) {
		uploadService.getUploadByNo(uploadNo, model);
		return "upload/detail";
	}
	
	@GetMapping("/display.do")
	public ResponseEntity<byte[]> display(@RequestParam("attachNo") int attachNo){
		return uploadService.display(attachNo);
	}
	
	@GetMapping("/download.do")
	// 파라미터와 헤더를 하나로 받고 싶다면 HttpServletRequest 사용하면 된다.
	public ResponseEntity<Resource> download(@RequestParam("attachNo") int attachNo, @RequestHeader("User-Agent") String userAgent){
		
		return uploadService.download(attachNo, userAgent);
	}
	
	@GetMapping("/downloadAll.do")
	// 파라미터와 헤더를 하나로 받고 싶다면 HttpServletRequest 사용하면 된다.
	public ResponseEntity<Resource> downloadAll(@RequestParam("uploadNo") int uploadNo){
		
		return uploadService.downloadAll(uploadNo);
	}
	
	@PostMapping("/removeUpload.do")
	public String removeUpload(@RequestParam("uploadNo") int uploadNo, RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("removeResult", uploadService.removeUpload(uploadNo));
		
		return "redirect:/upload/list.do"; // 삭제 후엔 목록보기로 리다이렉트 이동 한다.
		
	}
/*	
	@PostMapping("/edit.do")
	public String editUpload(@RequestParam("uploadNo") int uploadNo, Model model) {
		
	}
*/	
	
}
