package com.gdu.prd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prd.domain.ProductDTO;
import com.gdu.prd.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/list.do")
	public String list(Model model) {	// model에 담아줄 속성이 많을 경우에 컨트롤러는 단순히 model을 서비스로 넘기는 역할을 하고,
										// 서비스에서 model.addAttribute() 를 사용하여 값을 넘겨준다.
		productService.loadProductList(model);
		return "product/list";
	}
	
	@PostMapping("/add.do")
	// 파라미터를 받을 때 request를 사용하지 않을거라면 남은 방법은 @RequestParam, ProductDTO 두가지다.
	// 값을 보낼 땐 redirect이동시 값을 가지고가는 RedirectAttributes 사용.
	// 결론 forward이동할 땐 Model사용, redirect 이동할 땐 RedirectAttributes 사용
	public String add(ProductDTO productDTO, RedirectAttributes redirectAttributes) { // 이걸 나눠서 받을 수도 있다.
			
		int addResult = productService.addProduct(productDTO);
		redirectAttributes.addFlashAttribute("addResult", addResult); // 실제로 list.jsp로 이 속성값이 넘어간다. 
		return "redirect:/product/list.do";
	}
	
	@GetMapping("/detail.do")
	public String detail(@RequestParam(value="prodNo", required = false, defaultValue = "0") int prodNo, Model model) {
		productService.loadProduct(prodNo, model);
		return "product/detail";
	}
	
	@PostMapping("/edit.do")
	public String edit(ProductDTO product) {	// 왕 중요! 객체로 파라미터를 받으면 Model을 사용하지 않아도 자동으로 forward된다.
		return "product/edit";
	}

}
