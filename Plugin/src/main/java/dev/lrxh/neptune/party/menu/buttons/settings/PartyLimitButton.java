package dev.lrxh.neptune.party.menu.buttons.settings;

import dev.lrxh.neptune.configs.impl.MenusLocale;
import dev.lrxh.neptune.party.Party;
import dev.lrxh.neptune.providers.clickable.Replacement;
import dev.lrxh.neptune.providers.menu.Button;
import dev.lrxh.neptune.utils.ItemBuilder;
import dev.lrxh.neptune.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public class PartyLimitButton extends Button {
    private final Party party;

    public PartyLimitButton(int slot, Party party) {
        super(slot);
        this.party = party;
    }

    @Override
    public void onClick(ClickType type, Player player) {
        if (type.equals(ClickType.LEFT)) {
            party.setMaxUsers(party.getMaxUsers() + 1);
        } else if (type.equals(ClickType.RIGHT)) {
            party.setMaxUsers(Math.max(party.getUsers().size(), party.getMaxUsers() - 1));
        }
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(MenusLocale.PARTY_SETTINGS_MAX_SIZE_MATERIAL.getString())
                .name(MenusLocale.PARTY_SETTINGS_MAX_SIZE_TITLE.getString())
                .lore(ItemUtils.getLore(MenusLocale.PARTY_SETTINGS_MAX_SIZE_LORE.getStringList(),
                        new Replacement("<size>", String.valueOf(party.getMaxUsers()))), player)

                .build();
    }
}
