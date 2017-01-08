package com.aring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aring.service.MovieService;

@Controller
public class IndexController {

	@Autowired
	private MovieService movieService;
	
	
	@RequestMapping("/")
	public String index(){
		System.out.println(movieService.getMovieById(50));
		return "index";
	}
}
