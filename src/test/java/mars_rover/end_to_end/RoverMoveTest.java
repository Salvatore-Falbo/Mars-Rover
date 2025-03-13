package mars_rover.end_to_end;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import mars_rover.dtos.Move;
import mars_rover.dtos.ObstacleDetected;
import mars_rover.dtos.Position;
import mars_rover.enums.DirectionEnum;
import mars_rover.singletons.GridState;
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
class RoverMoveTest {
    @Autowired
    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private ObjectWriter ow;

    //rotate counterclockwise of 90° to get a fitting visual representation with the application logical one
    private final boolean[][] gridMock = {
        {true,true,true,true,true,true,false,true,true,true},
        {true,false,true,true,true,true,true,true,true,true},
        {true,true,true,true,false,true,true,true,true,true},
        {true,true,true,true,true,true,true,false,true,true},
        {false,true,true,true,true,true,true,true,true,true},
        {true,true,true,false,true,true,true,true,true,true},
        {true,true,true,true,true,true,true,true,true,false},
        {true,true,true,true,true,false,true,true,true,true},
        {true,true,true,true,true,true,true,true,false,true},
        {true,true,false,true,true,true,true,true,true,true}
    };

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
    public void move_rover_200() throws Exception {
        Position startingPosition = new Position(3,3, DirectionEnum.SOUTH);
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(startingPosition)))
                .andExpect(status().isOk());

        ReflectionTestUtils.setField(GridState.class, "grid", gridMock);
        Move move = new Move("fbfrfbfrbbfl");
        Position resultPosition = new Position(2,1,DirectionEnum.WEST);

        mockMvc.perform(post("/move")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(move)))
                .andExpect(status().isOk())
                .andExpect(content().json(ow.writeValueAsString(resultPosition)));
    }

    @Test
    public void move_rover_200_obstacle() throws Exception {
        Position startingPosition = new Position(3,3, DirectionEnum.SOUTH);
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(startingPosition)))
                .andExpect(status().isOk());

        ReflectionTestUtils.setField(GridState.class, "grid", gridMock);
        Move move = new Move("ffflfff");
        ObstacleDetected resultObstacle = new ObstacleDetected(
                new Position(3,0,DirectionEnum.EAST),
                4,
                0);

        mockMvc.perform(post("/move")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(move)))
                .andExpect(status().isOk())
                .andExpect(content().json(ow.writeValueAsString(resultObstacle)));
    }

    @Test
    public void move_rover_200_wrapping() throws Exception {
        Position startingPosition = new Position(8,9, DirectionEnum.NORTH);
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(startingPosition)))
                .andExpect(status().isOk());

        ReflectionTestUtils.setField(GridState.class, "grid", gridMock);
        Move move = new Move("flbbblf");
        Position resultPosition = new Position(1,9,DirectionEnum.SOUTH);

        mockMvc.perform(post("/move")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(move)))
                .andExpect(status().isOk())
                .andExpect(content().json(ow.writeValueAsString(resultPosition)));
    }

    @Test
    public void move_rover_400_not_init() throws Exception {
        Move move = new Move("fblr");
        Position resultPosition = new Position(2,2,DirectionEnum.WEST);

        mockMvc.perform(post("/move")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(move)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Rover is not initialized"));
    }

    @Test
    public void move_rover_400_empty() throws Exception {
        Position startingPosition = new Position(3,3, DirectionEnum.SOUTH);
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(startingPosition)))
                .andExpect(status().isOk());

        ReflectionTestUtils.setField(GridState.class, "grid", gridMock);
        Move move = new Move(null);
        Position resultPosition = new Position(2,2,DirectionEnum.WEST);

        mockMvc.perform(post("/move")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(move)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Sequence must be declared"));
    }

    @Test
    public void move_rover_400_missing() throws Exception {
        Position startingPosition = new Position(3,3, DirectionEnum.SOUTH);
        mockMvc.perform(post("/init")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(startingPosition)))
                .andExpect(status().isOk());

        ReflectionTestUtils.setField(GridState.class, "grid", gridMock);
        Move move = new Move(null);
        Position resultPosition = new Position(2,2,DirectionEnum.WEST);

        mockMvc.perform(post("/move")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request format"));
    }
}
