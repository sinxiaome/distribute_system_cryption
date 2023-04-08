package com.example.demo.demos.nacosdiscoveryconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class NetCrypt {

    @LoadBalanced
    @Autowired
    public RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 加密
     *
     * @param data  明文
     * @param saved 是否保存  0： 不保存  1： 保存
     * @return
     */
    public String aesEnCrypt( String data, String saved) {

        String type = "AES";
        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("original", data);
        map.add("saved", saved);

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String result = restTemplate.postForObject("http://nacos-service-cryptService/encrypt/" + type, request, String.class);

        return result;
    }

    /**
     * 解密
     *
     * @param data  明文
     * @param saved 是否保存  0： 不保存  1： 保存
     * @return
     */
    public String aesDeCrypt( String data, String saved) {

        String type = "AES";
        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("original", data);
        map.add("saved", saved);

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String result = restTemplate.postForObject("http://nacos-service-cryptService/decrypt/" + type, request, String.class);

        return result;
    }

    /**
     * 加密
     *
     * @param data  明文
     * @param saved 是否保存  0： 不保存  1： 保存
     * @return
     */
    public String rsaEnCrypt(String data, String saved) {

        String type = "RSA";
        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("original", data);
        map.add("saved", saved);

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String result = restTemplate.postForObject("http://nacos-service-cryptService/encrypt/" + type, request, String.class);

        return result;
    }

    /**
     * 解密
     *
     * @param data  明文
     * @param saved 是否保存  0： 不保存  1： 保存
     * @return
     */
    public String rsaDeCrypt(String data, String saved) {
        String type = "RSA";
        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("original", data);
        map.add("saved", saved);

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String result = restTemplate.postForObject("http://nacos-service-cryptService/decrypt/" + type, request, String.class);

        return result;
    }
}
