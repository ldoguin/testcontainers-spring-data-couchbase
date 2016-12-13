package com.example;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterInfo;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ldoguin on 12/13/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestcontainerSpringDataCouchbaseApplication.class, TestcontainerSpringDataCouchbaseApplicationTests.CouchbaseTestConfig.class})
public class AbstractSPDataTestConfig {

    public static final String clusterUser = "Administrator";
    public static final String clusterPassword = "password";

    @ClassRule
    public static CouchbaseContainer couchbaseContainer = new CouchbaseContainer()
            .withFTS(true)
            .withIndex(true)
            .withQuery(true)
            .withBeerSample(true)
            .withClusterUsername(clusterUser)
            .withClusterPassword(clusterPassword);

    @Configuration
    static class CouchbaseTestConfig extends AbstractCouchbaseConfiguration {

        private CouchbaseContainer couchbaseContainer;

        @PostConstruct
        public void init() throws Exception {
            couchbaseContainer = AbstractSPDataTestConfig.couchbaseContainer;
            BucketSettings settings = DefaultBucketSettings.builder()
                    .enableFlush(true).name("default").quota(100).replicas(0).type(BucketType.COUCHBASE).build();
            settings =  couchbaseCluster().clusterManager(clusterUser, clusterPassword).insertBucket(settings);
            waitForContainer();

        }

        public void waitForContainer(){
            CouchbaseWaitStrategy s = new CouchbaseWaitStrategy();
            s.withBasicCredentials(clusterUser, clusterPassword);
            s.waitUntilReady(couchbaseContainer);
        }

        @Override
        public CouchbaseEnvironment couchbaseEnvironment() {
            return couchbaseContainer.getCouchbaseEnvironnement();
        }

        @Override
        public Cluster couchbaseCluster() throws Exception {
            return couchbaseContainer.geCouchbaseCluster();
        }

        @Override
        public ClusterInfo couchbaseClusterInfo() throws Exception {
            Cluster cc = couchbaseCluster();
            ClusterManager manager = cc.clusterManager(clusterUser, clusterPassword);
            return manager.info();
        }

        @Override
        public Bucket couchbaseClient() throws Exception {
            return couchbaseContainer.geCouchbaseCluster().openBucket("default");
        }

        @Override
        protected List<String> getBootstrapHosts() {
            return Arrays.asList("localhost");
        }

        @Override
        protected String getBucketName() {
            return "default";
        }

        @Override
        protected String getBucketPassword() {
            return "";
        }
    }
}
