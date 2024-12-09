package com.koreait.surl_project_11.global.security;

import com.koreait.surl_project_11.domain.auth.auth.service.AuthTokenService;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.app.AppConfig;
import com.koreait.surl_project_11.global.rq.Rq;
import com.koreait.surl_project_11.standard.util.Ut;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final AuthTokenService authTokenService;
    private final Rq rq;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) {
        String accessToken = null;
        String refreshToken = null;

        boolean cookieBased = true;

        String authorization = req.getHeader("Authorization");
        if (authorization != null) {
            String[] authorizationBits = authorization.substring("Bearer ".length()).split(" ", 2);

            if (authorizationBits.length == 2) {
                refreshToken = authorizationBits[0];
                accessToken = authorizationBits[1];
                cookieBased = false;
            }
        }

        if (Ut.str.isBlank(accessToken) || Ut.str.isBlank(refreshToken)) {
            accessToken = rq.getCookieValue("accessToken", null);
            refreshToken = rq.getCookieValue("refreshToken", null);
            cookieBased = true;
        }

        if (Ut.str.isBlank(accessToken) || Ut.str.isBlank(refreshToken)) {
            filterChain.doFilter(req, resp);
            return;
        }

        if (!authTokenService.validateToken(accessToken)) {
            Member member = memberService.findByRefreshToken(refreshToken).orElse(null);

            if (member == null) {
                filterChain.doFilter(req, resp);
                return;
            }

            String newAccessToken = authTokenService.genToken(member, AppConfig.getAccessTokenExpirationSec());

            if (cookieBased)
                rq.setCookie("accessToken", newAccessToken);
            else
                resp.setHeader("Authorization", "Bearer " + refreshToken + " " + newAccessToken);

            accessToken = newAccessToken;
        }

        Map<String, Object> accessTokenData = authTokenService.getDataFrom(accessToken);

        long id = (int) accessTokenData.get("id");

        User user = new User(id + "", "", List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, resp);
    }
}
