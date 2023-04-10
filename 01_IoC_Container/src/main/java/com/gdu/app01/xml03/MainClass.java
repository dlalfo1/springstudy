package com.gdu.app01.xml03;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml03/app-context.xml");
		
		Board board = ctx.getBean("board", Board.class);
		System.out.println("제목 : " + board.getTitle());
		System.out.println("제목 : " + board.getContent());
		System.out.println("작성자ID : " + board.getWriter().getId());
		System.out.println("작성자ID : " + board.getWriter().getName());
		
		ctx.close();
	}

}
