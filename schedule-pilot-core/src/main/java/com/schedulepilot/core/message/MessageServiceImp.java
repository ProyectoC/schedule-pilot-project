package com.schedulepilot.core.message;

import com.schedulepilot.core.config.TwilioConfig;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceImp implements MessageService {

    public static final Logger LOGGER = LogManager.getLogger(MessageServiceImp.class);

    private final TwilioConfig twilioConfig;

    public MessageServiceImp(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendMessage(String phoneNumber, String message) throws SchedulePilotException {
        try {
            Twilio.init(twilioConfig.getAccount().getSid(), twilioConfig.getAuthToken());
            Message messageTwilio = Message.creator(new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioConfig.getAccount().getNumber()),
                    message).create();
            LOGGER.info("Send message: {}", messageTwilio.getSid());
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new SchedulePilotException(ex);
        }

    }
}
