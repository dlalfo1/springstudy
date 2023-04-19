package com.gdu.app06.service;

import java.util.List;

import com.gdu.app06.domain.BoardDTO;

public interface BoardService { // 서비스의 인터페이스로 추상메소드를 사용한다.

	// 게시글 목록 보기
	public List<BoardDTO> getBoardList();
	
	// 게시글 등록하기
	public int addBoardList(BoardDTO board);
	
	// 게시글 상세보기
	public BoardDTO getBoardByNo(int board_no);
	
	// 게시글 삭제하기
	public int removeBoard(int board_no);
	
	// 게시글 수정하기
	public int modifyBoard(BoardDTO board);
	
	public void testTx();
}
