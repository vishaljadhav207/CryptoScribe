package com.crypto.service;

import com.crypto.response.ApiResponse;

public interface ChatbotService {
    ApiResponse getCoinDetails(String prompt) throws Exception;
    String simpleChat(String prompt);
}
