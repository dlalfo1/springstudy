package com.gdu.app03.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app03.domain.BmiVO;
import com.gdu.app03.service.ISecondService;


@Controller
public class SecondController {
	
	private ISecondService secondService;
	
	// @AllArgsConstructor으로 아래 생성자를 대체해도 좋다!
	@Autowired // 생성자에서 @Autowired는 생략할 수 있다.
	public SecondController(ISecondService secondService) { // <bean>태그로 만들어둔 secondService객체가 이리로 들어오고
		super();
		this.secondService = secondService;					// 여기서 필드로 주입이 되는 것이다. 즉 SecondController에서 secondService 객체 사용 가능하다.
	}

	
	@ResponseBody // 이걸 붙여줘야만 return의 secondService.execute1(request, response) 이 값이 jsp이름으로 해석되지 않는다.
				  // 컨트롤러는 뷰리졸버랑 짝꿍이기 때문이다! 컨트롤러가 반환하는건 다 뷰리졸버가 해석하는데 그걸 막아주는거임.
	@GetMapping(value="/second/bmi1", produces=MediaType.APPLICATION_JSON_VALUE) // MediaType.APPLICATION_JSON_VALUE는 "application/json"이다.
	public BmiVO bmi1(HttpServletRequest request, HttpServletResponse response) {
		
		return secondService.execute1(request, response);
		
	}
	
	@ResponseBody
	@GetMapping(value="/second/bmi2", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> bmi2(BmiVO bmiVO){
		return secondService.execute2(bmiVO);
	}

}
