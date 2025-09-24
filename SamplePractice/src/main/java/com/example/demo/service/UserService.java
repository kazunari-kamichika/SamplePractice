package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.UserEntity;

/**
 * ユーザーサービス
 */
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 全ユーザー取得（課題3-1：宣言を開く確認対象）
	 */
	public List<UserEntity> getAllUsers() {
		return userMapper.findAll();
	}

	/**
	 * ユーザー詳細取得（課題用：複数箇所から呼ばれるメソッド）
	 */
	public UserEntity findById(Long id) {
		return userMapper.findById(id);
	}

	/**
	 * ユーザー登録
	 */
	public void registerUser(UserEntity user) {
		userMapper.insert(user);
	}

	/**
	 * ユーザー更新
	 */
	public void updateUser(UserEntity user) {
		userMapper.update(user);
	}

	/**
	 * ユーザー削除
	 */
	public void deleteUser(Long id) {
		userMapper.delete(id);
	}
}
