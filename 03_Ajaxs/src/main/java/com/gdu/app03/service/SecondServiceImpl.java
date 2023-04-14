package com.gdu.app03.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import com.gdu.app03.domain.BmiVO;

public class SecondServiceImpl implements ISecondService {

	
	/*
		ResponseEntity<T> 클래스
		1. Ajax 응답 데이터를 생성하는 클래스이다.
		2. 생성자 중 하나의 사용법
			public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status)
			1) @Nullable T body : 실제로 응답할 데이터	: 실제로 응답할 데이터
			2) MultiValueMap<String, String> headers 	: 응답 헤더(대표적으로 Content-Type)
			3) HttpStatus status						: 응답 코드(200, 404, 500 등)  
														  원래 catch문 안에 들어있어야 ajax의 error로 데이터가 넘어갔는데 이걸로 처리해줘도 된다.
	*/
	
	@Override
	public ResponseEntity<BmiVO> execute1(HttpServletRequest request) {
		
		try {
			double weight = Double.parseDouble(request.getParameter("weight"));
			double height = Double.parseDouble(request.getParameter("height")) / 100;
			
			double bmi = weight / (height * height);	// bmi = 몸무게(kg) / 키(m) * 키(m)
			
			String obesity = null;
			
			if(bmi < 18.5) {
				obesity = "저체중";
			} else if(bmi < 24.9) {
				obesity = "정상";
			} else if(bmi < 29.9) {
				obesity = "과체중";
			} else {
				obesity = "비만";
			}
			
			return new ResponseEntity<BmiVO>(new BmiVO(weight, height, bmi, obesity), HttpStatus.OK); // HttpStatus.OK : 200(정상)
			
			} catch(Exception e) {
				
				BmiVO bmiVO = null;
				return new ResponseEntity<BmiVO>(bmiVO, HttpStatus.INTERNAL_SERVER_ERROR);	// HttpStatus.INTERNAL_SERVER_ERROR : 500(서버에러)
																							// 500이므로 $.ajax의 error로 처리 된다.
				
				// T body 넣을 자리에 직접적으로 null값 넣지말고 null값이 담긴 객체를 넣어줘야 함.
			}
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> excute2(BmiVO bmiVO) { // 몸무게가 빈 문자열로 전달되면 스프링이 double타입으로 스스로 변환하려다
																	  // NumberFormatException 예외가 생긴다. 보통 프론트단에서 오류를 잡아준다.
		
		double weight = bmiVO.getWeight();
		double height = bmiVO.getHeight();
				
		if(weight == 0 || height == 0) {
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); // 응답 코드가 정상(200)이 아니면 $.ajax 요청의 error로 전달된다.
		}
		
		
		double bmi = weight / (height * height / 10000);
		
		String obesity = null;
		
		if(bmi < 18.5) {
			obesity = "저체중";
		} else if(bmi < 24.9) {
			obesity = "정상";
		} else if(bmi < 29.9) {
			obesity = "과체중";
		} else {
			obesity = "비만";
		}
		
		// 실제로 응답할 데이터
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bmi", bmi);
		map.put("obesity", obesity);
		
		// 응답 헤더(Content-Type)
		MultiValueMap<String, String> header = new HttpHeaders(); // MultiValueMap 인터페이스를 구현하는 HttpHeaders
		header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		return new ResponseEntity<Map<String,Object>>(map, header, HttpStatus.OK);
	}
	
	/*
		@Override
		public BmiVO execute1(HttpServletRequest request, HttpServletResponse response) {
			
			
			try {
			
				// bmi = 몸무게(kg) / 키(m) * 키(m)
				double weight = Double.parseDouble(request.getParameter("weight"));
				double height = Double.parseDouble(request.getParameter("height")) / 100;
				
				double bmi = weight / (height * height);
				
				String obesity = null;
				
				if(bmi < 18.5) {
					obesity = "저체중";
				} else if(bmi < 24.9) {
					obesity = "정상";
				} else if(bmi < 29.9) {
					obesity = "과체중";
				} else {
					obesity = "비만";
				}
				return new BmiVO(weight, height, bmi, obesity);
				
			} catch(Exception e) { // Double.parseDouble에서 발생되는 예외
		
				try {
					response.setContentType("text/plain; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("몸무게와 키 입력을 확인하세요.");	// catch문의 응답 :  $.ajax의 error로 넘기는 예외 메시지
					out.flush();
					out.close();
				
				} catch(Exception e2) {
						
					e2.printStackTrace();
				}
					return null;
			}
		}
	
		@Override
		public Map<String, Object> execute2(BmiVO bmiVO) {
			
			double weight = bmiVO.getWeight();
			double height = bmiVO.getHeight() / 100;
			
			double bmi = weight / (height * height);
			String obesity = null;
			
			if(bmi < 18.5) {
				obesity = "저체중";
			} else if(bmi < 24.9) {
				obesity = "정상";
			} else if(bmi < 29.9) {
				obesity = "과체중";
			} else {
				obesity = "비만";
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("bmi", bmi);
			map.put("obesity", obesity);
			
			return map;
		}
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
