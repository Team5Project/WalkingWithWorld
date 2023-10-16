package com.team5.WalkingWithWorld.global.filter;

import com.team5.WalkingWithWorld.global.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    //TODO 로그인이 필요 없는 URL 추가

    private static final String[] whiteList = {"/favicon.ico","/login","/signup","/signupForm","/walking-path","/walking-path/*","/images/*","/css/*",};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            log.info("인증 필터 시작 : {}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 로직 실행 : {}", requestURI);
                HttpSession session = httpServletRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LONGIN_USERS) == null) {
                    log.info("미인증 사용자 요청 : {}", requestURI);

                    //로그인으로 보내기
                    httpServletResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 필터 종료 : {}", requestURI);

        }
    }

    /**
     * whiteList는 체크 안하는 목록
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
