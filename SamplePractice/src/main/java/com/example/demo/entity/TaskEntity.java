package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * タスク情報を表すエンティティクラス（バリデーション付き）
 */
public class TaskEntity {
    
    /** タスクID */
    private Integer taskId;
    
    /** タスク名 */
    @NotBlank(message = "タスク名は必須です")
    @Size(max = 20, message = "タスク名は20文字以内で入力してください")
    private String taskName;
    
    /** タスク状況 */
    @NotBlank(message = "タスク状況は必須です")
    private String taskStatus;
    
    /** 作成日 */
    private LocalDate startDate;
    
    /** 完了日 */
    private LocalDate endDate;
    
    // デフォルトコンストラクタ
    public TaskEntity() {
    }
    
    // 全フィールドを持つコンストラクタ
    public TaskEntity(Integer taskId, String taskName, String taskStatus, LocalDate startDate, LocalDate endDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    // Getter and Setter
    public Integer getTaskId() {
        return taskId;
    }
    
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskStatus() {
        return taskStatus;
    }
    
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public String toString() {
        return "TaskEntity{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
