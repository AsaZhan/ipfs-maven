package org.ipfs.service.impl;

import io.ipfs.api.IPFS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ipfs.bean.IPFSCluster;
import org.ipfs.service.IPFSService;
import org.ipfs.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class IPFSServiceImpl implements IPFSService {
    private static final Log log = LogFactory.getLog(IPFSServiceImpl.class);

    @Autowired
    private IPFSCluster ipfsCluster;

    //Qma489Xc4jUboLpcFf8hGyhWuHFVA6PY2JH41bu7C8Lo8B
    @Override
    public String upload(MultipartFile multipartFile) {
        String hash = "";
        String originalFilename = multipartFile.getOriginalFilename();
        File file = new File("src/main/resources/tmp/"+originalFilename);
        try {
            // get cluster
            List<String> clusters = ipfsCluster.getClusters();
            // get random number
            int index = (int) (Math.random()* clusters.size());
            // get ipfs address
            String clusterAddress = clusters.get(index);
            log.info(String.format("==>upload address:%s",clusterAddress));
            // transfer multipartFile to file
            org.apache.commons.io.FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            // upload file and get hash
            hash = FileUtils.upload(new IPFS(clusterAddress),file);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (file.exists()){
                boolean b = file.delete();
            }
        }
        return hash;
    }

    //Qma489Xc4jUboLpcFf8hGyhWuHFVA6PY2JH41bu7C8Lo8B
    @Override
    public byte[] download(String hash) {

        byte[] data = new byte[]{};
        try {
            // get cluster
            List<String> clusters = ipfsCluster.getClusters();
            // get random number
            int index = (int) (Math.random()* clusters.size());
            // get ipfs address
            String clusterAddress = clusters.get(index);
            log.info(String.format("==>download address:%s",clusterAddress));
            data = FileUtils.download(new IPFS(clusterAddress), hash);
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
