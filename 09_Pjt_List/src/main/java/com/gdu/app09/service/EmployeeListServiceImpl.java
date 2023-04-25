package com.gdu.app09.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.app09.domain.EmpDTO;
import com.gdu.app09.mapper.EmployeeListMapper;
import com.gdu.app09.util.PageUtill;

import lombok.AllArgsConstructor;


@AllArgsConstructor	// field에 @Autowired 처리가 된다.
@Service
public class EmployeeListServiceImpl implements EmployeeListService {
	
	// field
	// 필드가 2개 이상이라 @Autowired 여러개 필요한 경우에는 생성자를 이용한 주입을 사용한다.
	// @AllArgsConstructor 롬복 애너테이션을 사용해서 생성자 만들기. 생성자를 통한 Bean주입시 @Autowired명시가 필요없으므로 가능.
	private EmployeeListMapper employeeListMapper;
	private PageUtill pageUtill;
	
	
	@Override
	public void getEmployeeListUsingPagination(HttpServletRequest request, Model model) {

		// 파라미터 page가 전달되지 않은 경우 page=1로 처리한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		// 전체 레코드 개수를 구한다.
		int totalRecord = employeeListMapper.getEmployeeCount();
		
		// 세션에 있는 recordPerPage를 가져온다. 세션에 없는 경우(첫 목록 - 1페이지 뿌릴 때) recordPerPage를=10으로 처리한다.
		HttpSession session = request.getSession();
		// 세션에 올리는 타입은 Object이다. (int든 String이든 올릴 수 있다.)
		Optional<Object> opt2 = Optional.ofNullable(session.getAttribute("recordPerPage"));
		int recordPerPage = (int)(opt2.orElse(10)); // Object에서 꺼낸걸 int타입으로 캐스팅해준다.
		
		// PageUtil(Pagination에 필요한 모든 정보) 계산
		pageUtill.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap <String, Object>();
		map.put("begin", pageUtill.getBegin());
		map.put("end", pageUtill.getEnd());
		
		// begin ~ end 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingPagination(map);
		
		// pagination.jsp로 전달할(forward) 정보 저장하기
		model.addAttribute("employees", employees);
		model.addAttribute("pagination", pageUtill.getPagination(request.getContextPath() + "/employees/pagination.do"));
		
	}

}
