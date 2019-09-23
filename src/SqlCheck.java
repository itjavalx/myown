import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SqlCheck{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static String sqlInjStr = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|";
	private static String scriptInjStr = "\"|<|>|script|alert|delete|clear|function|return|onfocus|onclick|onerror|onblur|onkeydown|onload|onkeypress|onkeyup|onmouseover|onmouseup|onmousedown|onsubmit";
	  
	
	@Around("execution(* com.xl.modules.*.models.*Impl.*(..))")
	public Object before(ProceedingJoinPoint PJ) throws Throwable {
	    String methodName = PJ.getSignature().getName();
	    if(!methodName.equals("getDepartmentTree")){
	    	Object[] params = PJ.getArgs();
		    for(int i=0;i<params.length;i++){
		    	if(sqlInj(params[i].toString())){
		    		log.info("检测未通过");
		    		throw new sqlCheckException();
		    	}
		    }
	    }
	    return PJ.proceed();
	}
	
	public boolean sqlInj(String str)  {
		  str = str.toLowerCase();
		  if(str.indexOf("'")>=0){
			  return true;
		  }
		  String[] inj_stra=sqlInjStr.split("\\|");
		  for (int i=0 ; i < inj_stra.length ; i++ )  {
			  if (str.indexOf(" "+inj_stra[i])>=0||str.indexOf(inj_stra[i]+" ")>=0)  {
				  return true;
			  }
		  }
		  String[] inj_stra_script=scriptInjStr.split("\\|");
		  for (int i=0 ; i < inj_stra_script.length ; i++ )  {
			  if (str.indexOf(inj_stra_script[i])>=0)  {
				  return true;
			  }
		  }
		  return false;  
	}
	
	public class sqlCheckException extends Throwable{

		private static final long serialVersionUID = -606583397774585066L;
	}
}






