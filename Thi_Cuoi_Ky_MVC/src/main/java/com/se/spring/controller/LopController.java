package com.se.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.se.spring.model.Lop;
import com.se.spring.service.LopService;

@Controller
@RequestMapping("/lop") // Các trang bắt đầu bằng /lop
public class LopController {
	// Tự động nhúng các Bean
	// Nó sẽ tìm kiếm bean có tên là LopDao trong container của nó ,sau đó
	// nhúng (hoặc tiêm) vào lớp LopServiceImpl.
	// Đây chính là cơ chế DI (dependency injection).
	@Autowired
	private LopService lopService;

	// Cấu hình cơ chế kiểm tra validation data
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// Cấu hình trang hiển thị list Lop
	@GetMapping("/list")
	public String listLop(Model model) {
		List<Lop> lopList = lopService.getLops();
		model.addAttribute("lops", lopList);
		return "lop-list";
	}

	// Cấu hình request save Lop
	// Cấu hình annotation @Valid @ModelAttribute ràng buộc dữ liệu khi Save
	@PostMapping("/saveLop")
	public String saveLop(@Valid @ModelAttribute("lop") Lop lop, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "lop-form";
		} else {
			lopService.saveLop(lop);
			return "redirect:/lop/list";
		}
	}

	// Cấu hình trang Update Lop
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("lopId") int theId, Model theModel) {
		Lop lop = lopService.getLop(theId);
		theModel.addAttribute("lop", lop);
		return "lop-form";
	}

	// Cấu hình trang Add Lop
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// create model attribute to bind form data
		Lop lop = new Lop();
		theModel.addAttribute("lop", lop);
		return "lop-form";
	}

	// Cấu hình request delete Lop
	@GetMapping("/delete")
	public String deleteLop(@RequestParam("lopId") int theId) {
		lopService.deleteLop(theId);
		return "redirect:/lop/list";
	}

}
