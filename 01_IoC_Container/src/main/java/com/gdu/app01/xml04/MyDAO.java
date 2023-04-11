 package com.gdu.app01.xml04;

import java.sql.Connection;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MyDAO {

	// field
	private Connection con;
	
	 
	/*
	    singleton pattern - app-context.xml에서 <bean> 태그를 만들 때 사용된다.
	    singleton pattern 작업은 app-context파일에서 bean 객체 만들 때 scope="singleton" 태그로 이미 완성된다. 
	    그러므로 자바에선 싱글톤패턴 코드가사라진다.
	
		private static MyDAO dao = new MyDAO(); // new 객체 생성 막아두기
		private MyDAO() { }						// 생성자 막아두기
		public static MyDAO getInstance() {     // dao를 반환해주는 getInstance() 메소드
			return dao;
		}
	 */
	
	// method
	public Connection getConnection() {
		
		// Spring Container에 만들어 둔 myConn Bean 가져오기
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml04/app-context.xml");
		con = ctx.getBean("myConn", MyConnection.class).getConnection();
		ctx.close();
		return con;
		
	}
	
	public void close () {
		
		try {
			if(con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void list() {
		
		con = getConnection();
		
		close();
		
	}
}
