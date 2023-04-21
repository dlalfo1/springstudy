package com.gdu.app07.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app07.domain.BoardDTO;

@Repository
public class BoardDAO {	// DAO의 역할은 mapper에게 받은 걸 그대로 Service에 돌려주는 것이다. (반대로 Service에게 받은 걸 mapper에게 전달해준다.)
						// 즉, mapper가 받는 건 DAO도 Service에게 받아야 한다.(mapper의 parameterType과 DAO 메소드의 매개변수 자리가 일치해야 한다는 소리임.)
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;	// 이제 얘가 mapper에있는 쿼리문을 부를거임.
	private final String NS = "mybatis.mapper.board.";

	// mapper가 받는게 없으니(parameterType이 없다.) 당연히 DAO도 받은게 없다.(Service가 준 게 없다.)
	public List<BoardDTO> selectBoardList() {
		// selectList() 메소드 안에는 mapper의 namespace의 id를 호출한다.
		// mybatis.mapper.board.selectBoardList : mapper 파일 아래 selectBoardList태그를 불러오는 것이다.
		return sqlSessionTemplate.selectList(NS + "selectBoardList");
		
	}
	
	public BoardDTO selectBoardByNo(int BoarNo) {
		
		return sqlSessionTemplate.selectOne(NS + "selectBoardByNo", BoarNo);
		
	}
	
	public int insertBoard(BoardDTO board) {
		
		return sqlSessionTemplate.insert(NS + "insertBoard", board);
		
	}
	
	public int updateBoard(BoardDTO board) {
		
		return sqlSessionTemplate.update(NS + "updateBoard", board);
	}
	
	public int deleteBoard(int boardNo) {
		
		return sqlSessionTemplate.delete(NS + "deleteBoard", boardNo);
	}
	
	
	
	
}
                                                                                                                         