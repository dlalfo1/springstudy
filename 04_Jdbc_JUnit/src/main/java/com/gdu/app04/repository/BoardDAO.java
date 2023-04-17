package com.gdu.app04.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.app04.domain.BoardDTO;

@Repository	// DAO가 사용하는 @Component
			// Spring Container에 Bean이 등록될 때 Singleton으로 등록되기 때문에 별도의 Singleton Pattern 코드를 작성할 필요가 없다.
public class BoardDAO {
	
	// jdbc 방식 
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	
	// private 메소드 -1 (BoardDAO 클래스 내부에서만 사용)
	private Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");	// ojdbc8.jar 메모리 로드
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GDJ61", "1111");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// private 메소드 -2 (BoardDAO 클래스 내부에서만 사용)
	private void close() {
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(con != null) con.close();
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
			con = getConnection(); 
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
			con = getConnection(); 
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
			con = getConnection(); 
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
			con = getConnection(); 
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
			con = getConnection(); 
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
