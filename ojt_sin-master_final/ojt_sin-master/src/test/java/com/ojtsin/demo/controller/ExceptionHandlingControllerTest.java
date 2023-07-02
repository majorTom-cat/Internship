package com.ojtsin.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

@WebMvcTest(ExceptionHandlingController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class ExceptionHandlingControllerTest {

    @Mock
	private MockHttpServletRequest request;
	
	@Mock
	private Model model;
	
	@InjectMocks
	private ExceptionHandlingController controller;
    
    @Test
    @DisplayName("404 에러 페이지 뷰 리턴 테스트")
    void testHandleError404() {
        //when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(HttpStatus.NOT_FOUND.value());
    	given(request.getAttribute("javax.servlet.error.status_code")).willReturn(HttpStatus.NOT_FOUND.value());
        String viewName = controller.handleError(request, model);

        assertEquals("/errors/404", viewName);
    }

    @Test
    @DisplayName("500 에러 페이지 뷰 리턴 테스트")
    void testHandleError500() {
        //when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
    	given(request.getAttribute("javax.servlet.error.status_code")).willReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String viewName = controller.handleError(request, model);

        assertEquals("/errors/500", viewName);
    }

    @Test
    @DisplayName("이외의 에러 페이지 뷰 리턴 테스트")
    void testHandleErrorOther() {
        //when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(400);
    	given(request.getAttribute("javax.servlet.error.status_code")).willReturn(400);
        String viewName = controller.handleError(request, model);

        assertEquals("/errors/error", viewName);
        //assertEquals("400", model.getAttribute("statusCode"));
    }
    

}
