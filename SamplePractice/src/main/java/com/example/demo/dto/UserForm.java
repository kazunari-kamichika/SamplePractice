package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class UserForm {
	
	/** ID（編集時に使用） */
    private Long id;
    
    /** 名前 */
    @NotBlank(message = "名前は必須です")
    private String name;
    
    /** カナ */
    @NotBlank(message = "カナは必須です")
    private String kana;
    
    /** メールアドレス */
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "正しいメールアドレスの形式で入力してください")
    private String email;
    
    /** 部署 */
    @NotBlank(message = "部署は必須です")
    private String department;
    
    /** 生年月日 */
    @NotNull(message = "生年月日は必須です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    /** 入社日 */
    @NotNull(message = "入社日は必須です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    
    public UserForm() {
    }
    
    // Getter/Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getKana() {
        return kana;
    }
    
    public void setKana(String kana) {
        this.kana = kana;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

}
