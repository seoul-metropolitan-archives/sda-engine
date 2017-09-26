package com.bgf.shbank.config;

import ch.qos.logback.classic.LoggerContext;
import com.bgf.shbank.config.constant.PackageNames;
import com.bgf.shbank.core.model.ModelMapperConverter;
import io.onsemiro.config.AXBootContextConfig;
import io.onsemiro.core.code.PackageManager;
import io.onsemiro.core.db.dbcp.AXBootDataSourceFactory;
import io.onsemiro.core.db.monitor.SqlMonitoringService;
import io.onsemiro.core.domain.log.AXBootErrorLogService;
import io.onsemiro.core.logging.AXBootLogbackAppender;
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
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
@EnableJpaRepositories(basePackages = {PackageManager.CORE_BASE, PackageNames.BASE}, entityManagerFactoryRef = "entityManagerFactory")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan(basePackages = {PackageManager.CORE_DOMAIN, PackageNames.DOMAIN}, markerInterface = MyBatisMapper.class)
public class AppConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @PostConstruct
    private void setupPackageManager() {
        PackageManager.BASE = PackageNames.BASE;
        PackageManager.DOMAIN = PackageNames.DOMAIN;
        PackageManager.CONTROLLER = PackageNames.CONTROLLER;
        PackageManager.CONTROLLER_API = PackageNames.CONTROLLER_API;
        PackageManager.CONTROLLER_VIEW = PackageNames.CONTROLLER_VIEW;
    }

    @Bean
    @Primary
    public DataSource dataSource(@Named(value = "axBootContextConfig") AXBootContextConfig axBootContextConfig) throws Exception {
        return AXBootDataSourceFactory.create(axBootContextConfig.getDataSourceConfig());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(ModelMapperConverter.toStringDate);

        return modelMapper;
    }

    @Bean
    public MapperFactory modelFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

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

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public SpringManagedTransactionFactory managedTransactionFactory() {
        return new SpringManagedTransactionFactory();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(SpringManagedTransactionFactory springManagedTransactionFactory, DataSource dataSource, AXBootContextConfig axBootContextConfig) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(PackageNames.DOMAIN);
        Class<?>[] typeAliases = new Class<?>[157];
        typeAliases[0] = com.bgf.shbank.domain.mng.equip.sh02001130.Sh02001130.class;
        typeAliases[1] = com.bgf.shbank.domain.mng.equip.sh02001130.Sh02001130ModelMapper.class;
        typeAliases[2] = com.bgf.shbank.domain.mng.equip.sh02001210.Sh02001210.class;
        typeAliases[3] = com.bgf.shbank.domain.mng.equip.sh02001210.Sh02001210ModelMapper.class;
        typeAliases[4] = com.bgf.shbank.domain.mng.equip.sh02001140.Sh02001140.class;
        typeAliases[5] = com.bgf.shbank.domain.mng.equip.sh02001140.Sh02001140ModelMapper.class;
        typeAliases[6] = com.bgf.shbank.domain.mng.equip.sh02001150.Sh02001150.class;
        typeAliases[7] = com.bgf.shbank.domain.mng.equip.sh02001150.Sh02001150ModelMapper.class;
        typeAliases[8] = com.bgf.shbank.domain.mng.equip.sh02001160.Sh02001160.class;
        typeAliases[9] = com.bgf.shbank.domain.mng.equip.sh02001160.Sh02001160ModelMapper.class;
        typeAliases[10] = com.bgf.shbank.domain.mng.equip.sh02001190.Sh02001190.class;
        typeAliases[11] = com.bgf.shbank.domain.mng.equip.sh02001190.Sh02001190ModelMapper.class;
        typeAliases[12] = com.bgf.shbank.domain.mng.equip.sh02001170.Sh02001170.class;
        typeAliases[13] = com.bgf.shbank.domain.mng.equip.sh02001170.Sh02001170ModelMapper.class;
        typeAliases[14] = com.bgf.shbank.domain.mng.equip.sh02001180.Sh02001180.class;
        typeAliases[15] = com.bgf.shbank.domain.mng.equip.sh02001180.Sh02001180ModelMapper.class;
        typeAliases[16] = com.bgf.shbank.domain.mng.equip.sh02001230.Sh02001230.class;
        typeAliases[17] = com.bgf.shbank.domain.mng.equip.sh02001230.Sh02001230ModelMapper.class;
        typeAliases[18] = com.bgf.shbank.domain.mng.equip.sh02001280.Sh02001280.class;
        typeAliases[19] = com.bgf.shbank.domain.mng.equip.sh02001280.Sh02001280ModelMapper.class;
        typeAliases[20] = com.bgf.shbank.domain.mng.equip.sh02001290.Sh02001290.class;
        typeAliases[21] = com.bgf.shbank.domain.mng.equip.sh02001290.Sh02001290ModelMapper.class;
        typeAliases[22] = com.bgf.shbank.domain.mng.equip.corner_status.CornerStatus.class;
        typeAliases[23] = com.bgf.shbank.domain.mng.equip.corner_status.CornerStatusModelMapper.class;
        typeAliases[24] = com.bgf.shbank.domain.mng.equip.terminal_status.TerminalStatus.class;
        typeAliases[25] = com.bgf.shbank.domain.mng.equip.terminal_status.TerminalStatusModelMapper.class;
        typeAliases[26] = com.bgf.shbank.domain.mng.equip.facility_status.FacilityStatus.class;
        typeAliases[27] = com.bgf.shbank.domain.mng.equip.facility_status.FacilityStatusModelMapper.class;
        typeAliases[28] = com.bgf.shbank.domain.mng.equip.sh02001220.Sh02001220.class;
        typeAliases[29] = com.bgf.shbank.domain.mng.equip.sh02001220.Sh02001220ModelMapper.class;
        typeAliases[30] = com.bgf.shbank.domain.mng.cash.sh03001110.Sh03001110.class;
        typeAliases[31] = com.bgf.shbank.domain.mng.cash.sh03001110.Sh03001110ModelMapper.class;
        typeAliases[32] = com.bgf.shbank.domain.mng.cash.sh03001120.Sh03001120.class;
        typeAliases[33] = com.bgf.shbank.domain.mng.cash.sh03001120.Sh03001120ModelMapper.class;
        typeAliases[34] = com.bgf.shbank.domain.mng.cash.sh03001130.Sh03001130.class;
        typeAliases[35] = com.bgf.shbank.domain.mng.cash.sh03001130.Sh03001130ModelMapper.class;
        typeAliases[36] = com.bgf.shbank.domain.mng.cash.sh03001140.Sh03001140.class;
        typeAliases[37] = com.bgf.shbank.domain.mng.cash.sh03001140.Sh03001140ModelMapper.class;
        typeAliases[38] = com.bgf.shbank.domain.mng.cash.sh03001150.Sh03001150.class;
        typeAliases[39] = com.bgf.shbank.domain.mng.cash.sh03001150.Sh03001150ModelMapper.class;
        typeAliases[40] = com.bgf.shbank.domain.mng.cash.sh03001160.Sh03001160.class;
        typeAliases[41] = com.bgf.shbank.domain.mng.cash.sh03001160.Sh03001160ModelMapper.class;
        typeAliases[42] = com.bgf.shbank.domain.mng.cash.sh03001170.Sh03001170.class;
        typeAliases[43] = com.bgf.shbank.domain.mng.cash.sh03001170.Sh03001170ModelMapper.class;
        typeAliases[44] = com.bgf.shbank.domain.mng.cash.sh03001180.Sh03001180.class;
        typeAliases[45] = com.bgf.shbank.domain.mng.cash.sh03001180.Sh03001180ModelMapper.class;
        typeAliases[46] = com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190.class;
        typeAliases[47] = com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190ModelMapper.class;
        typeAliases[48] = com.bgf.shbank.domain.mng.cash.sh03001200.Sh03001200.class;
        typeAliases[49] = com.bgf.shbank.domain.mng.cash.sh03001200.Sh03001200ModelMapper.class;
        typeAliases[50] = com.bgf.shbank.domain.mng.cash.sh03001210.Sh03001210.class;
        typeAliases[51] = com.bgf.shbank.domain.mng.cash.sh03001210.Sh03001210ModelMapper.class;
        typeAliases[52] = com.bgf.shbank.domain.mng.cash.sh03001220.Sh03001220.class;
        typeAliases[53] = com.bgf.shbank.domain.mng.cash.sh03001220.Sh03001220ModelMapper.class;
        typeAliases[54] = com.bgf.shbank.domain.mng.cash.sh03001230.Sh03001230.class;
        typeAliases[55] = com.bgf.shbank.domain.mng.cash.sh03001230.Sh03001230ModelMapper.class;
        typeAliases[56] = com.bgf.shbank.domain.mng.cash.jisa_sije_close.JisaSijeClose.class;
        typeAliases[57] = com.bgf.shbank.domain.mng.cash.jisa_sije_close.JisaSijeCloseModelMapper.class;
        typeAliases[58] = com.bgf.shbank.domain.mng.etc.sh04001110.Sh04001110.class;
        typeAliases[59] = com.bgf.shbank.domain.mng.etc.sh04001110.Sh04001110ModelMapper.class;
        typeAliases[60] = com.bgf.shbank.domain.mng.etc.sh04001120.Sh04001120.class;
        typeAliases[61] = com.bgf.shbank.domain.mng.common.SearchTerminalMapper.class;
        typeAliases[62] = com.bgf.shbank.domain.mng.etc.sh04001130.Sh04001130.class;
        typeAliases[63] = com.bgf.shbank.domain.mng.etc.sh04001130.Sh04001130ModelMapper.class;
        typeAliases[64] = com.bgf.shbank.domain.mng.etc.sh04001140.Sh04001140.class;
        typeAliases[65] = com.bgf.shbank.domain.mng.etc.sh04001140.Sh04001140ModelMapper.class;
        typeAliases[66] = com.bgf.shbank.domain.mng.etc.sh04001150.Sh04001150.class;
        typeAliases[67] = com.bgf.shbank.domain.mng.etc.sh04001150.Sh04001150ModelMapper.class;
        typeAliases[68] = com.bgf.shbank.domain.mng.etc.sh04001170.Sh04001170.class;
        typeAliases[69] = com.bgf.shbank.domain.mng.etc.sh04001170.Sh04001170ModelMapper.class;
        typeAliases[70] = com.bgf.shbank.domain.mng.etc.sh04001180.Sh04001180.class;
        typeAliases[71] = com.bgf.shbank.domain.mng.etc.sh04001180.Sh04001180ModelMapper.class;
        typeAliases[72] = com.bgf.shbank.domain.mng.etc.sh04001190.Sh04001190.class;
        typeAliases[73] = com.bgf.shbank.domain.mng.etc.sh04001190.Sh04001190ModelMapper.class;
        typeAliases[74] = com.bgf.shbank.domain.mng.etc.sh04001200.Sh04001200.class;
        typeAliases[75] = com.bgf.shbank.domain.mng.etc.sh04001200.Sh04001200ModelMapper.class;
        typeAliases[76] = com.bgf.shbank.domain.mng.etc.agent_mng.AgentMng.class;
        typeAliases[77] = com.bgf.shbank.domain.mng.etc.agent_mng.AgentMngModelMapper.class;
        typeAliases[78] = com.bgf.shbank.domain.mng.equip.sh05001110.Sh05001110.class;
        typeAliases[79] = com.bgf.shbank.domain.mng.equip.sh05001110.Sh05001110ModelMapper.class;
        typeAliases[80] = com.bgf.shbank.domain.mng.equip.sh05001120.Sh05001120.class;
        typeAliases[81] = com.bgf.shbank.domain.mng.equip.sh05001120.Sh05001120ModelMapper.class;
        typeAliases[82] = com.bgf.shbank.domain.mng.equip.sh05001130.Sh05001130.class;
        typeAliases[83] = com.bgf.shbank.domain.mng.equip.sh05001130.Sh05001130ModelMapper.class;
        typeAliases[84] = com.bgf.shbank.domain.mng.equip.sh05001140.Sh05001140.class;
        typeAliases[85] = com.bgf.shbank.domain.mng.equip.sh05001140.Sh05001140ModelMapper.class;
        typeAliases[86] = com.bgf.shbank.domain.mng.equip.overhaul_plan.OverhaulPlan.class;
        typeAliases[87] = com.bgf.shbank.domain.mng.equip.overhaul_plan.OverhaulPlanModelMapper.class;
        typeAliases[88] = com.bgf.shbank.domain.mng.error.sh01001110.Sh01001110.class;
        typeAliases[89] = com.bgf.shbank.domain.mng.error.sh01001110.Sh01001110ModelMapper.class;
        typeAliases[90] = com.bgf.shbank.domain.mng.error.sh01001120.Sh01001120.class;
        typeAliases[91] = com.bgf.shbank.domain.mng.error.sh01001120.Sh01001120ModelMapper.class;
        typeAliases[92] = com.bgf.shbank.domain.mng.error.sh01001130.Sh01001130.class;
        typeAliases[93] = com.bgf.shbank.domain.mng.error.sh01001130.Sh01001130ModelMapper.class;
        typeAliases[94] = com.bgf.shbank.domain.mng.error.sh01001140.Sh01001140.class;
        typeAliases[95] = com.bgf.shbank.domain.mng.error.sh01001140.Sh01001140ModelMapper.class;
        typeAliases[96] = com.bgf.shbank.domain.mng.error.sh01001150.Sh01001150.class;
        typeAliases[97] = com.bgf.shbank.domain.mng.error.sh01001150.Sh01001150ModelMapper.class;
        typeAliases[98] = com.bgf.shbank.domain.mng.error.sh01001160.Sh01001160.class;
        typeAliases[99] = com.bgf.shbank.domain.mng.error.sh01001160.Sh01001160ModelMapper.class;
        typeAliases[100] = com.bgf.shbank.domain.mng.error.error_status.ErrorStatus.class;
        typeAliases[101] = com.bgf.shbank.domain.mng.error.error_status.ErrorStatusModelMapper.class;
        typeAliases[102] = com.bgf.shbank.domain.mng.error.sh01001180.Sh01001180.class;
        typeAliases[103] = com.bgf.shbank.domain.mng.error.sh01001180.Sh01001180ModelMapper.class;
        typeAliases[104] = com.bgf.shbank.domain.mng.error.sh01001230.Sh01001230.class;
        typeAliases[105] = com.bgf.shbank.domain.mng.error.sh01001230.Sh01001230ModelMapper.class;
        typeAliases[106] = com.bgf.shbank.domain.mng.error.minwon_mng.MinwonMng.class;
        typeAliases[107] = com.bgf.shbank.domain.mng.error.minwon_mng.MinwonMngModelMapper.class;
        typeAliases[108] = com.bgf.shbank.domain.mng.equip.sh02001100.Sh02001100.class;
        typeAliases[109] = com.bgf.shbank.domain.mng.equip.sh02001100.Sh02001100ModelMapper.class;
        typeAliases[110] = com.bgf.shbank.domain.mng.equip.sh02001110.Sh02001110.class;
        typeAliases[111] = com.bgf.shbank.domain.mng.equip.sh02001110.Sh02001110ModelMapper.class;
        typeAliases[112] = com.bgf.shbank.domain.mng.equip.sh02001200.Sh02001200.class;
        typeAliases[113] = com.bgf.shbank.domain.mng.equip.sh02001200.Sh02001200ModelMapper.class;
        typeAliases[114] = com.bgf.shbank.domain.mng.equip.sh02001120.Sh02001120.class;
        typeAliases[115] = com.bgf.shbank.domain.mng.equip.sh02001120.Sh02001120ModelMapper.class;
        typeAliases[116] = com.bgf.shbank.domain.mng.equip.sh02001270.Sh02001270.class;
        typeAliases[117] = com.bgf.shbank.domain.mng.equip.sh02001270.Sh02001270ModelMapper.class;
        typeAliases[118] = com.bgf.shbank.domain.mng.common.SearchTerminalVO.class;
        typeAliases[119] = com.bgf.shbank.domain.mng.equip.corner_manage.CornerManage.class;
        typeAliases[120] = com.bgf.shbank.domain.mng.equip.corner_manage.CornerManageModelMapper.class;
        typeAliases[121] = com.bgf.shbank.domain.mng.error.error_handle_mng.ErrorHandleMng.class;
        typeAliases[122] = com.bgf.shbank.domain.mng.error.error_handle_mng.ErrorHandleMngMapper.class;
        typeAliases[123] = com.bgf.shbank.domain.mng.error.sh01001190.Sh01001190.class;
        typeAliases[124] = com.bgf.shbank.domain.mng.error.sh01001190.Sh01001190ModelMapper.class;
        typeAliases[125] = com.bgf.shbank.domain.mng.error.sh01001240.Sh01001240.class;
        typeAliases[126] = com.bgf.shbank.domain.mng.error.sh01001240.Sh01001240ModelMapper.class;
        typeAliases[127] = com.bgf.shbank.domain.mng.cash.sh03001110.Sh03001110VO.class;
        typeAliases[128] = com.bgf.shbank.domain.mng.cash.sh03001180.Sh03001180ExcelVO.class;

        typeAliases[129] = com.bgf.shbank.domain.mng.sla.sh_sla_10.ShSla10.class;
        typeAliases[130] = com.bgf.shbank.domain.mng.sla.sh_sla_10.ShSla10Mapper.class;

        typeAliases[131] = com.bgf.shbank.domain.mng.sla.sh_sla_20.ShSla20.class;
        typeAliases[132] = com.bgf.shbank.domain.mng.sla.sh_sla_20.ShSla20Mapper.class;

        typeAliases[133] = com.bgf.shbank.domain.mng.sla.sh_sla_30.ShSla30.class;
        typeAliases[134] = com.bgf.shbank.domain.mng.sla.sh_sla_30.ShSla30Mapper.class;

        typeAliases[135] = com.bgf.shbank.domain.mng.sla.sh_sla_40.ShSla40.class;
        typeAliases[136] = com.bgf.shbank.domain.mng.sla.sh_sla_40.ShSla40Mapper.class;

        typeAliases[137] = com.bgf.shbank.domain.mng.sla.sh_sla_50.ShSla50.class;
        typeAliases[138] = com.bgf.shbank.domain.mng.sla.sh_sla_50.ShSla50Mapper.class;

        typeAliases[139] = com.bgf.shbank.domain.mng.sla.sh_sla_60.ShSla60.class;
        typeAliases[140] = com.bgf.shbank.domain.mng.sla.sh_sla_60.ShSla60Mapper.class;

        typeAliases[141] = com.bgf.shbank.domain.mng.sla.sh_sla_70.ShSla70.class;
        typeAliases[142] = com.bgf.shbank.domain.mng.sla.sh_sla_70.ShSla70Mapper.class;

        typeAliases[143] = com.bgf.shbank.domain.mng.sla.sh_sla_80.ShSla80.class;
        typeAliases[144] = com.bgf.shbank.domain.mng.sla.sh_sla_80.ShSla80Mapper.class;

        typeAliases[145] = com.bgf.shbank.domain.mng.sla.sh_sla_f0.ShSlaF0.class;
        typeAliases[146] = com.bgf.shbank.domain.mng.sla.sh_sla_f0.ShSlaF0Mapper.class;

        typeAliases[147] = com.bgf.shbank.domain.mng.sla.sh_sla_a0.ShSlaA0.class;
        typeAliases[148] = com.bgf.shbank.domain.mng.sla.sh_sla_a0.ShSlaA0Mapper.class;

        typeAliases[149] = com.bgf.shbank.domain.mng.sla.sh_sla_b0.ShSlaB0.class;
        typeAliases[150] = com.bgf.shbank.domain.mng.sla.sh_sla_b0.ShSlaB0Mapper.class;

        typeAliases[151] = com.bgf.shbank.domain.mng.sla.sh_sla_e0.ShSlaE0.class;
        typeAliases[152] = com.bgf.shbank.domain.mng.sla.sh_sla_e0.ShSlaE0Mapper.class;

        typeAliases[153] = com.bgf.shbank.domain.mng.sla.sh_sla_g1.ShSlaG1.class;
        typeAliases[154] = com.bgf.shbank.domain.mng.sla.sh_sla_g1.ShSlaG1Mapper.class;

        typeAliases[155] = com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190VO.class;
        typeAliases[156] = com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190FormVO.class;

        /*typeAliases[155]  = io.onsemiro.core.domain.user.SessionUser.class;
        typeAliases[156]  = io.onsemiro.core.vo.ScriptSessionVO.class;*/

        sqlSessionFactoryBean.setTypeAliases(typeAliases);
        sqlSessionFactoryBean.setTypeHandlers(axBootContextConfig.getMyBatisTypeHandlers());
        sqlSessionFactoryBean.setTransactionFactory(springManagedTransactionFactory);
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:mybatis/**/*.xml"));
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

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws ClassNotFoundException {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public JdbcMetadataService jdbcMetadataService() {
        return new JdbcMetadataService();
    }

    @Bean(name = "axBootContextConfig")
    public AXBootContextConfig axBootContextConfig() {
        return new AXBootContextConfig();
    }

    @Bean
    public SqlMonitoringService sqlMonitoringService(DataSource dataSource) throws Exception {
        return new SqlMonitoringService(dataSource);
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        return new LocalValidatorFactoryBean();
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
