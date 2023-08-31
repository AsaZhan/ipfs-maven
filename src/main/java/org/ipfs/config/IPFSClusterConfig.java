package org.ipfs.config;

import org.ipfs.bean.IPFSCluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.LinkedList;

@Configuration
public class IPFSClusterConfig {

    @Value("${ipfs.clusters}")
    private String clusters;

    @Bean
    public IPFSCluster init() {
        LinkedList<String> clusterList = new LinkedList<>();
        if (clusters.contains(",")) {
            String[] nodes = clusters.split(",");
            clusterList.addAll(Arrays.asList(nodes));
        }else {
            clusterList.add(clusters);
        }
        return new IPFSCluster(clusterList);
    }
}
