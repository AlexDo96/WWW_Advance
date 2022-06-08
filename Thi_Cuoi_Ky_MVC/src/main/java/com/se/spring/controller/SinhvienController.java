package com.se.spring.controller;

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
import com.se.spring.model.Sinhvien;
import com.se.spring.service.LopService;
import com.se.spring.service.SinhvienService;

@Controller
@RequestMapping("/sinhvien") // Các trang bắt đầu bằng /sinhvien
public class SinhvienController {
	// Tự động nhúng các Bean
	// Nó sẽ tìm kiếm bean có tên là SinhvienDao trong container của nó ,sau đó
	// nhúng (hoặc tiêm) vào lớp SinhvienServiceImpl.
	// Đây chính là cơ chế DI (dependency injection).
	@Autowired
	private SinhvienService sinhvienService;

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

	// Cấu hình request save Sinhvien
	// Cấu hình annotation @Valid @ModelAttribute ràng buộc dữ liệu khi Save
	@PostMapping("/saveSinhvien*")
	public String saveSinhvien(@Valid @ModelAttribute("sinhvien") Sinhvien sinhvien, @RequestParam("lopId") int lopId,
			BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "sinhvien-form";
		} else {
			// save sinhvien using our service
			sinhvienService.saveSinhvien(sinhvien, lopId);
			return "redirect:/sinhvien/list?lopId=" + lopId;
		}
	}

	// Cấu hình request update Sinhvien
	// Cấu hình annotation @Valid @ModelAttribute ràng buộc dữ liệu khi Save
	@PostMapping("/updateSinhvien*")
	public String updateSinhvien(@Valid @ModelAttribute("sinhvien") Sinhvien sinhvien, @RequestParam("lopId") int lopId,
			BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "sinhvien-form";
		} else {
			// save sinhvien using our service
			sinhvienService.updateSinhvien(sinhvien, lopId);
			return "redirect:/sinhvien/list?lopId=" + lopId;
		}
	}

	// Cấu hình trang Add Sinhvien
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel, @RequestParam("lopId") int lopId) {
		// create model attribute to bind form data
		Sinhvien sinhvien = new Sinhvien();
		theModel.addAttribute("sinhvien", sinhvien);
		theModel.addAttribute("lopId", lopId);
		theModel.addAttribute("isUpdate", false);
		return "sinhvien-form";
	}

	// Cấu hình trang Update Sinhvien
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("lopId") int lopId, @RequestParam("sinhvienId") int sinhvienId,
			Model theModel) {
		// get sinhvien from our service
		Sinhvien sinhvien = sinhvienService.getSinhvien(sinhvienId);
		// set sinhvien as a model attribute to pre-populate the form
		theModel.addAttribute("sinhvien", sinhvien);
		theModel.addAttribute("lopId", lopId);
		theModel.addAttribute("isUpdate", true);

		// send over to our form
		return "sinhvien-form";
	}

	// Cấu hình request delete Sinhvien
	@GetMapping("/delete")
	public String deleteSinhvien(@RequestParam("sinhvienId") int sinhvienId, int lopId) {
		// delete the sinhvien
		sinhvienService.deleteSinhvien(sinhvienId);
		return "redirect:/sinhvien/list?lopId=" + lopId;
	}

	// Cấu hình trang hiển thị list Sinhvien
	@GetMapping("/list")
	public String listSinhvien(@RequestParam("lopId") int lopId, Model theModel) {
		// get lops from the service
		Lop lop = lopService.getLop(lopId);
		// add the lop to the model
		theModel.addAttribute("lop", lop);
		return "sinhvien-list";
	}

}
