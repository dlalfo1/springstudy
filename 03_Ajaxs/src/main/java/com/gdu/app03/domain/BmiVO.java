package com.gdu.app03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BmiVO {
	
	private double weight;
	private double height;
	private double bmi;
	private String obesity;
}
