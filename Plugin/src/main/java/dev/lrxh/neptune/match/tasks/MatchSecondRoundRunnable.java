package dev.lrxh.neptune.match.tasks;

import dev.lrxh.neptune.Neptune;
import dev.lrxh.neptune.configs.impl.MessagesLocale;
import dev.lrxh.neptune.match.Match;
import dev.lrxh.neptune.match.MatchService;
import dev.lrxh.neptune.match.impl.MatchState;
import dev.lrxh.neptune.match.impl.participant.Participant;
import dev.lrxh.neptune.providers.clickable.Replacement;
import dev.lrxh.neptune.providers.tasks.NeptuneRunnable;
import org.bukkit.GameMode;
import org.bukkit.Sound;

public class MatchSecondRoundRunnable extends NeptuneRunnable {
    private final Neptune plugin;

    private final Match match;
    private final Participant participant;
    private int respawnTimer = 3;

    public MatchSecondRoundRunnable(Match match, Participant participant, Neptune plugin) {
        this.match = match;
        this.participant = participant;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!MatchService.get().matches.contains(match)) {
            stop(plugin);

            return;
        }

        if (match.isEnded()) {
            stop(plugin);
            return;
        }

        if (participant.getPlayer() == null) return;
        if (respawnTimer == 0) {
            match.sendMessage(MessagesLocale.ROUND_STARTED);
            match.showParticipant(participant);
            match.setupParticipants();
            match.teleportToPositions();
            match.startMatch();
            stop(plugin);
            return;
        }

        if (match.getState().equals(MatchState.STARTING)) {
            match.playSound(Sound.UI_BUTTON_CLICK);

            match.sendTitle(MessagesLocale.MATCH_STARTING_TITLE_HEADER.getString().replace("<countdown-time>", String.valueOf(respawnTimer)),
                    MessagesLocale.MATCH_STARTING_TITLE_FOOTER.getString().replace("<countdown-time>", String.valueOf(respawnTimer)),
                    100);
            match.sendMessage(MessagesLocale.ROUND_STARTING, new Replacement("<timer>", String.valueOf(respawnTimer)));
        }

        if (respawnTimer == 3) {
            match.resetArena();
            match.hideParticipant(participant);
            match.setupParticipants();
            participant.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
        respawnTimer--;
    }
}
