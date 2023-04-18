package com.gdu.app05.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.gdu.app05.domain.BoardDTO;


//	@repository 대신 AppConfig에 @Bean이 등록되어 있다.
public class BoardDAO {
	
	// dbcp 방식 (jdbc + DataSource)
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private DataSource dataSource;
	
	// BoardDAO 생성자 (webapp/META-INF/cotext.xml에 작성한 <Resource> 태그 읽기)
	public BoardDAO() {
		// JNDI 방식 : <Resource> 태그의 name 속성으로 Resource를 읽어 오는 방식
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/GDJ61");
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// private 메소드 -2 (BoardDAO 클래스 내부에서만 사용)
	private void close() {
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(con != null) con.close();	// 사용한 Connection을 dataSource에게 반납한다.
			} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	// DAO 메소드 (BoardServiceImpl 클래스에서 사용하는 메소드)
	// DAO 메소드명
	// 방법1. BoardServiceImpl의 메소드와 이름을 맞춤.
	// 방법2. DB 친화적으로 새 이름을 부여 (이론상 권장)
	
	// 1. 목록
	public List<BoardDTO> selectBoardList() {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		try {
			con = dataSource.getConnection(); // dataSource가 관리하는 Connection 8개 중 하나를 대여한다.
			sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD ORDER BY BOARD_NO DESC";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery(); // 셀렉트 전용 메소드 : executeQuery(); , 셀렉트 전용 반환값 : rs
			while(rs.next()) {
				BoardDTO board = new BoardDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				list.add(board);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	
	// 2. 상세
	public BoardDTO selectBoardByNo(int board_no) {
		BoardDTO board = null;
		try {
			con = dataSource.getConnection(); 
			sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD WHERE BOARD_NO = ?"; // 변수값 직접 넣지 말자.(보안상 안 좋다.) 물음표 처리하기.
			ps = con.prepareStatement(sql);
			ps.setInt(1, board_no);	// 매개변수로 받는 board_no를 sql 쿼리문 첫번째 물음표(?)에 넣어라.
			rs = ps.executeQuery();
			if(rs.next()) { // 결과가 있다면
				board = new BoardDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return board;
	}
	
	
	// 3. 삽입
	public int insertBoard(BoardDTO board) {
		int result = 0;
		try {
			con = dataSource.getConnection(); 
			sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
			ps = con.prepareStatement(sql);
			ps.setNString(1, board.getTitle());
			ps.setNString(2, board.getContent());
			ps.setNString(3, board.getWriter());
			result = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	
	// 4. 수정
	public int updateBoard(BoardDTO board) {
		int result = 0;
		try {
			con = dataSource.getConnection(); 
			sql = "UPDATE BOARD SET TITLE = ?, CONTENT = ?, MODIFIED_AT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE BOARD_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getBoard_no());
			result = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	
	// 5. 삭제
	public int deleteBoard(int board_no) {
		int result = 0;
		try {
			con = dataSource.getConnection(); 
			sql = "DELETE FROM BOARD WHERE BOARD_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, board_no);
			result = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	

}
