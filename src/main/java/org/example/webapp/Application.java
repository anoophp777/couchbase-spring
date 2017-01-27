package org.example.webapp;

import com.couchbase.client.java.Bucket;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.CouchbaseHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.core.CouchbaseOperations;

/**
 * @author arungupta
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@SpringBootApplication
public class Application {

    @Bean
    CouchbaseHealthIndicator couchbaseHealthIndicator(CouchbaseOperations couchbaseOperations) {
        return new CouchbaseHealthIndicator(couchbaseOperations);
    }

    @Bean
    HealthIndicator couchbaseBucketHealthIndicator(Bucket bucket) {
        return () -> (bucket.isClosed() ? Health.down() : Health.up()).build();
    }

    @Bean
    CommandLineRunner demo(BookRepository repository) {
        return args -> {
            String isbn = "978-1-4919-1889-0";
            repository.save(new Book(isbn, "Minecraft Modding with Forge", "29.99"));

            Book book = repository.findByIsbn(isbn);
            System.out.println(book);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
