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
public class SingleCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(SingleCacheAspect.class);

    @Autowired
    private RedisTemplate<String, String> template;

    //切点
    @Pointcut("@annotation(cn.dubby.itbus.aspect.cache.SingleCache)")
    public void aspect() {
    }


    @Around("aspect()")
    public Object around(JoinPoint joinPoint) {

        //生成cacheKey
        CacheParameter cacheParameter = getCacheParameter(joinPoint);
        String cacheKey = cacheParameter.cacheKey;
        long timeout = cacheParameter.timeout;
        TimeUnit unit = cacheParameter.unit;

        if (timeout <= 0) {
            timeout = 1;
            unit = TimeUnit.HOURS;
        }

        if (cacheKey == null || cacheKey.trim().length() <= 0) {
            logger.warn(joinPoint.getTarget().getClass().getCanonicalName() + "#" + joinPoint.getSignature().getName(), "cacheKey is empty");
            return trueMethodProcess(joinPoint);
        }

        //先看缓存中有没有，如果有直接返回
        CacheResult cacheResult = getCache(cacheKey);
        if (cacheResult.isExist()) {
            if (CacheConstant.SINGLE_CACHE_NULL.equals(cacheResult.getResult())) {
                //防止缓存击穿
                return null;
            }
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;

            return JSON.parseObject(cacheResult.getResult(), methodSignature.getReturnType());
        }

        //缓存中没有，把返回值插入缓存
        Object trueResult = trueMethodProcess(joinPoint);
        if (trueResult == null) {
            if (timeout > 0) {
                template.opsForValue().set(cacheKey, CacheConstant.SINGLE_CACHE_NULL, timeout, unit);
            } else {
                template.opsForValue().set(cacheKey, CacheConstant.SINGLE_CACHE_NULL);
            }
        } else {
            if (timeout > 0) {
                template.opsForValue().set(cacheKey, JSON.toJSONString(trueResult), timeout, unit);
            } else {
                template.opsForValue().set(cacheKey, JSON.toJSONString(trueResult));
            }
        }

        return trueResult;
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
    private CacheParameter getCacheParameter(JoinPoint joinPoint) {
        CacheParameter cacheParameter = new CacheParameter();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterArray = methodSignature.getParameterNames();
        Object[] trueParameterArray = joinPoint.getArgs();

        for (int i = 0; i < parameterArray.length && i < trueParameterArray.length; ++i) {
            context.setVariable(parameterArray[i], trueParameterArray[i]);
        }

        String cacheKeyParameter = method.getAnnotation(SingleCache.class).cacheKey();
        ExpressionParser parser = new SpelExpressionParser();

        cacheParameter.cacheKey = parser.parseExpression(cacheKeyParameter).getValue(context, String.class);
        cacheParameter.timeout = method.getAnnotation(SingleCache.class).timeout();
        cacheParameter.unit = method.getAnnotation(SingleCache.class).unit();

        return cacheParameter;
    }

    /**
     * 获得缓存中的值
     *
     * @param cacheKey
     * @return
     */
    private CacheResult getCache(String cacheKey) {
        String result = template.opsForValue().get(cacheKey);
        if (result != null) {
            return CacheResult.success(result);
        }
        return CacheResult.NULL_CACHE;
    }

    private static class CacheResult {

        boolean exist = false;
        String result;

        public boolean isExist() {
            return exist;
        }

        public void setExist(boolean exist) {
            this.exist = exist;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public static CacheResult success(String result) {
            CacheResult cacheResult = new CacheResult();
            cacheResult.setExist(true);
            cacheResult.setResult(result);
            return cacheResult;
        }

        public static final CacheResult NULL_CACHE = new CacheResult();
    }

    private static class CacheParameter {
        private String cacheKey;

        private long timeout;

        private TimeUnit unit;
    }
}
