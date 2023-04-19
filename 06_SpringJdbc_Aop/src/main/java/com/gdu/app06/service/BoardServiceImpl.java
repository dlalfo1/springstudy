package com.gdu.app06.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.app06.domain.BoardDTO;
import com.gdu.app06.repository.BoardDAO;

@Service // BoardServiceImpl bean을 스프링컨테이너에 저장하기 위한 애너테이션
public class BoardServiceImpl implements BoardService {

	@Autowired // BoardDAO 객체를 주입하기 위한 애너테이션
	private BoardDAO boardDAO;
	
	@Override
	public List<BoardDTO> getBoardList() {
		// 별도로 List 선언해서 담아줄 필요 없다. 바로 객체를 return 해주면 된다.
		return boardDAO.selectBoardList();
	}

	@Override
	public int addBoardList(BoardDTO board) {
		
		return boardDAO.insertBoard(board);
	}

	@Override
	public BoardDTO getBoardByNo(int board_no) {
		
		return boardDAO.getBoardByNo(board_no);
	}

	@Override
	public int removeBoard(int board_no) {
		return boardDAO.deleteBoard(board_no);
	}

	@Override
	public int modifyBoard(BoardDTO board) { // 수정한 결과값은 int타입이지만 수정하기 위해서 받아야할건 BoardDTO 객체이다.
		
		return boardDAO.updateBoard(board);
	}
	
	// AOP를 활용한 트랜잭션 처리 테스트
	@Override
	public void testTx() {
		
		// 2개 이상의 삽입, 수정, 삭제가 하나의 서비스에서 진행되는 경우에 트랜잭션 처리가 필요하다.
		
		// 성공하는 작업
		boardDAO.insertBoard(new BoardDTO(0, "트랜잭션제목", "트랜잭션내용", "트랜잭션작성자", null, null));	// ROLLBACK 
		
		// 실패하는 작업
		boardDAO.insertBoard(new BoardDTO());	// TITLE 칼럼은 NOT NULL이기 때문에 Exception이 발생한다.
		
		// 트랜잭션처리가 정상적으로 된다면 모든 insert가 실패해야 한다.
		
	}

}
