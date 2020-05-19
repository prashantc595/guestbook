package guestbook;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import guestbook.jdbc.GuestBookJPARepository;
import guestbook.jdbc.GuestBookRepository;

@Configuration
@EnableJpaRepositories(basePackages = "guestbook.jdbc")
@EnableTransactionManagement
public class GuestBookConfig {

	@Bean
	@Scope("prototype")
	@DependsOn("createRateLimiter")
	public SpamCheckerDecorator createSpamCheckerDecorator() {
		return new SpamCheckerDecorator();
	}

	@Bean
	public FrenchSpamChecker createFrenchSpamChecker() {
		return new FrenchSpamChecker();
	}

	@Bean
	public EnglishSpamChecker createEnglishSpamChecker() {
		return new EnglishSpamChecker();
	}

	@Bean
	public RateLimiter createRateLimiter() {
		return new RateLimiter();
	}

	@Bean
	public GuestBookRepository createGuestBookRepository() {
		return new GuestBookRepository();
	}

	@Bean
	public JdbcTemplate createjdbcTemplate() {
		return new JdbcTemplate(createDataSource());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("guestbook.model");
		factory.setDataSource(createDataSource());
		return factory;
	}

	@Bean
	public DataSource createDataSource() {
		EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScripts("classpath:Table.properties").build();
		return embeddedDatabase;
	}

	@Bean
	TransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
		return transactionManager;
	}

	@Bean("guestBookService")
	@Scope("prototype")
	public GuestbookService createGuestbookService(@Value("${computername}") String tenantName,
			GuestBookJPARepository guestBookJPARepository) {
		return new GuestbookService(tenantName, createSpamCheckerDecorator(), createRateLimiter(),
				guestBookJPARepository);
	}
}
