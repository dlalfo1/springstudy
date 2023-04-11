package com.gdu.app01.java02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext { // 여기서 클래스의 이름 AppContext은 아무 상관도 없다.
	
	// 임의의 학생 만들고, MainClass에서 확인하기.
	@Bean
	public Student stu() {	// <bean id="stu" class="Student">
		
		// 0~100 난수 5개
		List<Integer> scores = new ArrayList<Integer>();
		for(int cnt = 0; cnt < 5; cnt++) {				// 5번 반복합니다. (인덱스는 필요없다. 그래서 변수명을 cnt로 잡음)
			scores.add( (int)(Math.random() * 101) );	// 0부터 101개의 난수가 발생된다. (0 ~ 100)
		}
		
		 // 상 3개
		Set<String> awards = new HashSet<String>();
		awards.add("개근상");
		awards.add("장려상");
		awards.add("참가상");
		
		// address, tel
		Map<String, String> contacts = new HashMap<String, String>();
		contacts.put("address", "seoul");
		contacts.put("tel", "02-1234-5678");
		
		// Bean 생성 및 반환
		Student student = new Student();
		student.setScores(scores);
		student.setAwards(awards);
		student.setContact(contacts);
		
		return student;

	}
	

}
