package com.tensquare.asset.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tensquare.asset.entity.ApiRequest;
import com.tensquare.asset.entity.ApiResponse;
import com.tensquare.asset.validation.CheckObj;
import com.tensquare.asset.validation.DcmsParamValidator;
import com.tensquare.asset.validation.ValidationResult;
import org.apache.log4j.MDC;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Aspect
@Component
public class DcmsLogAspect {
    private static final String LOGID = "LOGID";
    private static final Logger LOGGER = LoggerFactory.getLogger(DcmsLogAspect.class);
    private static final JsonSensitiveFilter jsFilter = new JsonSensitiveFilter();

    public DcmsLogAspect() {
    }

    public static void beginTrace() {
        if (MDC.get("LOGID") == null) {
            MDC.put("LOGID", UUID.randomUUID().toString());
        }

    }

    public static void beginTrace(String logid) {
        MDC.put("LOGID", logid);
    }

    @Around("@annotation(com.tensquare.asset.log.DcmsLogTrace)")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long start = System.currentTimeMillis();
        boolean first = false;
        if (MDC.get("LOGID") == null) {
            first = true;
            MDC.put("LOGID", UUID.randomUUID().toString());
        }

        Object var13;
        try {
            DcmsLogTrace logAnnotation = null;
            Method method = ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();

            try {
                Method realMethod = proceedingJoinPoint.getTarget().getClass().getDeclaredMethod(proceedingJoinPoint.getSignature().getName(), method.getParameterTypes());
                logAnnotation = (DcmsLogTrace)realMethod.getAnnotation(DcmsLogTrace.class);
            } catch (Exception var20) {
                LOGGER.debug("DcmsLogTrace get realmethod fail", var20);
            }

            if (logAnnotation == null) {
                logAnnotation = (DcmsLogTrace)method.getAnnotation(DcmsLogTrace.class);
            }

            if (logAnnotation == null) {
                LOGGER.debug("DcmsLogTrace annotation is null");
            }

            String argstxt = "";
            if (logAnnotation != null && logAnnotation.printArgs() && proceedingJoinPoint.getArgs() != null) {
                argstxt = JSON.toJSONString(proceedingJoinPoint.getArgs(), jsFilter, new SerializerFeature[0]);
            }

            LOGGER.info("class:{} method:{} args:{}", new Object[]{proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName(), argstxt});
            Object[] args = proceedingJoinPoint.getArgs();
            Object returnObj = null;
            if (logAnnotation != null && logAnnotation.checkApiRequest()) {
                returnObj = this.checkApiRequest(logAnnotation, (ApiRequest)args[0]);
            }

            if (returnObj == null) {
                try {
                    returnObj = proceedingJoinPoint.proceed();
                } catch (Throwable var21) {
                    if (logAnnotation == null || !logAnnotation.checkApiRequest()) {
                        throw var21;
                    }

                    LOGGER.error("class:{} method:{} invoke fail UNKNOW ERROR!", new Object[]{proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName(), var21});
                    LOGGER.error(getStackTrace(var21));
                    ApiResponse resp = new ApiResponse();
                    resp.setResultCode("9999");
                    resp.setResultMsg("未知异常");
                    returnObj = resp;
                }
            }

            String returntxt = "";
            if (returnObj != null && returnObj instanceof ApiResponse) {
                String resultCode = ((ApiResponse)returnObj).getResultCode();
                String resultMsg = ((ApiResponse)returnObj).getResultMsg();
                if (StringUtils.isEmpty(resultMsg)) {
                    if (resultCode.equals("9997")) {
                        ((ApiResponse)returnObj).setResultMsg("权限校验失败");
                    } else if (resultCode.equals("2")) {
                        ((ApiResponse)returnObj).setResultMsg("失败");
                    } else if (resultCode.equals("1")) {
                        ((ApiResponse)returnObj).setResultMsg("参数校验失败");
                    } else if (resultCode.equals("9998")) {
                        ((ApiResponse)returnObj).setResultMsg("token已失效");
                    } else if (resultCode.equals("9999")) {
                        ((ApiResponse)returnObj).setResultMsg("未知异常");
                    } else if (resultCode.equals("0")) {
                        ((ApiResponse)returnObj).setResultMsg("成功");
                    } else {
                        ((ApiResponse)returnObj).setResultMsg("无描述");
                    }
                }
            }

            if (returnObj != null && logAnnotation != null && logAnnotation.printReturn()) {
                try {
                    returntxt = JSON.toJSONString(returnObj, jsFilter, new SerializerFeature[0]);
                } catch (Exception var19) {
                    LOGGER.debug("return obj to json fail", var19);
                }
            }

            Long used = System.currentTimeMillis() - start;
            LOGGER.info("class:{} method:{} return:{} used:{} mills", new Object[]{proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName(), returntxt, used});
            var13 = returnObj;
        } catch (Throwable var22) {
            Long used = System.currentTimeMillis() - start;
            LOGGER.error("class:{} method:{} invoke fail! used:{} mills", new Object[]{proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName(), used, var22});
            throw var22;
        } finally {
            if (first) {
                MDC.remove("LOGID");
            }

        }

        return var13;
    }

    private Object checkApiRequest(DcmsLogTrace logAnnotation, ApiRequest apiRequest) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        ApiResponse resp = new ApiResponse();
        if (logAnnotation.checkSysId() && StringUtils.isEmpty(apiRequest.getSysId())) {
            resp.setResultCode("1");
            resp.setResultMsg("sysId为空");
            return resp;
        } else if (logAnnotation.checkUserId() && StringUtils.isEmpty(apiRequest.getUserId())) {
            resp.setResultCode("1");
            resp.setResultMsg("userId为空");
            return resp;
        } else if (logAnnotation.checkToken() && StringUtils.isEmpty(apiRequest.getToken())) {
            resp.setResultCode("1");
            resp.setResultMsg("token为空");
            return resp;
        } else if (logAnnotation.checkBizParam()) {
            if (apiRequest.getBizParam() == null) {
                resp.setResultCode("1");
                resp.setResultMsg("bizParam为空");
                return resp;
            } else {
                ValidationResult vr = this.checkObj(apiRequest.getBizParam(), logAnnotation);
                if (!vr.isSuccess()) {
                    resp.setResultCode("1");
                    resp.setResultMsg(vr.getErrorMsg());
                    return resp;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    private ValidationResult checkObj(Object obj, DcmsLogTrace logAnnotation) throws IllegalArgumentException, IllegalAccessException {
        Class bizclz = obj.getClass();
        Field[] bizfields = bizclz.getDeclaredFields();
        Field[] var8 = bizfields;
        int var7 = bizfields.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            Field field = var8[var6];
            if (field.isAnnotationPresent(CheckObj.class)) {
                field.setAccessible(true);
                Object childobj = field.get(obj);
                CheckObj chkObjAnno = (CheckObj)field.getAnnotation(CheckObj.class);
                ValidationResult childvr;
                if (childobj == null && !chkObjAnno.nullable()) {
                    childvr = new ValidationResult();
                    childvr.setSuccess(false);
                    childvr.setErrorMsg(chkObjAnno.message());
                    return childvr;
                }

                if (childobj != null) {
                    Object clsElm;
                    Iterator var13;
                    //ValidationResult childvr;
                    if (childobj instanceof List) {
                        List childList = (List)childobj;
                        var13 = childList.iterator();

                        while(var13.hasNext()) {
                            clsElm = var13.next();
                            childvr = this.checkObj(clsElm, logAnnotation);
                            if (!childvr.isSuccess()) {
                                return childvr;
                            }
                        }
                    } else if (childobj instanceof Set) {
                        Set childSet = (Set)childobj;
                        var13 = childSet.iterator();

                        while(var13.hasNext()) {
                            clsElm = var13.next();
                            childvr = this.checkObj(clsElm, logAnnotation);
                            if (!childvr.isSuccess()) {
                                return childvr;
                            }
                        }
                    } else {
                        childvr = DcmsParamValidator.validate(childobj, logAnnotation.groups());
                        if (!childvr.isSuccess()) {
                            return childvr;
                        }

                        childvr = this.checkObj(childobj, logAnnotation);
                        if (!childvr.isSuccess()) {
                            return childvr;
                        }
                    }
                }
            }
        }

        ValidationResult vr = DcmsParamValidator.validate(obj, logAnnotation.groups());
        return vr;
    }

    private static String getStackTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        try {
            t.printStackTrace(printWriter);
            printWriter.close();
            stringWriter.close();
            String result = stringWriter.toString();
            return result;
        } catch (Exception var4) {
            LOGGER.error("print error fail", var4);
            return "";
        }
    }
}
