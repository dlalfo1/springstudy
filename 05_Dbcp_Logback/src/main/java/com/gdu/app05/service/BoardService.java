package com.gdu.app05.service;

import java.util.List;

import com.gdu.app05.domain.BoardDTO;



public interface BoardService {
	
	// 목록, 추가, 상세보기, 삭제,  수정
	
	public List<BoardDTO> getBoardList();
	public int addBoard(BoardDTO board);
	public BoardDTO getBoardByNo(int board_no);
	public int remove(int board_no);
	public int modify(BoardDTO board);
}
