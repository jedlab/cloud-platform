package com.cloud.userservice.app;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import com.cloud.userservice.app.service.UserService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
//        WithSecurityContextTestExecutionListener.class,
        })
public class SpringACLIntegrationTest extends AbstractJUnit4SpringContextTests{
    
    private static Integer FIRST_MESSAGE_ID = 1;
    private static Integer SECOND_MESSAGE_ID = 2;
    private static Integer THIRD_MESSAGE_ID = 3;
    private static String EDITTED_CONTENT = "EDITED";
    
    @Configuration
    @ComponentScan(basePackageClasses = UserService.class)
    public static class SpringConfig {
    	
    }
    
//    @Autowired
    
    
    
//    @Autowired
//    NoticeMessageRepository repo;
//    
//    @Test
//    @WithMockUser(username="manager")
//    public void givenUserManager_whenFindAllMessage_thenReturnFirstMessage(){
//        List<NoticeMessage> details = repo.findAll();
//        assertNotNull(details);
//        assertEquals(1,details.size());
//        assertEquals(FIRST_MESSAGE_ID,details.get(0).getId());
//    }
    
    @Test
//    @WithMockUser(username="manager")
    public void givenUserManager_whenFind1stMessageByIdAndUpdateItsContent_thenOK(){
    	
    }
    
}