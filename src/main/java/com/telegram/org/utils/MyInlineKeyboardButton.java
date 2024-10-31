package com.telegram.org.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@SuppressWarnings("serial")
public class MyInlineKeyboardButton extends InlineKeyboardButton {
	
	private String callbackData;

    public MyInlineKeyboardButton(String text, String callbackData) {
        super(text);
        this.callbackData = callbackData;
    }

    public String getCallbackData() {
        return callbackData;
    }

}
