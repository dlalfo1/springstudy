package com.gdu.app06.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.gdu.app06.domain.BoardDTO;

@Repository // BoardDAO bean을 스프링컨테이너에 저장하기 위한 애너테이션
public class BoardDAO {		// DB와 연결하여 쿼리문을 실행시킬 BoardDAO
		
	@Autowired
	private JdbcTemplate jdbcTemplate;	// Connection, PrepareStatement, ResultSet을 사용하는 스프링 클래스
	private String sql;			
	
	
	// 서비스에서 사용할 메소드를 만들어 준다.
	
	// 1. 목록보기 (list)
	public List<BoardDTO> selectBoardList() {
		sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD ORDER BY BOARD_NO DESC";
		List<BoardDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BoardDTO.class));
		
		return list;
	}
	
	// 2. 삽입하기 (add)
	public int insertBoard(final BoardDTO BOARD) {
		sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
		int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {	
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, BOARD.getTitle());
				ps.setString(2, BOARD.getContent());
				ps.setString(3, BOARD.getWriter());
			}
		});
		
		return result;
	}
	
	// 3. 상세보기(detail)
	public BoardDTO getBoardByNo(final int BOARD_NO) {	// 매개변수의 변조를 막기 위해서 final 처리한다. (예전에 매개변수 해킹시도가 있었다.)
		sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD WHERE BOARD_NO = ?";
		BoardDTO board = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BoardDTO.class), BOARD_NO);
		
		return board;
	}
	
	// 4. 삭제하기(remove)
	public int deleteBoard(final int BOARD_NO) {
		sql = "DELETE FROM BOARD WHERE BOARD_NO = ?";
		int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {	
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, BOARD_NO);
			}
		});
		
		return result;
	}
	
	// 5. 수정하기(modify)
	public int updateBoard(final BoardDTO BOARD) {
		sql = "UPDATE BOARD SET TITLE = ?, CONTENT = ?, MODIFIED_AT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE BOARD_NO = ?";
		int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {	
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, BOARD.getTitle());
				ps.setString(2, BOARD.getContent());
				ps.setInt(3, BOARD.getBoard_no());
			}
		});
		
		return result;
	}

}
