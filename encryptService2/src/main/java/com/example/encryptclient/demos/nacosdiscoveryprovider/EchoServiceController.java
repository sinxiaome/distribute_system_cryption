/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.encryptclient.demos.nacosdiscoveryprovider;

import com.example.encryptclient.biz.CryptBiz;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class EchoServiceController {

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        return "[ECHO] : " + message;
    }

    @PostMapping("/encrypt/{type}")
    public String encrypt(
            //类型  ，AES or RSA
            @PathVariable String type,
            //原数据 明文
            @RequestParam(name = "original", defaultValue = "unknown") String original,
            //是否保存到数据库
            @RequestParam(name = "saved", defaultValue = "unknown") String saved) { // aes  rsa
        if (type.equals("RSA") || type.equals("rsa")) {
            return CryptBiz.rsaEncrypt(original);
        }
        String data = CryptBiz.aesEncrypt(original);
//        String ss = "";
//        try {
//            ss = new String(CryptBiz.stringToBytes(CryptBiz.aesDecrypt(data)), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        return "type:" + type + " ，original： " + original + " saved： " + saved + " encryptData: " + data + "   decrypt: " + ss;
        System.out.println("data is >> " + data);
        return data;
    }

    @PostMapping("/decrypt/{type}")
    public String decrypt(//类型  ，AES or RSA
                          @PathVariable String type,
                          //原数据 明文
                          @RequestParam(name = "original", defaultValue = "unknown") String original,
                          //是否保存到数据库
                          @RequestParam(name = "saved", defaultValue = "unknown") String saved) {
        try {
            if (type.equals("RSA") || type.equals("rsa")) {
                String data = new String(CryptBiz.stringToBytes(CryptBiz.rsaDecrypt(original)), "utf-8");
                System.out.println("data is >> " + data);
                return data;
            }
            String data = new String(CryptBiz.stringToBytes(CryptBiz.aesDecrypt(original)), "utf-8");
            System.out.println("data is >> " + data);
            return data;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "failed";
    }
}
