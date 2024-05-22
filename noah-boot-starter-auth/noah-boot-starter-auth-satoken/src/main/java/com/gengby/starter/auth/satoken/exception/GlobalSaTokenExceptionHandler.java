package com.gengby.starter.auth.satoken.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.gengby.starter.web.model.R;

/**
 * 全局 SaToken 异常处理器
 *
 * @author Noah
 * @since 1.2.0
 */
@RestControllerAdvice
public class GlobalSaTokenExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalSaTokenExceptionHandler.class);

    /**
     * 认证异常-登录认证
     */
    @ExceptionHandler(NotLoginException.class)
    public R<Void> handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，认证失败，无法访问系统资源。", request.getRequestURI(), e);
        String errorMsg = switch (e.getType()) {
            case NotLoginException.KICK_OUT -> "您已被踢下线。";
            case NotLoginException.BE_REPLACED_MESSAGE -> "您已被顶下线。";
            default -> "您的登录状态已过期，请重新登录。";
        };
        return R.fail(HttpStatus.UNAUTHORIZED.value(), errorMsg);
    }

    /**
     * 认证异常-权限认证
     */
    @ExceptionHandler(NotPermissionException.class)
    public R<Void> handleNotPermissionException(NotPermissionException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，权限码校验失败。", request.getRequestURI(), e);
        return R.fail(HttpStatus.FORBIDDEN.value(), "没有访问权限，请联系管理员授权");
    }

    /**
     * 认证异常-角色认证
     */
    @ExceptionHandler(NotRoleException.class)
    public R<Void> handleNotRoleException(NotRoleException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，角色权限校验失败。", request.getRequestURI(), e);
        return R.fail(HttpStatus.FORBIDDEN.value(), "没有访问权限，请联系管理员授权");
    }
}