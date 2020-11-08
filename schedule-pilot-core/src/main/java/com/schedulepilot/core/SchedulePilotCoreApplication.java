package com.schedulepilot.core;

import com.schedulepilot.core.audit.AuditorAwareImpl;
import com.schedulepilot.core.config.JobConfig;
import com.schedulepilot.core.config.NotificationConfig;
import com.schedulepilot.core.config.OAuthConfig;
import com.schedulepilot.core.config.TokenConfig;
import com.schedulepilot.core.constants.ApplicationConstants;
import com.schedulepilot.core.dto.model.ParameterDto;
import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.TokenTypeDto;
import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import com.schedulepilot.core.notification.service.imp.NotificationConsumer;
import com.schedulepilot.core.service.ParameterService;
import com.schedulepilot.core.service.ProductRequestStatusService;
import com.schedulepilot.core.service.RolAccountService;
import com.schedulepilot.core.service.TokenTypeService;
import com.schedulepilot.core.util.CommonUtil;
import com.schedulepilot.core.util.dto.GlobalListDinamic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import reactor.bus.EventBus;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
@EnableRetry
@EnableConfigurationProperties({OAuthConfig.class, TokenConfig.class, JobConfig.class, NotificationConfig.class})
public class SchedulePilotCoreApplication implements CommandLineRunner {

    public static final Logger LOGGER = LogManager.getLogger(SchedulePilotCoreApplication.class);

    @Autowired
    private TokenTypeService tokenTypeService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private RolAccountService rolAccountService;

    @Autowired
    private ProductRequestStatusService productRequestStatusService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private NotificationConsumer notificationConsumer;

    @Bean
    public GlobalListDinamic<ParameterDto> globalParameterList() {
        return new GlobalListDinamic<>(parameterService.getAll());
    }

    @Bean
    public GlobalListDinamic<TokenTypeDto> globalTokenTypesList() {
        return new GlobalListDinamic<>(tokenTypeService.getAll());
    }

    @Bean
    public GlobalListDinamic<RolAccountDto> globalRolAccountList() {
        return new GlobalListDinamic<>(rolAccountService.getAll());
    }

    @Bean
    public GlobalListDinamic<ProductRequestStatusEntity> globalProductRequestStatusList() {
        return new GlobalListDinamic<>(productRequestStatusService.getAll());
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean(CommonUtil.NAME_ASYNC_TASK_EXECUTOR)
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        LOGGER.info(CommonUtil.LOG_STARTING_APPLICATION_START, ApplicationConstants.APPLICATION_NAME);
        SpringApplication.run(SchedulePilotCoreApplication.class, args);
        LOGGER.info(CommonUtil.LOG_STARTING_APPLICATION_END, ApplicationConstants.APPLICATION_NAME);
    }

    @Override
    public void run(String... args) throws Exception {
        eventBus.on($("notificationConsumer"), notificationConsumer);
        LOGGER.info(CommonUtil.LOG_EXECUTING_APPLICATION, ApplicationConstants.APPLICATION_NAME);
        for (int i = 0; i < args.length; ++i) {
            LOGGER.info("args[{}]: {}", i, args[i]);
        }
    }

}
