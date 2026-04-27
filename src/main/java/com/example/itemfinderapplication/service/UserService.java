package com.example.itemfinderapplication.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String normalizePhone(String phone) {
        return "+994" + phone;
    }
}
