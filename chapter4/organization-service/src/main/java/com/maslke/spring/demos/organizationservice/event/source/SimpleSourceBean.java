package com.maslke.spring.demos.organizationservice.event.source;

import com.maslke.spring.demos.organizationservice.event.OutOrgChangeSource;
import com.maslke.spring.demos.organizationservice.model.OrganizationChangeModel;
import com.maslke.spring.demos.organizationservice.util.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {

    // private Source source;

    private OutOrgChangeSource outOrgChangeSource;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    @Autowired
    public SimpleSourceBean(OutOrgChangeSource outOrgChangeSource) {
        this.outOrgChangeSource = outOrgChangeSource;
    }

    public void publishOrgChange(String action, String orgId) {
        logger.info("Sending Kafka message {} for Organization Id:{}", action, orgId);
        OrganizationChangeModel change = new OrganizationChangeModel(action, orgId, UserContextHolder.getContext().getCorrelationId());
//        this.source.output().send(MessageBuilder.withPayload(change).build());
        this.outOrgChangeSource.outOrgChange().send(MessageBuilder.withPayload(change).build());
    }
}
