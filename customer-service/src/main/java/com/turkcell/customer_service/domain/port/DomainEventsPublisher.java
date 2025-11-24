package com.turkcell.customer_service.domain.port;

import com.turkcell.customer_service.domain.event.AddressChangedEvent;
import com.turkcell.customer_service.domain.event.EmailChangedEvent;
import com.turkcell.customer_service.domain.event.PhoneNumberChangedEvent;

public interface DomainEventsPublisher {

    void publish(AddressChangedEvent event);

    void publish(EmailChangedEvent event);

    void publish(PhoneNumberChangedEvent event);

}
