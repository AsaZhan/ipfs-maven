package org.ipfs.service;

import org.springframework.web.multipart.MultipartFile;

public interface IPFSService {

    String upload(MultipartFile multipartFile);

    byte[] download(String hash);
}
