package com.befam.api.domain.user.controller;


import com.befam.api.domain.user.dto.UserDto;
import com.befam.api.domain.user.dto.UserPassDto;
import com.befam.api.domain.user.service.UserService;
import com.befam.api.domain.user.support.UserValidGroups;
import com.befam.api.domain.utils.ApiResponse;
import com.befam.api.domain.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse.ApiResult<UserDto>> signUp(@RequestBody @Validated(
            value = {UserValidGroups.commonValidGroup.class, UserValidGroups.registValidGroup.class}) UserDto requestDto) {
        return new ResponseEntity<>(ApiResponse.success(userService.signUp(requestDto)), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse.ApiResult<UserDto>> signIn(@RequestBody @Validated(
            UserValidGroups.commonValidGroup.class) UserDto requestDto) {
        UserDto result = userService.signIn(requestDto);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(JwtUtil.ACCESS_TOKEN, JwtUtil.create(result));
        return new ResponseEntity<>(ApiResponse.success(result), headers, HttpStatus.OK);
    }

    //비밀번호 변경
    @PostMapping("/updatePass")
    public ResponseEntity<ApiResponse.ApiResult<UserDto>> updatePass(@RequestBody @Validated(
            value = {UserValidGroups.commonValidGroup.class, UserValidGroups.registValidGroup.class}) UserPassDto requestDto) {
        return new ResponseEntity<>(ApiResponse.success(userService.updatePass(requestDto)), HttpStatus.OK);
    }
    
    //자기회원정보 조회
    @GetMapping("/myInfo")
    public ResponseEntity<ApiResponse.ApiResult<UserDto>> selectMyInfo(@RequestBody @Validated(
            UserValidGroups.commonValidGroup.class) UserDto requestDto) {
        UserDto result = userService.selectMyInfo(requestDto);
        return new ResponseEntity<>(ApiResponse.success(result), HttpStatus.OK);
    }

}
