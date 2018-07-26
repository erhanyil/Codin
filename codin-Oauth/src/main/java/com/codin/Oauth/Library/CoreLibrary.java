package com.codin.Oauth.Library;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class CoreLibrary {

    @Autowired
    MailSender mailSender;

    public String createLicenseKey(String userName, String productKey, String versionNumber) {
        String uuid = UUID.randomUUID().toString();
        final String s = userName + "|" + productKey + "|" + versionNumber + "|" + uuid;
        final HashFunction hashFunction = Hashing.sha1();
        final HashCode hashCode = hashFunction.hashString(s, Charset.defaultCharset());
        final String upper = hashCode.toString().toLowerCase();
        return group(upper);
    }

    public String group(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            if (i % 6 == 0 && i > 0) {
                result += '-';
            }
            result += s.charAt(i);
        }
        return result;
    }

    public java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    public String md5(String input) {
        String md5 = null;
        if (null == input) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public Boolean passwordEqual(String password, String encodedPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }

    public String passwordEncoder(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public Boolean sendMail(String to, String from, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(body);
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
