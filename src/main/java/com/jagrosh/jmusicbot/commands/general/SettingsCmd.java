/*
 * Copyright 2017 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.settings.Settings;
import com.jagrosh.jmusicbot.utils.FormatUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class SettingsCmd extends Command 
{
    private final static String EMOJI = "\uD83C\uDFA7"; // 🎧
    
    public SettingsCmd(Bot bot)
    {
        this.name = "settings";
        this.help = "顯示機器人設定";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = true;
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        MessageBuilder builder = new MessageBuilder()
                .append(EMOJI + " **")
                .append(FormatUtil.filter(event.getSelfUser().getName()))
                .append("** settings:");
        TextChannel tchan = s.getTextChannel(event.getGuild());
        VoiceChannel vchan = s.getVoiceChannel(event.getGuild());
        Role role = s.getRole(event.getGuild());
        EmbedBuilder ebuilder = new EmbedBuilder()
                .setColor(event.getSelfMember().getColor())
                .setDescription("文字頻道: " + (tchan == null ? "任何" : "**#" + tchan.getName() + "**")
                        + "\n語音頻道: " + (vchan == null ? "任何" : "**" + vchan.getName() + "**")
                        + "\nDJ身分組: " + (role == null ? "無" : "**" + role.getName() + "**")
                        + "\n自定義前綴: " + (s.getPrefix() == null ? "無" : "`" + s.getPrefix() + "`")
                        + "\n重複撥放模式: **" + (s.getRepeatMode() ? "On" : "Off") + "**"
                        + "\n預設撥放列表: " + (s.getDefaultPlaylist() == null ? "無" : "**" + s.getDefaultPlaylist() + "**")
                        )
                .setFooter(event.getJDA().getGuilds().size() + " 伺服器 | "
                        + event.getJDA().getGuilds().stream().filter(g -> g.getSelfMember().getVoiceState().inVoiceChannel()).count()
                        + " 語音連接", null);
        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
    }
    
}
