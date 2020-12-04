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
import com.schedulepilot.core.entities.model.ItemStatusEntity;
import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import com.schedulepilot.core.entities.model.TicketCheckStatusEntity;
import com.schedulepilot.core.notification.service.imp.NotificationConsumer;
import com.schedulepilot.core.service.*;
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

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
@EnableRetry
@EnableConfigurationProperties({OAuthConfig.class, TokenConfig.class, JobConfig.class, NotificationConfig.class})
public class SchedulePilotCoreApplication implements CommandLineRunner {

    public static final Logger LOGGER = LogManager.getLogger(SchedulePilotCoreApplication.class);

    public static final String ACCOUNT_SID = "ACb00996a2f2fc7f7993b8e2d7ea8966c7";
    public static final String AUTH_TOKEN = "e48a34165a16e1ed04219a0cb45c6f22";

    @Autowired
    private TokenTypeService tokenTypeService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private RolAccountService rolAccountService;

    @Autowired
    private ProductRequestStatusService productRequestStatusService;

    @Autowired
    private TicketCheckStatusService ticketCheckStatusService;

    @Autowired
    private ItemStatusService itemStatusService;

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
    public GlobalListDinamic<TicketCheckStatusEntity> globalTicketCheckStatusList() {
        return new GlobalListDinamic<>(ticketCheckStatusService.getAll());
    }

    @Bean
    public GlobalListDinamic<ItemStatusEntity> globalItemStatusList() {
        return new GlobalListDinamic<>(itemStatusService.getAll());
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
        // this.sendMessage();
    }

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendMessage() {
        Message message = Message.creator(new PhoneNumber("+573057515066"),
                new PhoneNumber("+4672500913"),
                "Como vamos!! enviado desde los servicios de SchedulePilot, nos toca validar para pedirle los numeros " +
                        "de telefono a los usuarios para enviarlos").create();
        LOGGER.info("Send message: {}", message.getSid());
    }

}
