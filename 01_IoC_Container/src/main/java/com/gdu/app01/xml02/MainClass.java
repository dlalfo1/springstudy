package com.gdu.app01.xml02;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {	
		
 
	AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml02/app-context.xml"); 
	


	Academy academy = ctx.getBean("academy", Academy.class); 
	
	System.out.println("이름: " + academy.getName());
	System.out.println("도로명주소: " + academy.getAddress().getDoronameAddress());
	System.out.println("지번주소: " + academy.getAddress().getJibunAddress());
	System.out.println("전화번호: " + academy.getAddress().getContact().getCallNo());
	System.out.println("팩스번호: " + academy.getAddress().getContact().getFaxNo());
	
	ctx.close();
	}
}
