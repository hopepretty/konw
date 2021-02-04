package com.pc.knowservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author huangjun
 */
public class FileResponseUtils {

    public static ResponseEntity<byte[]> from(String fileName, String suffix, byte[] bytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        String encodedFileName = null;
        try {
            encodedFileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.add("content-type", "image/png");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(bytes.length).contentType(MediaType.IMAGE_PNG).body(bytes);
    }
}
