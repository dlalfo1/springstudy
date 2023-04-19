package com.gdu.app06.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {		// 게시글 하나 그 자체인 BoardDTO 
							// 필드 변수의 이름들은 은 DB의 칼럼명과 맞춰준다.
	private int board_no;
	private String title;
	private String content;
	private String writer;
	private String created_at;
	private String modified_at;
	
}
