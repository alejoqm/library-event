package com.library.event.producer.ports.input;

public interface PublishBookUseCase {
    void publish(PublishBookCommand command);

}
