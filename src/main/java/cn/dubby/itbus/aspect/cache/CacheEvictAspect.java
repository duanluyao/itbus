package cn.dubby.itbus.aspect.cache;

import cn.dubby.itbus.constant.CacheConstant;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by teeyoung on 17/5/5.
 */
@Aspect
@Order(0)
@Component
public class CacheEvictAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheEvictAspect.class);

    @Autowired
    private RedisTemplate<String, String> template;

    //切点
    @Pointcut("@annotation(cn.dubby.itbus.aspect.cache.CacheEvict)")
    public void aspect() {
    }


    @Around("aspect()")
    public Object around(JoinPoint joinPoint) {

        //生成cacheKey
        String cacheKey = getCacheKey(joinPoint);
        template.delete(cacheKey);

        return trueMethodProcess(joinPoint);
    }

    /**
     * 调用真正的方法
     *
     * @param joinPoint
     * @return
     */
    private Object trueMethodProcess(JoinPoint joinPoint) {
        try {
            return ((ProceedingJoinPoint) joinPoint).proceed();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
        }
        return null;
    }

    /**
     * 生成缓存的 key
     *
     * @param joinPoint
     * @return
     */
    private String getCacheKey(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterArray = methodSignature.getParameterNames();
        Object[] trueParameterArray = joinPoint.getArgs();

        for (int i = 0; i < parameterArray.length && i < trueParameterArray.length; ++i) {
            context.setVariable(parameterArray[i], trueParameterArray[i]);
        }

        String cacheKeyParameter = method.getAnnotation(CacheEvict.class).cacheKey();
        ExpressionParser parser = new SpelExpressionParser();

        return parser.parseExpression(cacheKeyParameter).getValue(context, String.class);
    }
}
