#TestCase #Spring-boot-example #Testing 

**Setting up Testing environment:**
Add configuration to User Settings (JSON) in **Visual Studio Code** for _static import_

```json
"java.completion.favoriteStaticMembers": [
    "org.assertj.core.api.Assertions.*",
    "org.mockito.Mockito.*",
    "org.mockito.ArgumentMatchers.*",
    "org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*",
    "org.springframework.test.web.servlet.result.MockMvcResultMatchers.*",
    "org.springframework.test.web.servlet.result.MockMvcResultHandlers.*",
  ]
```
> Note: "org.mockito.ArgumentMatchers.*" is for using anyType() as input. e.g. anyLong(), anyInt(), anySet() etc.

#### `@SpringBootTest`  
***Class*** annotation scanning all `@Component` : `@Controller`, `@Service`, `@Repository`, `@Repository` and `@Configuration` for testing

> We can do this but compare to only `@WebMvcTest` or `@DataJpaTest` the speed is much slower.

#### `@Test`
***Method*** annotation for each test case.

---

### Unit Test

> Test all the Scenario
> only need to give the **service input** and test the **expected output**

*Configuration before Testing:*

- `@ExtendWith(SpringExtension.class)` 
  : ***Class*** annotation for finding `@Mock` or `@MockBean`


- `@Mock`
  : Mock the service return. Can set what to be returned later
  ```java
  @Mock
  Service service;
  ```
  <!-- - `@InjectMocks`
    : only  Inject Class
    Find `@Mock`
    same as `@BeforeEach` but not safe -->
- `@BeforeEach`
  Execute before each `@Test`.
  ControllerClass shoud include `@AllArgsConstructor` if you need 

  ```java
  ControllerInterface controllerInterface;

  @BeforeEach
  void setup() {controllerInterface = new ControllerClass(Service)}
  ```
  
*Demo Syntex:*

```java
void testSomething(String input, String output) {
  // Mockito
  // Given
  when(service.serviceMethod()).thenReturn(input); // domain
  // When
  String string = controllerInterface.controllerMethod();
  // Then
  assertThat(string).isEqualTo(output); // co-domain
}

@Test
void testCases() {
  testSomething("input", "output");
  // ... 100 testcases
}
```

---

### Integration Test

> Test whether the application works properly with the API(s)

*Configuration before Testing:*
- `@WebMvcTest`
  : ***Class*** annotation for scanning `@Controller`, `@Configuration` **ONLY**

- `@MockBean`
  : Create a new bean to spring context

  ```java
  @MockBean
  Service service;
  ```

- `@Autowired`
  : Due to `@WebMvcTest`, the **mockMvc** Bean has been loaded to spring context
  ```java
  @Autowired
  MockMvc mockMvc; // just like postman for testing
  ```

*Demo Syntex:*

```java
@Test
void testWebMvc() throws Exception {
    when(service.serviceMethod()).thenReturn("Hello World");
    mockMvc.perform(get("/api/v1/testing")) // MockMvcRequestBuilders.get()
           .andExpect(status().isOk()) // MockMvcResultMatchers.status()
           .andExpect(content().string("Hello Worldd")); // MockMvcResultMatchers.content()
}
```
> Note: Testing the content in integration test is redundant [^1]
---

### Data Jpa Test
> Test whether the repository works properly.

*Configuration before Testing:*
- `@DataJpaTest`
	: ***Class*** annotation for scanning `@Repository` **ONLY**

- `@Autowired`
	: 

---

### Related Concepts
- Junit 5
- Mockito
---
### References
[Test Auto-configuration Annotations](https://docs.spring.io/spring-boot/docs/current/reference/html/test-auto-configuration.html#appendix.test-auto-configuration)

[^1]: All scenarios should be tested in Unit test session.