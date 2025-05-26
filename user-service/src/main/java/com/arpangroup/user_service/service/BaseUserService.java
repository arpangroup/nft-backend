package com.arpangroup.user_service.service;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.exception.IdNotFoundException;
import com.arpangroup.user_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class BaseUserService {

}
