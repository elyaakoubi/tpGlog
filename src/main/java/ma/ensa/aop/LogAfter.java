package ma.ensa.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import ma.ensa.entities.Produit;

public class LogAfter implements AfterReturningAdvice {
	
	private Logger logger =Logger.getLogger(this.getClass().getName());


	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		
		if(method.getName()=="addItem"){
			String message = method.getName() + " " + ((Produit)args[0]).getPrix() + " " + args[1];
			logger.info(message);
		}
		
		
		
	}
	

}
