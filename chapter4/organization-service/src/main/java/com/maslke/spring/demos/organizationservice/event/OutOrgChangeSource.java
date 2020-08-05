package com.maslke.spring.demos.organizationservice.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutOrgChangeSource {

    String OUT_ORG_CHANGE = "outOrgChange";

    @Output(OUT_ORG_CHANGE)
    MessageChannel outOrgChange();
}
