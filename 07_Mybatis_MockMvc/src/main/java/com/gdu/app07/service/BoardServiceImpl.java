package com.gdu.app07.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.app07.domain.BoardDTO;
import com.gdu.app07.repository.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public List<BoardDTO> getBoardList() { 

		return boardDAO.selectBoardList();
	}

	@Override
	public BoardDTO getBoardByNo(HttpServletRequest request) {
		// 파라미터 boardNo가 없으면(null, "") 0을 사용한다.
		// Optional은 "" 빈문자열을 처리하지 못 한다. 오직 null처리만 가능하다.
		// "" 빈문자열까지 처리하려면 고전방식을 쓰자. if문 사용.
		// 만약에 넘어온 파라미터가 숫자가 아닌것까지 막고 싶다면 jsp에서 자바스크립트로 막기(isNaN : 숫자가 아니면)
		// 파라미터는 문자열로 넘어오기때문에 String타입으로 받는다.
		String strBoardNo = request.getParameter("boardNo");
		int boardNo = 0;
		if(strBoardNo != null && strBoardNo.isEmpty() == false) { // strBoardNo null이 아니거나 비어있지 않다면.
			boardNo = Integer.parseInt(strBoardNo);
		}
		return boardDAO.selectBoardByNo(boardNo); //  int타입으로 변환된 boardNo 아니면 초기값인 0이 전달된다.
	}

	@Override
	public int addBoard(HttpServletRequest request) {
		try { // DB테이블의 NOT NULL 칼럼의 파라미터가 넘어오지 않았을경우 예외가 발생. try-catch문 사용
			// 파라미터 : title, content, writer를 받아온다.
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			// BoardDAO로 전달할 BoardDTO를 만든다.
			BoardDTO board = new BoardDTO();
			board.setTitle(title);
			board.setContent(content);
			board.setWriter(writer);
	
			return boardDAO.insertBoard(board);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int modifyBoard(HttpServletRequest request) {
		try {
			
		// 파라미터 : title, content, boardNo (writer는 수정불가, 수정일은 SYSDATE가 자동으로 날짜를 넣어주기 때문에 신경쓸 필요없다.)
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		// 수정은 POST요청이기 때문에 title, content, boardNo 이 파라미터를이 안 넘어올 이유가 없다.
		// 그렇기 때문에 boardNo가 전달되지 않았을 때를 고려해서 코드를 짤 필요가 없다.
		// 파라미터가 안 넘어왔다? 그건 개발이 잘못된것이다.
	
		BoardDTO board = new BoardDTO();
		board.setTitle(title);
		board.setContent(content);
		board.setBoardNo(boardNo);
		
		return boardDAO.updateBoard(board);
		
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int removeBoard(HttpServletRequest request) {
		try {
			// 파라미터 : boardNo
			// 삭제 요청을 GET방식으로 할 경우 권한이 없는 사용자가 주소창에 입력함으로써 정보가 삭제될 수 있다.
			// 보안상의 이유로 삭제요청은 POST방식으로 진행한다.
			// 그러니 boardNo 또한 값이 없을 경우를 대비해 코드를 짜지 않아도 된다.
			
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			
			return boardDAO.deleteBoard(boardNo);
		} catch (Exception e) {
			return 0;
		}
	}
	
	// 트랜잭션 확인
	@Transactional // insert, update, delete를 두개 이상 사용할 때 붙인다.
				   // AOP코드 없이도 쌉가능
				   // AOP같은 경우엔 예를들어 *.TX 이것처럼 모든 메소드를 트랜잭션 처리하고 싶을 때 사용하는건데
	 			   // @Transactional 그냥 이게 깔끔할듯.
	@Override
	public void testTx() {
		boardDAO.insertBoard(new BoardDTO(0, "타이틀", "콘텐트", "작성자", null, null));
		boardDAO.insertBoard(new BoardDTO());
	}

}
