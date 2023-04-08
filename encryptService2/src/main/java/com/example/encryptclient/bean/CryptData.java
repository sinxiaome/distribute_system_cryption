package com.example.encryptclient.bean;

public class CryptData {
    private String uuid;
    private String type; //encrypt  decrypt
    private int saved;//0 不用保存 ， 1 需要保存
    private int algorithm; // 1 AES  2 RSA1024   3  验签  md5 +  4 、验签  sha
    private String secretKey;  //秘钥  如果是 AES  直接是 秘钥  ，如果是 RSA 则是 私钥+公钥
    private String originalText;
    private String cipherText;
    private int state;  // 成功 or 失败      成功： 0   失败：  非 0
    private long time;
    private String remark; //相关说明 ， 推荐  json 格式
    private String salt;  //本地盐 客户端 盐
    private String localSalt; //本地盐
    private int needSalt; //运算是否需要盐参与   0 ：不需要   1 ：需要

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public int getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLocalSalt() {
        return localSalt;
    }

    public void setLocalSalt(String localSalt) {
        this.localSalt = localSalt;
    }

    public int getNeedSalt() {
        return needSalt;
    }

    public void setNeedSalt(int needSalt) {
        this.needSalt = needSalt;
    }
}
