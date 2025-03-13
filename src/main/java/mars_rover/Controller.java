package mars_rover;

import jakarta.validation.Valid;
import mars_rover.commands.CommandFactory;
import mars_rover.enums.CommandEnum;
import mars_rover.exceptions.AlreadyInitializedException;
import mars_rover.dtos.Move;
import mars_rover.dtos.Position;
import mars_rover.exceptions.InvalidSequenceException;
import mars_rover.exceptions.NotInitializedException;
import mars_rover.exceptions.ObstacleDetectedException;
import mars_rover.singletons.GridState;
import mars_rover.singletons.RoverState;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @PostMapping(value = "init")
    public ResponseEntity<Position> init(@Valid @RequestBody Position startPosition) throws AlreadyInitializedException {
        System.out.println(startPosition.getX());

        //initialize all states singleton
        RoverState.initialize(startPosition);
        GridState.initialize(startPosition.getX(), startPosition.getY());

        //return position set
        return ResponseEntity.ok(startPosition);
    }

    @PostMapping(value = "move")
    public ResponseEntity<Position> move(@RequestBody Move move) throws Exception {
        if (RoverState.getPosition() == null) {
            throw new NotInitializedException();
        }

        //check if move is not empty
        if (move.sequence == null || move.sequence.isEmpty()) {
            throw new InvalidSequenceException();
        }

        //check if all moves are valid
        CommandEnum[] commands = new CommandEnum[move.sequence.length()];
        for (int i = 0; i < move.sequence.length(); i++) {
            commands[i] = CommandEnum.valueOf(move.sequence.charAt(i));
        }

        //execute all commands
        for (CommandEnum command : commands) {
            CommandFactory.getCommand(command).execute();
        }

        return ResponseEntity.ok(RoverState.getPosition());
    }
}
