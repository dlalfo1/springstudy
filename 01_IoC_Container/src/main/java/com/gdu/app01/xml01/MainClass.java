package com.gdu.app01.xml01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {	
		
	/*
	  	<bean> 태그로 생성한 Bean을 가져올 때 사용하는 스프링 클래스
	  	1. GenericXmlApplicationContext 클래스
	  	2. ClassPathXmlApplicationContext 클래스
	  	위 클래스 중 아무거나 사용하면 된다.
	 */

	// src/main/resources/xml01 디렉터리에 있는 app-context.xml 파일에 정의된 Bean을 쓸게요!
	AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml01/app-context.xml"); // src/main/resources는 명시하지 않습니다.
	
	// Bean 중에서 student란 id를 가진 Bean을 주세요!
	Student haksaeng = ctx.getBean("student", Student.class); // (Student)ctx.getBean("student") 코드와 동일하다.
	                                                          // getBean()을 통해 가져온 student객체의 타입이 Student라는 뜻이다.
	
	// 근데 여기서 Calculator는 객체로 만들어주지 않았는데 어케 사용가능?
	// haksaeng의 calculator를 이용한 메소드를 호출합니다.
	haksaeng.getCalculator().add(5, 2);
	haksaeng.getCalculator().sub(5, 2);
	haksaeng.getCalculator().mul(5, 2);
	haksaeng.getCalculator().div(5, 2);
		
	// 사용한 자원 반납합니다.! (생략 가능합니다.)
	ctx.close();
		
	}

}
