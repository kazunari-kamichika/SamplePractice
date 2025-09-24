package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;

/**
 * レポート関連サービス
 * 課題4-2：UserEntityの使用箇所確認用
 */
@Service
public class ReportService {
	@Autowired
	private UserService userService;

	/**
	 * ユーザーレポート生成
	 * 課題4-2：UserEntity参照確認で、この箇所も表示される
	 */
	public String generateUserReport(Long userId) {
		UserEntity user = userService.findById(userId);

		if (user != null) {
			return "ユーザーレポート: " + user.getName() + " (" + user.getDepartment() + ")";
		}

		return "ユーザーが見つかりません";
	}

	/**
	 * 全ユーザーレポート生成
	 * 課題4-2：UserEntityの別の使用箇所
	 */
	public String generateAllUsersReport() {
		StringBuilder report = new StringBuilder();

		for (UserEntity user : userService.getAllUsers()) {
			report.append(user.getName()).append(",");
		}

		return report.toString();
	}

}
