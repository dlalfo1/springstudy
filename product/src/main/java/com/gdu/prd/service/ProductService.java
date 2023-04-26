package com.gdu.prd.service;

import org.springframework.ui.Model;

import com.gdu.prd.domain.ProductDTO;


public interface ProductService {
	
	// 리스트엔 사실 반환할 값들이 많다. 그렇기 때문에 void처리 하고 서비스에서 직접 값을 반환할거임.(model 사용해서 한다든가,,)
	public void loadProductList(Model model);
	public int addProduct(ProductDTO productDTO);
	public void loadProduct(int prodNo, Model model);
	public int modifyProduct(ProductDTO productDTO);
	public int deleteProduct(int prodNo);

}
