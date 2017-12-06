package com.log.aop;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.log.model.LogModel;
import com.log.service.LogService;

@Aspect
@Component
public class LogDetailAop {

	@Autowired
	private LogService logService;

	@Around("@annotation(com.log.aop.LogDetail)")
	public Object logDetail(final ProceedingJoinPoint pjp) {
		try {
			// 通过反射拿到传过来注解类的信息
			Method method = ((MethodSignature) pjp.getSignature()).getMethod();
			LogDetail logDetail = method.getAnnotation(LogDetail.class);

			//获取注解中的信息
			Object[] args = pjp.getArgs();
			String userId = "1";// 用户的ID,从登录信息中获取
			String desc = logDetail.desc();
			String model = logDetail.model();
			String modelName = logDetail.modelName();
			if (StringUtils.isNotBlank(modelName)) {
				modelName = this.parseValue(modelName, method, args);
			}
			Integer handleType = logDetail.handle().code;

			LogModel entity = new LogModel();
			entity.setUserId(userId);
			entity.setDesc(desc);
			entity.setModel(model);
			entity.setMothedName(modelName);
			entity.setHandleType(handleType);
			entity.setCreated(new Date());
			entity.setCreator(userId);

			// 保存操作日志
			this.logService.save(entity);
			return pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SPEL表达式
	 */
	private String parseValue(String key, Method method, Object[] args) {
		StandardEvaluationContext context = new StandardEvaluationContext();
		Expression expression = this.getExpression(key, method, args, context);
		if (expression != null) {
			return expression.getValue(context, String.class);
		}
		return null;
	}

	private Expression getExpression(String key, Method method, Object[] args, StandardEvaluationContext context) {
		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = parameterNameDiscoverer.getParameterNames(method);
		// 使用SPEL进行key的解析
		ExpressionParser parser = new SpelExpressionParser();
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		try {
			return parser.parseExpression(key);
		} catch (Exception e) {
			return null;
		}
	}
}
