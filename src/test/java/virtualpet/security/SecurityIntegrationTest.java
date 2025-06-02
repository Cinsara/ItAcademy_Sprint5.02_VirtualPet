package virtualpet.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import virtualpet.model.User;
import virtualpet.model.UserRol;
import virtualpet.repositories.UserRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SecurityIntegrationTest {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityIntegrationTest(MockMvc mockMvc, UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeAll
    void setUp() {
        if (!userRepository.existsByEmail("testUser@example.com")) {
            User user = new User();
            user.setUsername("testUser");
            user.setEmail("testUser@example.com");
            user.setPassword(passwordEncoder.encode("testUser_password"));
            user.setRol(UserRol.USER);
            userRepository.save(user);
        }

        if (!userRepository.existsByEmail("testAdmin@example.com")) {
            User admin = new User();
            admin.setUsername("testAdmin");
            admin.setEmail("testAdmin@example.com");
            admin.setPassword(passwordEncoder.encode("testAdmin_password"));
            admin.setRol(UserRol.ADMIN);
            userRepository.save(admin);
        }
    }

    String obtainToken(String username, String password) throws Exception {
        String requestBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password);

        String responseContent = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(responseContent).get("token").asText();
    }

    @Test
    void userToken_canAccessUserRoute() throws Exception {
        String token = obtainToken("testUser", "testUser_password");

        System.out.println("Token obtain");

        mockMvc.perform(get("/api/user/data")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void userToken_cannotAccessAdminRoute() throws Exception {
        String token = obtainToken("testUser", "testUser_password");

        System.out.println("Token obtain");

        mockMvc.perform(get("/api/admin/data")
                        .with(user("testUser").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void adminToken_canAccessAdminRoute() throws Exception {
        String token = obtainToken("testAdmin", "testAdmin_password");

        mockMvc.perform(get("/api/admin/data")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void requestWithoutToken_isUnauthorized() throws Exception {
        mockMvc.perform(get("/api/user/data"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminLoginTest() throws Exception {
        String requestBody = "{\"username\":\"testAdmin\",\"password\":\"testAdmin_password\"}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void userLoginTest() throws Exception {
        String requestBody = "{\"username\":\"testUser\",\"password\":\"testUser_password\"}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

}
