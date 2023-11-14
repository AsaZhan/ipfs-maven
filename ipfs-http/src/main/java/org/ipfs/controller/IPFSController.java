package org.ipfs.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ipfs.service.IPFSService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

import static java.lang.String.format;

@RestController
@RequestMapping("/ipfs")
public class IPFSController {

    private static final Log log = LogFactory.getLog(IPFSController.class);

    @Autowired
    private IPFSService service;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        JSONObject result = new JSONObject();

        String hash = service.upload(multipartFile);
        log.info(format("==>upload hash:%s", hash));
        int code = StringUtils.isEmpty(hash) ? 500 : 200;

        result.put("code", code);
        result.put("data", hash);

        return ResponseEntity.ok().body(result.toString());
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("hash") String hash) {

        log.info(format("==>download hash:%s", hash));
        byte[] data = service.download(hash);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Content-Disposition",String.format("attachment; filename=\"%s\"",Math.abs(hash.hashCode())));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(new ByteArrayInputStream(data)));
    }

    @RequestMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam(name = "date") String date) {
        return ResponseEntity.ok().body("hello" + date);
    }
}
