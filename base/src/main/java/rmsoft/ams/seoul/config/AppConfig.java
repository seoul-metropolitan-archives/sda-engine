package rmsoft.ams.seoul.config;

import io.onsemiro.config.AXBootContextConfig;
import io.onsemiro.core.code.PackageManager;
import io.onsemiro.core.db.dbcp.AXBootDataSourceFactory;
import io.onsemiro.core.db.monitor.SqlMonitoringService;
import io.onsemiro.core.model.ModelMapperConverter;
import io.onsemiro.core.model.extract.service.jdbc.JdbcMetadataService;
import io.onsemiro.core.mybatis.MyBatisMapper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import rmsoft.ams.seoul.config.constant.PackageNames;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * The type App config.
 */
@Slf4j
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
@EnableJpaRepositories(basePackages = {PackageManager.CORE_BASE, PackageNames.BASE}, entityManagerFactoryRef = "entityManagerFactory")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan(basePackages = {PackageManager.CORE_DOMAIN, PackageNames.BASE}, markerInterface = MyBatisMapper.class)
@EnableAsync
public class AppConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Value("${taskExecutor.core-pool-size}")
    private int corePoolSize;

    @Value("${taskExecutor.max-pool-size}")
    private int maxPoolSize;

    @Value("${taskExecutor.queue-capacity}")
    private int queueCapacity;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @PostConstruct
    private void setupPackageManager() {
        PackageManager.BASE = PackageNames.BASE;
    }

    /**
     * Data source data source.
     *
     * @param axBootContextConfig the ax boot context config
     * @return the data source
     * @throws Exception the exception
     */
    @Bean
    @Primary
    public DataSource dataSource(@Named(value = "axBootContextConfig") AXBootContextConfig axBootContextConfig) throws Exception {
        return AXBootDataSourceFactory.create(axBootContextConfig.getDataSourceConfig());
    }

    /**
     * Property placeholder configurer property sources placeholder configurer.
     *
     * @return the property sources placeholder configurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Model mapper model mapper.
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(ModelMapperConverter.toStringDate);

        return modelMapper;
    }

    /**
     * Model factory mapper factory.
     *
     * @return the mapper factory
     */
    @Bean
    public MapperFactory modelFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    /**
     * Entity manager factory local container entity manager factory bean.
     *
     * @param dataSource          the data source
     * @param axBootContextConfig the ax boot context config
     * @return the local container entity manager factory bean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, AXBootContextConfig axBootContextConfig) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan(PackageManager.CORE_BASE, PackageNames.BASE);

        AXBootContextConfig.DataSourceConfig.HibernateConfig hibernateConfig = axBootContextConfig.getDataSourceConfig().getHibernateConfig();
        entityManagerFactory.setJpaVendorAdapter(hibernateConfig.getHibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(hibernateConfig.getAdditionalProperties());

        return entityManagerFactory;
    }

    /**
     * Exception translation persistence exception translation post processor.
     *
     * @return the persistence exception translation post processor
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Managed transaction factory spring managed transaction factory.
     *
     * @return the spring managed transaction factory
     */
    @Bean
    public SpringManagedTransactionFactory managedTransactionFactory() {
        return new SpringManagedTransactionFactory();
    }

    /**
     * Sql session factory sql session factory.
     *
     * @param springManagedTransactionFactory the spring managed transaction factory
     * @param dataSource                      the data source
     * @param axBootContextConfig             the ax boot context config
     * @return the sql session factory
     * @throws Exception the exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(SpringManagedTransactionFactory springManagedTransactionFactory, DataSource dataSource, AXBootContextConfig axBootContextConfig) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(PackageNames.BASE);
        //sqlSessionFactoryBean.setTypeAliases(typeAliases);
        sqlSessionFactoryBean.setTypeHandlers(axBootContextConfig.getMyBatisTypeHandlers());
        sqlSessionFactoryBean.setTransactionFactory(springManagedTransactionFactory);
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath*:mybatis/**/*.xml"));
        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider());

        return sqlSessionFactoryBean.getObject();
    }

    private VendorDatabaseIdProvider databaseIdProvider() {

        Properties props = new Properties();

        props.setProperty("Oracle", "oracle");
        props.setProperty("MySQL", "mysql");
        props.setProperty("PostgreSQL", "postgresql");

        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        databaseIdProvider.setProperties(props);

        return databaseIdProvider;
    }

    private org.apache.ibatis.session.Configuration mybatisConfig() {

        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();

        config.setCacheEnabled(false);
        config.setUseGeneratedKeys(true);
        config.setDefaultExecutorType(ExecutorType.REUSE);
        config.setAggressiveLazyLoading(false);
        config.setMapUnderscoreToCamelCase(true);

        return config;
    }

    /*@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

        mapperScannerConfigurer.setBasePackage(Constants.DOMAIN_PACKAGE);
        mapperScannerConfigurer.setMarkerInterface(MyBatisMapper.class);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");

        return mapperScannerConfigurer;
    }*/

    /**
     * Transaction manager platform transaction manager.
     *
     * @param entityManagerFactory the entity manager factory
     * @return the platform transaction manager
     * @throws ClassNotFoundException the class not found exception
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws ClassNotFoundException {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

    /**
     * B crypt password encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    /**
     * Jdbc metadata service jdbc metadata service.
     *
     * @return the jdbc metadata service
     */
    @Bean
    public JdbcMetadataService jdbcMetadataService() {
        return new JdbcMetadataService();
    }

    /**
     * Ax boot context config ax boot context config.
     *
     * @return the ax boot context config
     */
    @Bean(name = "axBootContextConfig")
    public AXBootContextConfig axBootContextConfig() {
        return new AXBootContextConfig();
    }

    /**
     * Sql monitoring service sql monitoring service.
     *
     * @param dataSource the data source
     * @return the sql monitoring service
     * @throws Exception the exception
     */
    @Bean
    public SqlMonitoringService sqlMonitoringService(DataSource dataSource) throws Exception {
        return new SqlMonitoringService(dataSource);
    }

    /**
     * Validator factory bean local validator factory bean.
     *
     * @return the local validator factory bean
     */
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }


    /**
     * 여기 설정된 함수 이름이 Thread를 돌렸을때의 이름으로 보여짐
     *
     * @return task executor
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //taskExecutor.setDaemon(true);
        return taskExecutor;
    }


    /*@Bean
    public AXBootLogbackAppender axBootLogbackAppender(AXBootErrorLogService axBootErrorLogService, AXBootContextConfig axBootContextConfig) throws Exception {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        AXBootLogbackAppender axBootLogbackAppender = new AXBootLogbackAppender(axBootErrorLogService, axBootContextConfig);
        axBootLogbackAppender.setContext(loggerContext);
        axBootLogbackAppender.setName("axBootLogbackAppender");
        axBootLogbackAppender.start();
        loggerContext.getLogger("ROOT").addAppender(axBootLogbackAppender);

        return axBootLogbackAppender;
    }*/


}

/*@Bean
    public String databaseId() {

        try {

            Connection conn =  dataSource().getConnection();

            DatabaseMetaData databaseMetaData = conn.getMetaData();

            String databaseId = sqlSessionFactory().getConfiguration().getDatabaseId();
            String productName = databaseMetaData.getDatabaseProductName();

            conn.close();

            log.info("[Database ProductName] {}", productName);
            log.info("[Database ID] {}", databaseId);

            return databaseId;

        } catch (Exception e) {
            log.error("[printDatabaseId] {}", e.getMessage());
            return null;
        }
    }*/
