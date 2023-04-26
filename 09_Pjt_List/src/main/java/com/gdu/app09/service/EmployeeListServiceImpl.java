package com.gdu.app09.service;

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
		
		// 파라미터 order가 전달되지 않은 경우 order=ASC로 처리한다.
		Optional<String> opt3 = Optional.ofNullable(request.getParameter("order"));
		String order = opt3.orElse("ASC");
		
		// 파라미터 column이 전달되지 않는 경우 column=EMPLOYEE_ID로 처리한다.
		Optional<String> opt4 = Optional.ofNullable(request.getParameter("column"));
		String column = opt4.orElse("EMPLOYEE_ID");
		
		
		// PageUtil(Pagination에 필요한 모든 정보) 계산
		pageUtill.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap <String, Object>();
		map.put("begin", pageUtill.getBegin());
		map.put("end", pageUtill.getEnd());
		map.put("order", order);
		map.put("column", column);
		
		
		// begin ~ end 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingPagination(map);
		
		// pagination.jsp로 전달할(forward) 정보 저장하기
		model.addAttribute("employees", employees);
		// order의 값을 알고 있는 서비스에서 전달을 해줘야한다.
		model.addAttribute("pagination", pageUtill.getPagination(request.getContextPath() + "/employees/pagination.do?column=" +  column + "&order=" + order));
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		
		switch(order) {
		case "ASC" : model.addAttribute("order", "DESC"); break;	// 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
		case "DESC" : model.addAttribute("order", "ASC"); break;	// 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
		}
		
		model.addAttribute("page", page);
	
		
	}
	
	@Override
	public Map<String, Object> getEmployeeListUsingScroll(HttpServletRequest request) {
		// 실무에선 사실 ArrayList를 반환한다고 해도 반환타입을 List로 두진 않는다.
		// 보통 List를 Map에 담아 반환한다. 왜냐? Map에 담아야 여러가지를 계속 담을 수 있으므로.
		
		// 파라미터 page가 전달되지 않은 경우 page=1로 처리한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		// 전체 레코드 개수를 구한다.
		int totalRecord = employeeListMapper.getEmployeeCount();
		
		// ecordPerPage = 9로 처리한다.
		int recordPerPage = 9; 
		
		// PageUtil(Pagination에 필요한 모든 정보) 계산
		pageUtill.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap <String, Object>();
		map.put("begin", pageUtill.getBegin());
		map.put("end", pageUtill.getEnd());
		
		// begin ~ end 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingScroll(map);
		
		// model에 저장해서 forward하는 방식이 아니다. Map에 값을 담아 전달해줘야한다.
		// scroll.jsp로 응답할 데이터
		Map<String, Object> resultMap = new HashMap <String, Object>(); 
		resultMap.put("employees", employees);
		resultMap.put("totalPage", pageUtill.getTotalPage());
		
		// Map을 반환하면 만약 추가로 담아서 보내줘야할 경우 여기에 계속 put메소드 사용해서 집어넣으면 된다.
		
		return resultMap;
		
		/*
		 	resultMap이 json으로 변활될 때의 모습 (이게 곧 resData이다.)
		 	
		 	{
		 		"employees":[
		 			{
	 					"employeeId": 100,
	 					"firstName": "Steven",
	 					"lastName": "King",
		 				...,
		 				"deptDTO": {
		 					"departmentId": 90,
		 					"departmentName": "Executive",
		 					...
		 				}
		 			},
		 			{
	 					"employeeId": 101,
	 					"firstName": "Neena",
	 					"lastName": "Kochhar",
		 				...,
		 				"deptDTO": {
		 					"departmentId": 90,
		 					"departmentName": "Executive",
		 					...
		 				}
		 			},
		 			...
		 		],
		 		"totalPage": 12
		 	}	
		*/
		
		
		
		
		
		
	}

}
