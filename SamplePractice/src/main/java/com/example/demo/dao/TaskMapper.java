package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.TaskEntity;

/**
 * タスク情報に関するデータアクセス処理を定義するMapper
 */
@Mapper
public interface TaskMapper {

	/**
	 * タスクIDでタスク情報を取得する（SELECT）
	 * @param taskId タスクID
	 * @return TaskEntity タスク情報
	 */
	TaskEntity findByTaskId(Integer taskId);

	/**
	 * 全タスク情報を取得する（SELECT）
	 * @return List<TaskEntity> タスク情報リスト
	 */
	List<TaskEntity> findAll();

	/**
	 * タスク状況で絞り込んでタスク情報を取得する（SELECT）
	 * @param taskStatus タスク状況
	 * @return List<TaskEntity> タスク情報リスト
	 */
	List<TaskEntity> findByStatus(String taskStatus);

	/**
	 * タスク情報を新規登録する（INSERT）
	 * @param task 登録するタスク情報
	 * @return int 登録件数
	 */
	int insertTask(TaskEntity task);

	/**
	 * タスク情報を更新する（UPDATE）
	 * @param task 更新するタスク情報
	 * @return int 更新件数
	 */
	int updateTask(TaskEntity task);

	/**
	 * タスク情報を削除する（DELETE）
	 * @param taskId 削除するタスクID
	 * @return int 削除件数
	 */
	int deleteTask(Integer taskId);
}