package com.gdu.prd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oracle.sql.DATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private int prodNo;
	private String prodName;
	private int prodPrice;
	private DATE prodMadeAt;
}

