package se.johantiden.myfeed.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NotFound404 extends RuntimeException {
    public NotFound404(String message) {
        super(message);
    }
}
