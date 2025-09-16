package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dao.TaskMapper;
import com.example.demo.entity.TaskEntity;

/**
 * TaskServiceの単体テスト
 * 
 * 【テストの基本】
 * 1. @Mock でダミーオブジェクト（偽物）を作る
 * 2. when().thenReturn() で偽物の動作を決める
 * 3. 実際にメソッドを呼び出す
 * 4. assertEquals() で結果をチェック
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

	@Mock
	private TaskMapper taskMapper; // 偽物のMapper

	@InjectMocks
	private TaskService taskService; // テスト対象（本物）

	private TaskEntity testTask;

	@BeforeEach
	void setUp() {
		// テスト用のデータを準備
		// 共通テストデータ: ID=1, 名前="テストタスク", ステータス="未着手", 開始日=2025-9-10
		testTask = new TaskEntity();
		testTask.setTaskId(1);
		testTask.setTaskName("テストタスク");
		testTask.setTaskStatus("未着手");
		testTask.setStartDate(LocalDate.of(2025, 9, 10));
	}

	// ==================== 1. タスク取得のテスト ====================

	@Test
	void testGetTaskById_Success() {
		// 【準備】偽物Mapperの動作を決める
		// 設定: ID=1で検索時にtestTaskを返す
		when(taskMapper.findByTaskId(1)).thenReturn(testTask);

		// 【実行】実際にメソッドを呼び出す
		TaskEntity result = taskService.getTaskById(1);

		// 【検証】結果をチェック
		assertNotNull(result); // 結果がnullでない
		assertEquals(1, result.getTaskId()); // IDが1
		assertEquals("テストタスク", result.getTaskName()); // 名前が"テストタスク"
	}

	@Test
	void testGetTaskById_NotFound() {
		// 【準備】偽物Mapperが null を返すように設定
		// 設定: 存在しないID=999で検索時にnullを返す
		when(taskMapper.findByTaskId(999)).thenReturn(null);

		// 【実行】
		TaskEntity result = taskService.getTaskById(999);

		// 【検証】
		assertNull(result); // 結果がnull（存在しないタスク）
	}

	// ==================== 2. 全タスク取得のテスト ====================

	@Test
	void testGetAllTasks_Success() {
		// 【準備】テスト用のタスクリストを作成
		// 設定: task1=ID1/タスク1/未着手、task2=ID2/タスク2/進行中
		TaskEntity task1 = new TaskEntity(1, "タスク1", "未着手", LocalDate.now(), null);
		TaskEntity task2 = new TaskEntity(2, "タスク2", "進行中", LocalDate.now(), null);
		List<TaskEntity> taskList = Arrays.asList(task1, task2);

		when(taskMapper.findAll()).thenReturn(taskList);

		// 【実行】
		List<TaskEntity> result = taskService.getAllTasks();

		// 【検証】
		assertNotNull(result); // 結果がnullでない
		assertEquals(2, result.size()); // 件数が2件
		assertEquals("タスク1", result.get(0).getTaskName()); // 1件目の名前が"タスク1"
		assertEquals("タスク2", result.get(1).getTaskName()); // 2件目の名前が"タスク2"
	}

	// ==================== 3. タスク登録のテスト ====================

	@Test
	void testCreateTask_Success() {
		// 【準備】新しいタスクを作成
		// 設定: 名前="新規タスク", ステータス="未着手"
		TaskEntity newTask = new TaskEntity();
		newTask.setTaskName("新規タスク");
		newTask.setTaskStatus("未着手");

		// 偽物Mapperが「1件登録成功」を返すように設定
		when(taskMapper.insertTask(any(TaskEntity.class))).thenReturn(1);

		// 【実行】
		boolean result = taskService.createTask(newTask);

		// 【検証】
		assertTrue(result); // trueが返される（登録成功）

		// 今日の日付が自動設定されているかチェック
		assertEquals(LocalDate.now(), newTask.getStartDate()); // 開始日が今日に設定される
	}

	@Test
	void testCreateTask_EmptyName() {
		// 【準備】タスク名が空のタスクを作成
		// 設定: 名前="", ステータス="未着手"
		TaskEntity newTask = new TaskEntity();
		newTask.setTaskName(""); // 空文字
		newTask.setTaskStatus("未着手");

		// 【実行】
		boolean result = taskService.createTask(newTask);

		// 【検証】
		assertFalse(result); // falseが返される（登録失敗）

		// Mapperが呼ばれていないことを確認
		verify(taskMapper, never()).insertTask(any(TaskEntity.class)); // insertTaskが呼ばれない
	}

	@Test
	void testCreateTask_NullName() {
		// 【準備】タスク名がnullのタスクを作成
		// 設定: 名前=null, ステータス="未着手"
		TaskEntity newTask = new TaskEntity();
		newTask.setTaskName(null); // null
		newTask.setTaskStatus("未着手");

		// 【実行】
		boolean result = taskService.createTask(newTask);

		// 【検証】
		assertFalse(result); // falseが返される（登録失敗）
		verify(taskMapper, never()).insertTask(any(TaskEntity.class)); // insertTaskが呼ばれない
	}

	// ==================== 4. タスク更新のテスト ====================

	@Test
	void testUpdateTask_Success() {
		// 【準備】更新用のタスクを作成
		// 設定: ID=1, 名前="更新されたタスク", ステータス="進行中"
		TaskEntity updateTask = new TaskEntity();
		updateTask.setTaskId(1);
		updateTask.setTaskName("更新されたタスク");
		updateTask.setTaskStatus("進行中");

		// 偽物Mapperの動作を設定
		when(taskMapper.findByTaskId(1)).thenReturn(testTask); // タスクが存在する
		when(taskMapper.updateTask(updateTask)).thenReturn(1); // 更新成功（1件更新）

		// 【実行】
		boolean result = taskService.updateTask(updateTask);

		// 【検証】
		assertTrue(result); // trueが返される（更新成功）
	}

	@Test
	void testUpdateTask_NotFound() {
		// 【準備】
		// 設定: 存在しないID=999, 名前="存在しないタスク"
		TaskEntity updateTask = new TaskEntity();
		updateTask.setTaskId(999);
		updateTask.setTaskName("存在しないタスク");

		when(taskMapper.findByTaskId(999)).thenReturn(null); // タスクが存在しない

		// 【実行】
		boolean result = taskService.updateTask(updateTask);

		// 【検証】
		assertFalse(result); // falseが返される（更新失敗）

		// 更新処理が呼ばれていないことを確認
		verify(taskMapper, never()).updateTask(any(TaskEntity.class)); // updateTaskが呼ばれない
	}

	// ==================== 5. タスク削除のテスト ====================

	@Test
	void testDeleteTask_Success() {
		// 【準備】
		// 設定: ID=1のタスクを削除
		when(taskMapper.findByTaskId(1)).thenReturn(testTask); // タスクが存在する
		when(taskMapper.deleteTask(1)).thenReturn(1); // 削除成功（1件削除）

		// 【実行】
		boolean result = taskService.deleteTask(1);

		// 【検証】
		assertTrue(result); // trueが返される（削除成功）
	}

	@Test
	void testDeleteTask_NotFound() {
		// 【準備】
		// 設定: 存在しないID=999を削除
		when(taskMapper.findByTaskId(999)).thenReturn(null); // タスクが存在しない

		// 【実行】
		boolean result = taskService.deleteTask(999);

		// 【検証】
		assertFalse(result); // falseが返される（削除失敗）

		// 削除処理が呼ばれていないことを確認
		verify(taskMapper, never()).deleteTask(any(Integer.class)); // deleteTaskが呼ばれない
	}
}