package com.gdu.app07.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gdu.app07.domain.BoardDTO;

public interface BoardService {

	// Service는 request를 받아서 그대로 넘기기만 할 것이고 처리는 Controller가 할 것이다.
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardByNo(HttpServletRequest request);
	public int addBoard(HttpServletRequest request);
	public int modifyBoard(HttpServletRequest request);
	public int removeBoard(HttpServletRequest request);
	public void testTx();
}
