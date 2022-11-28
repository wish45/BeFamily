package com.befam.api.domain.user.service;


import com.befam.api.domain.exception.ServiceError;
import com.befam.api.domain.exception.ServiceException;
import com.befam.api.domain.user.dto.UserDto;
import com.befam.api.domain.user.dto.UserPassDto;
import com.befam.api.domain.user.entity.User;
import com.befam.api.domain.user.repository.UserRepository;
import com.befam.api.domain.utils.UserUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signUp(UserDto requestDto) throws ServiceException {
        userRepository.findById(requestDto.getUserId())
                .ifPresent(o -> {
                    throw new ServiceException(ServiceError.CONFLICT_DATA, "이미 등록된 아이디입니다: " + o.getUserId());
                });

        requestDto.setUserPw(passwordEncoder.encode(requestDto.getUserPw()));
        return UserDto.from(userRepository.save(requestDto.toEntity()));
    }

    @Transactional
    public UserDto signIn(UserDto requestDto) throws ServiceException {
        User user = null;
        if("EMAIL".equals(requestDto.getUserType())){
            user = userRepository.findById(requestDto.getEmail())
                    .orElseThrow(() -> new ServiceException(ServiceError.UNAUTHORIZED, "이메일을 찾을 수 없습니다: "));
        }else{
            user = userRepository.findById(requestDto.getPhoneNumber())
                    .orElseThrow(() -> new ServiceException(ServiceError.UNAUTHORIZED, "핸드폰 번호를 찾을 수 없습니다: "));
        }

        if (!passwordEncoder.matches(requestDto.getUserPw(), user.getUserPw())) {
            throw new ServiceException(ServiceError.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.setFnlLoginDttm(Timestamp.valueOf(LocalDateTime.now()));
        return UserDto.from(user);
    }

    @Transactional
    public UserDto updatePass(UserPassDto requestDto) throws ServiceException{
        UserUtil.getCurrentLoginUser().ifPresent(o -> {
            requestDto.setUserId(o.getUserId());
        });

        User userDto = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceError.UNAUTHORIZED, "아이디를 찾을 수 없습니다: "));

        if(!passwordEncoder.matches(requestDto.getExistPwd(),requestDto.getRetryPwd())){
            throw new ServiceException(ServiceError.UNAUTHORIZED, "두 비밀번호가 일치하지 않습니다.");
        }

        userDto.setUserPw(passwordEncoder.encode(requestDto.getRetryPwd()));
        
        return UserDto.from(userDto);
    }

    @Transactional(readOnly = true)
    public UserDto selectMyInfo(UserDto requestDto) throws ServiceException {
        UserUtil.getCurrentLoginUser().ifPresent(o -> {
            requestDto.setUserId(o.getUserId());
        });

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceError.UNAUTHORIZED, "아이디를 찾을 수 없습니다: "));

        user.setFnlLoginDttm(Timestamp.valueOf(LocalDateTime.now()));
        return UserDto.from(user);
    }
}