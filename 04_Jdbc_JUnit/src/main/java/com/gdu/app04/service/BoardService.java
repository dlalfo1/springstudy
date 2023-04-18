package com.gdu.app04.service;

import java.util.List;

import com.gdu.app04.domain.BoardDTO;

public interface BoardService {
	
	// 서비스 계층의 메소드명은 가급적 "사용자 친화적"으로 작성하자.
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardByNo(int board_no); // 하나 골라서 올거니까 매개변수로 번호 받아오기
	public int addBoard(BoardDTO board);	    // 추가하는 쿼리문 돌린 후 반환받는 건 1 or 0이다. (int 타입)
	public int modifyBoard(BoardDTO board);		// 수정하는 쿼리문 돌린 후 반환받는 건 1 or 0이다. (int 타입)
	public int removeBoard(int board_no);		// 삭제하는 쿼리문 돌린 후 반환받는 건 1 or 0이다. (int 타입)
											    // 삭제할 때도 번호만 있으면 되니까 매개변수로 번호 받아오기

}
