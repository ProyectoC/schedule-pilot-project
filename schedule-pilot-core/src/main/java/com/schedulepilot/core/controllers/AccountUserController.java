package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.AccountUserConstants;
import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.UserAccountAuthRequest;
import com.schedulepilot.core.request.UserAccountChangePasswordRequest;
import com.schedulepilot.core.request.UserAccountCreateRequest;
import com.schedulepilot.core.request.UserAccountForgotPasswordRequest;
import com.schedulepilot.core.response.UserAccountAuthResponse;
import com.schedulepilot.core.service.ManageUserService;
import com.schedulepilot.core.util.CommonUtil;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = AccountUserConstants.REST_PATH_DEFAULT_V1)
public class AccountUserController {

    @Autowired
    private ManageUserService manageUserService;

    @ResponseBody
    @PostMapping(AccountUserConstants.CREATE_USER_ACCOUNT_REST)
    public ResponseEntity<ResponseDto<UserAccountDto>> createUserAccount(@RequestBody @Valid UserAccountCreateRequest userAccountCreateRequest) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageUserService.createUserAccount(userAccountCreateRequest)), HttpStatus.CREATED);
    }

    @GetMapping(AccountUserConstants.ACTIVATE_USER_ACCOUNT_REST)
    public ModelAndView activateUserAccount(@RequestParam(name = "tk") @Valid @NotNull @NotEmpty String token,
                                            @RequestParam(name = "id") @Valid @NotNull @NotEmpty Long id) throws SchedulePilotException {
        return new ModelAndView(CommonUtil.REDIRECT_ANOTHER_PAGE_COMMAND
                + this.manageUserService.activateUserAccount(token, id));
    }

    @ResponseBody
    @PostMapping(AccountUserConstants.AUTH_AUTHORIZE_USER_ACCOUNT_REST)
    public ResponseDto<UserAccountAuthResponse> authUserAccount(@RequestBody @Valid UserAccountAuthRequest userAccountCreateRequest) throws SchedulePilotException {
        return ResponseDto.success(this.manageUserService.authUserAccount(userAccountCreateRequest));
    }

    @ResponseBody
    @PostMapping(AccountUserConstants.FORGOT_PASSWORD_USER_ACCOUNT_REST)
    public ResponseDto<String> restorePasswordUserAccount(@RequestBody @Valid UserAccountForgotPasswordRequest request) throws SchedulePilotException {
        this.manageUserService.restorePasswordUserAccount(request);
        return ResponseDto.success("Password restored successfully.");
    }

    @ResponseBody
    @PostMapping(AccountUserConstants.CHANGE_PASSWORD_USER_ACCOUNT_REST)
    public ResponseDto<String> changePasswordUserAccount(@RequestBody @Valid UserAccountChangePasswordRequest request) throws SchedulePilotException {
        this.manageUserService.changePasswordUserAccount(request);
        return ResponseDto.success("Password changed successfully.");
    }
}
