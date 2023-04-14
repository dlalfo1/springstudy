package com.gdu.app03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Papago {
	
	private String source;
	private String target;
	private String text;

}
