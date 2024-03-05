package com.ssarylog.api.exception;

public class InvalidSignInformation extends SsarylogException{
    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public InvalidSignInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
