package com.gdu.app06.aop;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component	// Spring Container에 ParameterCheckAOP 객체를 Bean으로 만든다.
@Aspect		// ParameterCheckAOP 클래스는 AOP 동작을 위한 클래스이다.
//	@EnableAspectJAutoProxy DBConfig에서 이미 작성하였다.
public class ParameterCheckAOP {
	
	
	// 포인트컷(어떤 메소드에 어드바이스(AOP 동작)를 적용할 것인가?)
	@Pointcut("execution(* com.gdu.app06.controller.*Controller.*ParamCheck(..))")
	public void setPointCut() {
		// 이 메소드는 이름만 제공하는 역할(아무 이름이나 써도 되고, 본문도 필요가 없다.)
		// setPointCut() 메소드를 부르면 포인트컷이 적용된다. 여기서 메소드명은 아무 상관이 없다. 아무렇게나 지어도 된다.
	}
	
	// 어드바이스(포인트컷에서 실제로 동작할 작업 : 파라미터들의 값 LOGGER를 이용해서 콘솔로 확인)

	// 파라미터를 콘솔에 출력하기 위한 LOGGER
	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterCheckAOP.class);
	
	@After("setPointCut()")
	public void paramLogging(JoinPoint joinPoint) {
		
		// 모든 파라미터가 저장된 HttpServletRequest 사용 
		// 이 두줄 복붙해서 넣으면 request를 알아낼 수 있다.
		// 컨트롤러가 아닌 곳에서 request가 필요하면 이런방식으로 알아낼 수 있다.
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		
		// 메소드마다 파라미터가 다르니까 Map으로 바꿔서 파라미터 꺼내오기.
		// HttpServletRequest -> Map으로 변환하기
		// 모든 파라미터가 Map의 Key로 변환된다. Map의 Key는 반복문으로 모두 순회할 수 있다. (파라미터를 찾을 수 있는거임.)
		Map<String, String[]> map = request.getParameterMap();

		// 콘솔에 출력할 형태 만들기
		// [파라미터명=값]
		String str = "";
		if(map.isEmpty()) {
			str += "[No Parameter]";
		} else {
			for(Entry<String, String[]> entry : map.entrySet()) {
				str += "[" + entry.getKey() + "=" + Arrays.toString(entry.getValue()) + "]"; // value는 배열이니까 문자열로 바꿔준다.
			}
		}
		
		// 어드바이스 실행
		// 치환 문자 : {}
		LOGGER.debug("{} {} {}", request.getMethod(), request.getRequestURI(), str);
	}

}
