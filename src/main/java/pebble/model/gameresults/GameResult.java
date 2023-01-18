package pebble.model.gameresults;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pebble.model.player.PlayerIdentity;

import javax.persistence.*;

@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Entity
@Generated
@Getter
@Setter
public class GameResult {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    String firstPlayerName;

    @NotBlank
    String secondPlayerName;

    @Enumerated(EnumType.STRING)
    @NotNull
    PlayerIdentity identityOfWinner;
}
