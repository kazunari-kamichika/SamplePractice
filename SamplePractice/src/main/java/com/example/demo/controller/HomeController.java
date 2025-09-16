package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ホーム画面用コントローラー
 */
@Controller
public class HomeController {

	/**
	 * ルートパス（/）から一覧画面にリダイレクト
	 */
	@GetMapping("/")
	public String home() {
		return "redirect:/tasks/list";
	}

}
