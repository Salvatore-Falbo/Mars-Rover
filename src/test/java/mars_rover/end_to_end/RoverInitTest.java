package mars_rover.end_to_end;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import mars_rover.dtos.Position;
import mars_rover.enums.DirectionEnum;
import mars_rover.singletons.RoverState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoverInitTest {
    @Autowired
    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private ObjectWriter ow;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        this.ow = mapper.writer().withDefaultPrettyPrinter();
    }

    @AfterEach
    public void clearState() {
        ReflectionTestUtils.setField(RoverState.class, "position", null);
    }

    @Test
    public void init_rover_200() throws Exception {
        Position okPosition = new Position(3,3, DirectionEnum.WEST);

        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(okPosition)))
                .andExpect(status().isOk())
                .andExpect(content().json(ow.writeValueAsString(okPosition)));
    }

    @Test
    public void init_rover_400_invalid_direction() throws Exception {
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{\"x\": 3, \"y\": 3, \"direction\": \"A\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request format"));
    }

    @Test
    public void init_rover_400_missing_param() throws Exception {
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{\"y\": 3, \"direction\": \"W\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request format"));
    }

    @Test
    public void init_rover_403_multi_init() throws Exception {
        Position position = new Position(3,3, DirectionEnum.WEST);
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(position)))
                .andExpect(status().isOk());

        System.out.println(ow.writeValueAsString(position));

        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(position)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Cannot initialize multiple times"));
    }
}
