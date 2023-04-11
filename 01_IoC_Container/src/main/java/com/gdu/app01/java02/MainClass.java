package com.gdu.app01.java02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.gdu.app01.java01.AppContext;

public class MainClass {

	public static void main(String[] args) {

		// com.gdu.app01.java02 패키지에 있는 Bean을 주세요!
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.gdu.app01.java02"); 
		
		// 이름이 stu인 Bean을 주세요! @Bean 
		Student student = ctx.getBean("stu", Student.class);
		System.out.println("점수: " + student.getScores());
		System.out.println("상: " + student.getAwards());
		System.out.println("연락처: " +student.getContact());
		ctx.close();
		
	}

}
