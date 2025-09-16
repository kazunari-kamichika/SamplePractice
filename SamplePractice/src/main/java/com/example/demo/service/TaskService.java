package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TaskMapper;
import com.example.demo.entity.TaskEntity;

/**
 * タスク情報に関するビジネスロジックを処理するサービスクラス
 */
@Service
public class TaskService {

	@Autowired
	private TaskMapper taskMapper;

	/**
	 * タスクIDでタスク情報を取得する
	 * @param taskId タスクID
	 * @return TaskEntity タスク情報
	 */
	public TaskEntity getTaskById(Integer taskId) {
		return taskMapper.findByTaskId(taskId);
	}

	/**
	 * 全タスク情報を取得する
	 * @return List<TaskEntity> タスク情報リスト
	 */
	public List<TaskEntity> getAllTasks() {
		return taskMapper.findAll();
	}

	/**
	 * タスク状況で絞り込んでタスク情報を取得する
	 * @param taskStatus タスク状況
	 * @return List<TaskEntity> タスク情報リスト
	 */
	public List<TaskEntity> getTasksByStatus(String taskStatus) {
		return taskMapper.findByStatus(taskStatus);
	}

	/**
	 * タスク情報を新規登録する
	 * @param task 登録するタスク情報
	 * @return boolean 登録成功の場合true
	 */
	public boolean createTask(TaskEntity task) {
		// 簡単なバリデーション
		if (task.getTaskName() == null || task.getTaskName().isEmpty()) {
			return false;
		}
		if (task.getTaskStatus() == null || task.getTaskStatus().isEmpty()) {
			return false;
		}

		// 作成日が未設定の場合は現在日付を設定
		if (task.getStartDate() == null) {
			task.setStartDate(LocalDate.now());
		}

		int result = taskMapper.insertTask(task);
		return result > 0;
	}

	/**
	 * タスク情報を更新する
	 * @param task 更新するタスク情報
	 * @return boolean 更新成功の場合true
	 */
	public boolean updateTask(TaskEntity task) {
		// タスクが存在するかチェック
		TaskEntity existingTask = taskMapper.findByTaskId(task.getTaskId());
		if (existingTask == null) {
			return false;
		}

		// タスク完了時に完了日を自動設定
		if ("完了".equals(task.getTaskStatus()) && task.getEndDate() == null) {
			task.setEndDate(LocalDate.now());
		}

		int result = taskMapper.updateTask(task);
		return result > 0;
	}

	/**
	 * タスク情報を削除する
	 * @param taskId 削除するタスクID
	 * @return boolean 削除成功の場合true
	 */
	public boolean deleteTask(Integer taskId) {
		// タスクが存在するかチェック
		TaskEntity existingTask = taskMapper.findByTaskId(taskId);
		if (existingTask == null) {
			return false;
		}

		int result = taskMapper.deleteTask(taskId);
		return result > 0;
	}
}
