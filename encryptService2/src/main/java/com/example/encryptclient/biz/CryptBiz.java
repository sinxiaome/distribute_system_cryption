package com.example.encryptclient.biz;

import org.apache.http.util.TextUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CryptBiz {

    private static final String key_local = "**&&120314-56abcde";
    private static final String iv_local = "1234567890123321";

    public static String aesEncrypt(String data) {
        return aesEncrypt(key_local, iv_local, data);
    }

    public static String aesDecrypt(String data) {
        return aesDecrypt(key_local, iv_local, data);
    }


    public static String aesEncrypt(String key, String ivStr, String data) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(ivStr) || TextUtils.isEmpty(data)) {
            return "param can not be empty";
        }
        try {
            if (key.length() < 16) {
                return "key is shorter than 16 length";
            }
            if (key.length() > 16) {
                key = key.substring(0, 16);
            }

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes("utf-8"));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encryptData = cipher.doFinal(data.getBytes("utf-8"));
            return bytesToString(encryptData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String aesDecrypt(String key, String ivStr, String data) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(ivStr) || TextUtils.isEmpty(data)) {
            return "param can not be empty";
        }
        try {
            if (key.length() < 16) {
                return "key is shorter than 16 length";
            }
            if (key.length() > 16) {
                key = key.substring(0, 16);
            }
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes("utf-8"));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] encryptData = cipher.doFinal(stringToBytes(data));
            return bytesToString(encryptData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    // 1024 bits 的 RSA 密钥对，最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;

    // 1024 bits 的 RSA 密钥对，最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static Map<String, Object> localRsaKey;


    // 生成密钥对
    public static Map<String, Object> initKey(int keysize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 设置密钥对的 bit 数，越大越安全
        keyPairGen.initialize(keysize);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 获取公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        if (localRsaKey == null) {
            localRsaKey = new HashMap<>(2);
        }

        localRsaKey.put(PUBLIC_KEY, publicKey);
        localRsaKey.put(PRIVATE_KEY, privateKey);
        return localRsaKey;
    }

    // 获取公钥字符串
    public static String getPublicKeyStr(Map<String, Object> keyMap) {
        // 获得 map 中的公钥对象，转为 key 对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    // 获取私钥字符串
    public static String getPrivateKeyStr(Map<String, Object> keyMap) {
        // 获得 map 中的私钥对象，转为 key 对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    // 获取公钥
    public static PublicKey getPublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyByte = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    // 获取私钥
    public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        byte[] privateKeyByte = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * BASE64 编码返回加密字符串
     *
     * @param key 需要编码的字节数组
     * @return 编码后的字符串
     */
    public static String encryptBASE64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

    /**
     * BASE64 解码，返回字节数组
     *
     * @param key 待解码的字符串
     * @return 解码后的字节数组
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * 公钥加密
     *
     * @param text         待加密的明文字符串
     * @param publicKeyStr 公钥
     * @return 加密后的密文
     */
    public static String rsaEncrypt(String text, String publicKeyStr) {
        try {
            System.out.println("明文字符串为:" + text);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyStr));
            byte[] tempBytes = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(tempBytes);
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + text + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param secretText    待解密的密文字符串
     * @param privateKeyStr 私钥
     * @return 解密后的明文
     */
    public static String rsaDecrypt(String secretText, String privateKeyStr) {
        try {
            System.out.println("secretText : " + secretText);
            // 生成私钥
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyStr));
            // 密文解码
            byte[] secretTextDecoded = Base64.getDecoder().decode(secretText);
            byte[] tempBytes = cipher.doFinal(secretTextDecoded);
//            new String(tempBytes);
//            return new String(tempBytes);
            return bytesToString(tempBytes);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + secretText + "]时遇到异常", e);
        }
    }


    public static String bytesToString(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] stringToBytes(String data) {
        return Base64.getDecoder().decode(data);
    }

    private static String local_pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCb9W02aZ9aGgxQa+3ZHTWVzz3EO7ek8pNrErEPVuYjl+JQRjbQe5VBLTcPksoGS/iOV/BXR6yPA+xQlCxg4lJtTHMz7nOMpfique6yu68et5LAMh1orfM7sSC8CiN33pKui0HcW7qsGDOg2/h1/Rp58gW9QVVRTeWNN6/xMFsxSwIDAQAB";
    private static String local_priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJv1bTZpn1oaDFBr7dkdNZXPPcQ7t6Tyk2sSsQ9W5iOX4lBGNtB7lUEtNw+SygZL+I5X8FdHrI8D7FCULGDiUm1MczPuc4yl+Kq57rK7rx63ksAyHWit8zuxILwKI3fekq6LQdxbuqwYM6Db+HX9GnnyBb1BVVFN5Y03r/EwWzFLAgMBAAECgYAys7VPrULrDfDW7F+k6AQVsgxIkYmAjIQ0mR0K+ZtUEvq2Uj1bxfGeAM8XtwcKyQQDcr9mAJ0u2X2oLKPI04ssC6dS5l/WOJw2avpIcdwOCdv+HVw8NRia8sv+xfrikKFdj1nJ86pPHLIDTElxiCCfx52f4kmNvcSwSp9G7pBu4QJBAP5dwfSLYtxm9meoVQ74PRpRttI1goIJDxMpy91V61MZzP7tzhDcYivGv02WxzyfhKxwc+Rkl/6TXwT7hXRILgMCQQCc9dyzV/ipv0dHhlGcB/A53MGmhzRT5GgHQZU+KEI5MQz8umlNuY904fWL001ohpcRd6/IiG4NtjhMnXoPFZEZAkBxVTTl0HTr3rRK2yWK1e/jmiTq5AySA5dD4ouCwpVnIUfFDg+SU58qAhOabmO3Dxv9+NyqFmfidacQgUCk3sQhAkAAvXQF8HM+saUvZCW8W5k5XJ4ZH+gSQTBwYFHI0j3FsUKDaomWkTpo7avPtfZWeNP7hBnzExmZvPM1AUUTDyH5AkEAm5ooy+VN4UcCkxYFPK0K4U/mn7Su3JcbZ7iTImFYrQHPfu6OVU40efmswssduSiNXy1r3mcggvIjfT02Z/ELsw==";

    //
    public static String rsaEncrypt(String data) {
        System.out.println("rsaEncrypt  data : " + data);
        return rsaEncrypt(data, local_pubKey);
    }

    public static String rsaDecrypt(String data) {
        System.out.println("rsaDecrypt  data : " + data);
        return rsaDecrypt(data, local_priKey);
    }

//    public static void main(String[] args) {
//        try {
//            Map<String, Object> keys = initKey(1024);
//            String pubKey = getPublicKeyStr(keys);
//            System.out.println("pubKey ::: " + pubKey);
//            String priKey = getPrivateKeyStr(keys);
//            System.out.println("priKey ::: " + priKey);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
