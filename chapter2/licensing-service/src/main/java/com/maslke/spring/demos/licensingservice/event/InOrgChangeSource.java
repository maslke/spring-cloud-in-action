package com.maslke.spring.demos.licensingservice.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

// 自定义通道，在不自定义的情况下，默认使用的是名称为input
// 自行定义的为 InOrgChange
public interface InOrgChangeSource {
    String ORGCHANGE = "InOrgChange";

    @Input(ORGCHANGE)
    SubscribableChannel orgs();
}
