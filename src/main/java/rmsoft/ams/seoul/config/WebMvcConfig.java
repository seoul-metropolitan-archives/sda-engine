package rmsoft.ams.seoul.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.onsemiro.core.filters.AXBootCorsFilter;
import io.onsemiro.core.filters.MultiReadableHttpServletRequestFilter;
import io.onsemiro.core.parameter.RequestParamsArgumentResolver;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver();

        pageableHandlerMethodArgumentResolver.setPageParameterName("pageNumber");
        pageableHandlerMethodArgumentResolver.setSizeParameterName("pageSize");

        argumentResolvers.add(new RequestParamsArgumentResolver());
        argumentResolvers.add(pageableHandlerMethodArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_GETTERS, true);
        objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));

        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

    @Bean
    public FilterRegistrationBean multiReadableHttpServletRequestFilterRegistrationBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new MultiReadableHttpServletRequestFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

    @Bean
    public AXBootCorsFilter corsFilter() {
        return new AXBootCorsFilter();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(objectMapper());
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();

        resourceBundleMessageSource.setBasename("classpath:messages/messages");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setFallbackToSystemLocale(true);

        return resourceBundleMessageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {

        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();

        cookieLocaleResolver.setCookieName("language");
        cookieLocaleResolver.setCookiePath("/");
        cookieLocaleResolver.setDefaultLocale(new Locale("ko_KR"));

        return cookieLocaleResolver;
    }

    @Bean
    public ViewResolver viewResolver() {

        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");

        return resolver;
    }

    private TemplateEngine templateEngine() {

        SpringTemplateEngine engine = new SpringTemplateEngine();

        engine.setTemplateResolver(templateResolver());
        engine.setEnableSpringELCompiler(false);
        engine.addDialect(new LayoutDialect(new GroupingStrategy()));

        return engine;
    }

    private ITemplateResolver templateResolver() {

        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();

        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("classpath:/templates");
        resolver.setTemplateMode(TemplateMode.HTML);

        //if (PhaseUtils.isDevelopmentMode()) {
        //resolver.setCacheable(true);
        //}
        return resolver;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*@Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager);
        contentNegotiatingViewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);

        List<View> views = new ArrayList<>();
        ContentTypeSwitchableMappingJackson2JsonView contentTypeSwitchableMappingJackson2JsonView = new ContentTypeSwitchableMappingJackson2JsonView();
        contentTypeSwitchableMappingJackson2JsonView.setEncoding(JsonEncoding.UTF8);
        contentTypeSwitchableMappingJackson2JsonView.setExtractValueFromSingleKeyModel(true);
        contentTypeSwitchableMappingJackson2JsonView.setPrefixJson(false);
        contentTypeSwitchableMappingJackson2JsonView.setObjectMapper(objectMapper());

        views.add(contentTypeSwitchableMappingJackson2JsonView);

        contentNegotiatingViewResolver.setDefaultViews(views);

        List<ViewResolver> viewResolvers = new ArrayList<>();

        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/jsp");
        internalResourceViewResolver.setSuffix(".jsp");

        viewResolvers.add(internalResourceViewResolver);

        contentNegotiatingViewResolver.setViewResolvers(viewResolvers);

        return contentNegotiatingViewResolver;
    }*/

}
