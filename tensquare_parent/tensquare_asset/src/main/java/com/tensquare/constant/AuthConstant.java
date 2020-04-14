package com.tensquare.constant;

public class AuthConstant {
    public enum Sys{
        EIP,
        DSP
    }

    /**
     * 是否为支持的系统
     *
     * @param sysId
     * @return
     */
    public static boolean isSupportedSys(String sysId) {
        //日后有需要再改成数据库配置
        for(Sys sys:AuthConstant.Sys.values()) {
            if(sys.name().equals(sysId)) {
                return true;
            }
        }
        return false;
    }
}
