package com.example.hotelreservationapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hotelreservationapp.entity.Hotel;
import com.example.hotelreservationapp.service.HotelService;

@Controller
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@GetMapping
	public String getAllHotels(Model model) {
		model.addAttribute("hotels", hotelService.getAllHotels());
		return "hotel-list";
	}

	@GetMapping("/new")
	public String showAddHotelForm(Model model) {
		model.addAttribute("hotel", new Hotel());
		return "hotel-form";
	}

	@PostMapping
	public String addHotel(Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			return "hotel-form";
		}
		try {
			hotelService.saveOrUpdateHotel(hotel);
			redirectAttributes.addFlashAttribute("successMessage", "Hotel added successfully");
			return "redirect:/hotels";
		} catch (DataIntegrityViolationException ex) {
			model.addAttribute("errorMessage", "Enter Unique Data");
			return "hotel-form";
		}
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		Hotel hotel = hotelService.getHotelById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid hotel id: " + id));
		model.addAttribute("hotel", hotel);
		return "hotel-form";
	}

	@PostMapping("/edit/{id}")
	public String updateHotel(@PathVariable Long id, Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			return "hotel-form";
		}
		hotel.setId(id);
		try {
			hotelService.saveOrUpdateHotel(hotel);
			redirectAttributes.addFlashAttribute("successMessage", "Hotel updated successfully");
			return "redirect:/hotels";
		} catch (DataIntegrityViolationException ex) {
			model.addAttribute("errorMessage", "Enter Unique Data");
			return "hotel-form";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		hotelService.deleteHotel(id);
		redirectAttributes.addFlashAttribute("successMessage", "Hotel deleted successfully");
		return "redirect:/hotels";
	}
}
