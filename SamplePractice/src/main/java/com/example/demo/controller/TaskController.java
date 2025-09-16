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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.TaskEntity;
import com.example.demo.service.TaskService;

/**
 * タスク管理のWeb画面用コントローラー
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	/**
	 * タスク一覧画面表示
	 */
	@GetMapping("/list")
	public String showTaskList(Model model) {
		List<TaskEntity> tasks = taskService.getAllTasks();
		model.addAttribute("tasks", tasks);
		return "list";
	}

	/**
	 * タスク新規追加画面表示
	 */
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("task", new TaskEntity());
		return "add";
	}

	/**
	 * タスク新規追加処理
	 */
	@PostMapping("/add")
	public String addTask(@Valid @ModelAttribute TaskEntity task,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {

		// バリデーションエラーがある場合は追加画面に戻る
		if (result.hasErrors()) {
			return "add";
		}

		// タスク登録処理
		boolean success = taskService.createTask(task);

		if (success) {
			redirectAttributes.addFlashAttribute("message", "タスクを正常に登録しました");
			return "redirect:/tasks/list";
		} else {
			model.addAttribute("error", "タスクの登録に失敗しました");
			return "add";
		}
	}

	/**
	 * タスク編集画面表示
	 */
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Integer id, Model model) {
		TaskEntity task = taskService.getTaskById(id);

		if (task == null) {
			return "redirect:/tasks/list";
		}

		model.addAttribute("task", task);
		return "edit";
	}

	/**
	 * タスク更新処理
	 */
	@PostMapping("/edit")
	public String updateTask(@Valid @ModelAttribute TaskEntity task,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {

		// バリデーションエラーがある場合は編集画面に戻る
		if (result.hasErrors()) {
			return "edit";
		}

		// タスク更新処理
		boolean success = taskService.updateTask(task);

		if (success) {
			redirectAttributes.addFlashAttribute("message", "タスクを正常に更新しました");
			return "redirect:/tasks/list";
		} else {
			model.addAttribute("error", "タスクの更新に失敗しました");
			return "edit";
		}
	}

	/**
	 * タスク削除処理
	 */
	@PostMapping("/delete")
	public String deleteTask(@RequestParam Integer taskId,
			RedirectAttributes redirectAttributes) {

		boolean success = taskService.deleteTask(taskId);

		if (success) {
			redirectAttributes.addFlashAttribute("message", "タスクを正常に削除しました");
		} else {
			redirectAttributes.addFlashAttribute("error", "タスクの削除に失敗しました");
		}

		return "redirect:/tasks/list";
	}

	/**
	 * ルートパスから一覧画面にリダイレクト
	 */
	@GetMapping("/")
	public String redirectToList() {
		return "redirect:/tasks/list";
	}
}