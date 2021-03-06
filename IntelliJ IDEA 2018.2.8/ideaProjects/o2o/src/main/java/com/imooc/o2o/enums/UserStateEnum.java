package com.imooc.o2o.enums;

/**
 * 使用枚举表述常量数据字典
 */
public enum UserStateEnum {

	SUCCESS(0, "创建成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(-1002, "用户信息为空");

	private int state;

	private String stateInfo;

	private UserStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserStateEnum stateOf(int index) {
		for (UserStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}