package com.example;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class TestcontainerSpringDataCouchbaseApplicationTests extends AbstractSPDataTestConfig {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Bucket bucket;

	@Test
	public void testRepository() {
		User user = new User();
		user.setUsername("alovelace");
		user.setFirstName("Ada");
		user.setLastName("Lovelace");
		userRepository.save(user);

		JsonDocument doc = bucket.get("alovelace");
		Assert.assertNotNull(doc);
	}

}
