package org.ipfs.bean;

import java.util.List;

public class IPFSCluster {

    private List<String> clusters;

    public List<String> getClusters() {
        return clusters;
    }

    public void setClusters(List<String> clusters) {
        this.clusters = clusters;
    }

    public IPFSCluster(List<String> clusters) {
        this.clusters = clusters;
    }

    public IPFSCluster() {
    }
}
