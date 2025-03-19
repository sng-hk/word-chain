package snghk.word_chain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class WordChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordChainApplication.class, args);
    }

}
