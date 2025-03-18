package snghk.word_chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import snghk.config.ProjectSecurityConfiguration;

@SpringBootApplication
@EntityScan(basePackages = {
        "snghk.domain.users.domain",
        "snghk.domain.contact.domain",
        "snghk.domain.bookmark.domain"
}) // 엔티티 패키지 명시
public class WordChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordChainApplication.class, args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProjectSecurityConfiguration.class);

        // 등록된 bean의 이름을 get
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        // bean 이름을 출력
        for(String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}
