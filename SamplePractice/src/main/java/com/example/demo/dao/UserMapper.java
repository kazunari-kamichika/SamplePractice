package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.UserEntity;

@Mapper
public interface UserMapper {
	
	/**
     * 全ユーザー取得
     */
    List<UserEntity> findAll();
    
    /**
     * ID指定でユーザー取得（課題用：呼び出し階層確認対象）
     */
    UserEntity findById(@Param("id") Long id);
    
    /**
     * ユーザー登録
     */
    void insert(UserEntity user);
    
    /**
     * ユーザー更新
     */
    void update(UserEntity user);
    
    /**
     * ユーザー削除
     */
    void delete(@Param("id") Long id);

}
