package com.example;

import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@SpringBootApplication
public class TestcontainerSpringDataCouchbaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcontainerSpringDataCouchbaseApplication.class, args);
	}
}

class User {
	@Id
	private String username;
	private String firstName;
	private String lastName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
interface UserRepository extends CouchbasePagingAndSortingRepository<User, String>{}