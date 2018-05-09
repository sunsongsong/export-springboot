package com.song.export.enums;

/**
 * Redis key值 前缀枚举类  避免混乱
 *
 */
public enum RedisPrefixEnum {
    VERIFY_CODE("VerifyCode:"),//验证码缓存redis中key的前缀
    ;
    private String prefix;

    RedisPrefixEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
