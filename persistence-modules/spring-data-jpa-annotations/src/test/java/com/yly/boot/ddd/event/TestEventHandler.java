/**
 *
 */
package com.yly.boot.ddd.event;

import org.springframework.transaction.event.TransactionalEventListener;

interface TestEventHandler {
    @TransactionalEventListener
    void handleEvent(DomainEvent event);

}
