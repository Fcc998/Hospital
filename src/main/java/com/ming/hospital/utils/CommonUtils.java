package com.ming.hospital.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * Created by Ming on 2017/11/17.
 */
public class CommonUtils {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static Long getId(){
        Long id = System.currentTimeMillis();
        id = id*10+(int)(Math.random()*10);
        id = id*10+(int)(Math.random()*10);
        return id;
    }
    public static String  MD5(String key){
        //盐值
        String salt = "12u3jk125b1389adnsann9./,/.129012";
        String base = key + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public static String BCryptEncode(String key) {
        return bCryptPasswordEncoder.encode(key);
    }

    public static boolean BCryptMatch(String key, String hashedPassword) {
        return bCryptPasswordEncoder.matches(key, hashedPassword);
    }

    public static void main(String[] args) {
        System.out.println(MD5("admin"));
        String hashed = BCryptEncode("admin");
        System.out.println("BCrypt hash: " + hashed);
        System.out.println("BCrypt match: " + BCryptMatch("admin", hashed));

    }

}
