package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.UserEntity;

/**
 * 部署関連サービス
 * 課題用：同じfindByIdメソッドを別クラスでも使用（呼び出し階層確認用）
 */
@Service
public class DepartmentService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 部署長情報取得
	 * 課題4-1：findByIdの呼び出し階層確認で、この箇所も表示される
	 */
	public UserEntity findById(Long managerId) {
		// 部署長のユーザー情報を取得
		return userMapper.findById(managerId);
	}

	/**
	 * 部署メンバー数を取得（課題用）
	 */
	public int getDepartmentMemberCount(String departmentName) {
		return 5; // 簡易実装
	}
}
