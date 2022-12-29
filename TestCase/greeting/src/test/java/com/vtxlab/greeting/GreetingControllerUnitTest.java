package com.vtxlab.greeting;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.vtxlab.greeting.controller.GreetingOperation;
import com.vtxlab.greeting.controller.impl.GreetingController;
import com.vtxlab.greeting.service.GreetingService;

// Junit 5
@ExtendWith(SpringExtension.class) // @Mock, InjectMocks,
class GreetingControllerUnitTest {

  @Mock // No implementation injected in this reference
  // Mock the dependency of greetingOperation
  GreetingService greetingService;

  // @InjectMocks
  private GreetingOperation greetingOperation;


  @BeforeEach
  void setup() {
    greetingOperation = new GreetingController(greetingService);
  }

  
  void testHelloworld(String input, String output, String notEqualOutput) {
    // Mockito
    // Given
    Mockito.when(greetingService.greeting()).thenReturn(input); // domain
    // When
    String string = greetingOperation.greeting();
    // Then
    if (output != null) {
      Assertions.assertThat(string).isEqualTo(output); // co-domain
    }
    if (notEqualOutput != null) {
      Assertions.assertThat(string).isNotEqualTo(notEqualOutput); // co-domain
    }
  }

  @Test
  void testCases() {
    // Test Equal and Not Equal
    testHelloworld("hello world", "hello worldd","abc");
    testHelloworld("on9", "on9d", "on9a");
    testHelloworld("jkdnvvjjnv", "jkdnvvjjnvd", "dasdwqd");
    // ... 100 test cases
  }


}
