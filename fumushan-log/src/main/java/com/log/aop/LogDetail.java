package com.log.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志详情
 * @author FUBINBIN
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogDetail {

	/**
	 * 操作模块
	 * @return
	 */
	public String model() default "";

	/**
	 * 模块名称
	 * @return
	 */
	public String modelName() default "";

	/**
	 * 方法描述
	 * @return
	 */
	public String desc() default "";

	/**
	 * 操作类型
	 * @return
	 */
	public HandleType handle() default HandleType.SELECT;

	public enum HandleType {

		SAVE(0), DELETE(1), SELECT(2), UPDATE(3);

		public final int code;

		private HandleType(int code) {
			this.code = code;
		}

	}

}
