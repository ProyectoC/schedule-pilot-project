package com.schedulepilot.core.security;

import com.schedulepilot.core.config.SwaggerConfig;
import com.schedulepilot.core.config.TokenConfig;
import com.schedulepilot.core.constants.AccountUserConstants;
import com.schedulepilot.core.constants.CollegeCareerConstants;
import com.schedulepilot.core.constants.CountryConstants;
import com.schedulepilot.core.constants.RolAccountConstants;
import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.security.token.service.ManageTokenService;
import com.schedulepilot.core.service.UserAccountService;
import com.schedulepilot.core.util.SecurityUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private ManageTokenService manageTokenService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        LOGGER.info("### AUTH: Url Request: {} ###", request.getRequestURI());
        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_OK, "success");
            return;
        }
        if (allowRequestWithOutToken(request)) {
            LOGGER.info("### AUTH: Allow request without Token ###");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            LOGGER.info("### AUTH: Allow request with UserCommon Token ###");
            String token = getJwtFromRequest(request);
            allowRequest:
            {
                if (StringUtils.isEmpty(token))
                    break allowRequest;
                String username = manageTokenService.validateUserToken(token, tokenConfig.getClients().getUserCommon().getKey());
                if (StringUtils.isEmpty(username))
                    break allowRequest;
                UserAccountDto userDto = userAccountService.getByUsername(username);
                if (userDto == null)
                    break allowRequest;
                UserDetails userDetails = userAccountService.loadUserById(userDto.getId());
                if (StringUtils.isEmpty(userDetails))
                    break allowRequest;
                if (userDto.getBlock()) {
                    LOGGER.error("### AUTH: User is not enabled, Username: {} ###", username);
                    break allowRequest;
                }
                if (isAdministrationRequest(request)
                        && userDto.getRolAccountEntity().getName().equals(SecurityUtil.ROL_ADMINISTRATION_DEFAULT)
                        && userDto.getAuthTokenEntity().getKey().equals(token)) {
                    LOGGER.info("### AUTH: Authentication Administration request with UserCommon Token Username: {} ###", username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    requestWrapper.addHeader(SecurityUtil.USER_NAME_KEY, username);
                    requestWrapper.addHeader(SecurityUtil.USER_NAME_ID_KEY, userDto.getId().toString());

                    // request.setAttribute(SecurityUtil.USER_NAME_KEY, username);
                    // request.setAttribute(SecurityUtil.USER_NAME_ID_KEY, userDto.getId());
                } else if (userDto.getAuthTokenEntity().getKey().equals(token)) {
                    LOGGER.info("### AUTH: Authentication request with UserCommon Token Username: {} ###", username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    requestWrapper.addHeader(SecurityUtil.USER_NAME_KEY, username);
                    requestWrapper.addHeader(SecurityUtil.USER_NAME_ID_KEY, userDto.getId().toString());

                    // request.setAttribute(SecurityUtil.USER_NAME_KEY, username);
                    // request.setAttribute(SecurityUtil.USER_NAME_ID_KEY, userDto.getId());
                }
            }
        }
        filterChain.doFilter(requestWrapper, response);
    }

    private boolean allowRequestWithOutToken(HttpServletRequest request) {
        return (request.getRequestURI().contains(SecurityUtil.SING_HOST_REST_DEFAULT)
                || request.getRequestURI().contains(AccountUserConstants.REST_PATH_DEFAULT_V1 + AccountUserConstants.CREATE_USER_ACCOUNT_REST)
                || request.getRequestURI().contains(AccountUserConstants.REST_PATH_DEFAULT_V1 + AccountUserConstants.ACTIVATE_USER_ACCOUNT_REST)
                || request.getRequestURI().contains(AccountUserConstants.REST_PATH_DEFAULT_V1 + AccountUserConstants.AUTH_AUTHORIZE_USER_ACCOUNT_REST)
                || request.getRequestURI().contains(AccountUserConstants.REST_PATH_DEFAULT_V1 + AccountUserConstants.FORGOT_PASSWORD_USER_ACCOUNT_REST)
                || request.getRequestURI().contains(RolAccountConstants.REST_PATH_DEFAULT_V1)
                || request.getRequestURI().contains(CollegeCareerConstants.REST_PATH_DEFAULT_V1)
                || request.getRequestURI().contains(CountryConstants.REST_PATH_DEFAULT_V1)
                || request.getRequestURI().contains(AccountUserConstants.REST_PATH_DEFAULT_V1 + "/logout")
                || request.getRequestURI().contains(SwaggerConfig.SWAGGER_UI)
                || request.getRequestURI().contains(SwaggerConfig.SPRING_ADMIN));
    }

    private boolean isAdministrationRequest(HttpServletRequest request) {
        return (request.getRequestURI().contains("/test/oath2")
                || request.getRequestURI().contains("/test/common/oauth2/authorize/user"));
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityUtil.AUTHORIZATION_TYPE);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityUtil.TOKEN_TYPE))
            return bearerToken.substring(7, bearerToken.length());
        return "";
    }

    /**
     * allow adding additional header entries to a request
     *
     * @author wf
     *
     */
    public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
        /**
         * construct a wrapper for this request
         *
         * @param request
         */
        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        private Map<String, String> headerMap = new HashMap<>();

        /**
         * add a header with given name and value
         *
         * @param name
         * @param value
         */
        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }

        /**
         * get the Header names
         */
        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headerMap.keySet()) {
                names.add(name);
            }
            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                values.add(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }

    }
}
