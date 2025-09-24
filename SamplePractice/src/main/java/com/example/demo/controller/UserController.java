package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.UserForm;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.ValidationUtil;

/**
 * ユーザーコントローラー（課題1-1のファイル検索対象）
 */
@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * ユーザー一覧表示
	 * 課題2-1：ここにSystem.out.printlnを追加
	 */
	@GetMapping("/list")
	public String showUserList(Model model) {
		// 課題2-1：この行の下にsysoutでログ追加

		List<UserEntity> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);
		model.addAttribute("totalCount", userList.size());

		return "user/list";
	}

	/**
	 * ユーザー詳細表示
	 * 課題5-1：デバッグ用ブレークポイント設定箇所
	 */
	@GetMapping("/detail/{id}")
	public String showUserDetail(@PathVariable Long id, Model model) {
		// 課題5-1：ここにブレークポイントを設定してid変数を確認
		// 課題5-2：条件付きブレークポイント（id == 2の場合のみ停止）

		UserEntity user = userService.findById(id);
		model.addAttribute("user", user);

		// 課題2-2：user.getName()を使ってコンソール出力を追加

		return "user/detail";
	}

	/**
	 * ユーザー登録画面表示
	 */
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "user/register";
	}

	/**
	 * ユーザー登録処理
	 * 課題1-2のテキスト検索対象メッセージを含む
	 */
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute UserForm userForm,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes) {

		System.out.println("ユーザー登録処理開始");

		// バリデーションエラーチェック
		if (bindingResult.hasErrors()) {
			System.out.println("バリデーションエラーが発生しました");
			model.addAttribute("userForm", userForm);
			return "user/register";
		}

		// UserFormからUserEntityへ変換
		UserEntity user = convertFormToEntity(userForm);

		// ValidationUtilを使った最終チェック
		if (!ValidationUtil.isValidUser(user)) {
			bindingResult.reject("validation.error", "入力内容に問題があります");
			model.addAttribute("userForm", userForm);
			return "user/register";
		}

		userService.registerUser(user);

		// 課題1-2：この文字列を検索で見つける
		System.out.println("ユーザー登録が完了しました");

		redirectAttributes.addFlashAttribute("successMessage",
				"ユーザー「" + user.getName() + "」を登録しました");

		return "redirect:/users/list";
	}

	/**
	 * ユーザー編集画面表示  
	 */
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		UserEntity user = userService.findById(id);

		if (user == null) {
			model.addAttribute("errorMessage", "指定されたユーザーが見つかりません");
			return "redirect:/users/list";
		}

		// UserEntityからUserFormへ変換
		UserForm userForm = convertEntityToForm(user);
		model.addAttribute("userForm", userForm);

		return "user/edit";
	}

	/**
	 * ユーザー更新処理
	 */
	@PostMapping("/edit")
	public String editUser(@Valid @ModelAttribute UserForm userForm,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes) {

		System.out.println("ユーザー更新処理開始 - ID: " + userForm.getId());

		// バリデーションエラーチェック
		if (bindingResult.hasErrors()) {
			System.out.println("バリデーションエラーが発生しました");
			model.addAttribute("userForm", userForm);
			return "user/edit";
		}

		// UserFormからUserEntityへ変換
		UserEntity user = convertFormToEntity(userForm);
		user.setId(userForm.getId());

		// ValidationUtilを使った最終チェック
		if (!ValidationUtil.isValidUser(user)) {
			bindingResult.reject("validation.error", "入力内容に問題があります");
			model.addAttribute("userForm", userForm);
			return "user/edit";
		}

		userService.updateUser(user);

		System.out.println("ユーザー更新が完了しました - ID: " + user.getId());

		redirectAttributes.addFlashAttribute("successMessage",
				"ユーザー「" + user.getName() + "」を更新しました");

		return "redirect:/users/list";
	}

	/**
	 * ユーザー削除処理
	 */
	@PostMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		System.out.println("ユーザー削除処理開始 - ID: " + id);

		UserEntity user = userService.findById(id);
		if (user == null) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"指定されたユーザーが見つかりません");
			return "redirect:/users/list";
		}

		String userName = user.getName();
		userService.deleteUser(id);

		System.out.println("ユーザー削除が完了しました - " + userName);

		redirectAttributes.addFlashAttribute("successMessage",
				"ユーザー「" + userName + "」を削除しました");

		return "redirect:/users/list";
	}

	/**
	 * UserFormからUserEntityへ変換
	 */
	private UserEntity convertFormToEntity(UserForm form) {
		UserEntity entity = new UserEntity();
		entity.setName(form.getName());
		entity.setKana(form.getKana());
		entity.setEmail(form.getEmail());
		entity.setDepartment(form.getDepartment());
		entity.setBirthDate(form.getBirthDate());
		entity.setHireDate(form.getHireDate());
		return entity;
	}

	/**
	 * UserEntityからUserFormへ変換
	 */
	private UserForm convertEntityToForm(UserEntity entity) {
		UserForm form = new UserForm();
		form.setId(entity.getId());
		form.setName(entity.getName());
		form.setKana(entity.getKana());
		form.setEmail(entity.getEmail());
		form.setDepartment(entity.getDepartment());
		form.setBirthDate(entity.getBirthDate());
		form.setHireDate(entity.getHireDate());
		return form;
	}
}
