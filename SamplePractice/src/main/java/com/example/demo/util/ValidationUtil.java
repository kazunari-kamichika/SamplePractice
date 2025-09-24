package com.example.demo.util;

import com.example.demo.entity.UserEntity;

/**
 * バリデーションユーティリティクラス
 * 課題4-2：UserEntityの使用箇所をさらに増やすためのクラス
 */
public class ValidationUtil {

	/**
	 * ユーザー情報の妥当性チェック
	 * 課題4-2：UserEntity参照箇所の一つ
	 */
	public static boolean isValidUser(UserEntity user) {
		if (user == null) {
			return false;
		}

		if (user.getName() == null || user.getName().trim().isEmpty()) {
			return false;
		}

		if (user.getEmail() == null || !user.getEmail().contains("@")) {
			return false;
		}

		return true;
	}

	/**
	 * メールアドレス形式チェック
	 */
	public static boolean isValidEmailFormat(String email) {
		return email != null && email.contains("@") && email.contains(".");
	}

}
