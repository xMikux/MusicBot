/*
 * Copyright 2016 John Grosh <john.a.grosh@gmail.com>.
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
package com.jagrosh.jmusicbot.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.OwnerCommand;
import com.jagrosh.jmusicbot.settings.Settings;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class AutoplaylistCmd extends OwnerCommand
{
    private final Bot bot;
    
    public AutoplaylistCmd(Bot bot)
    {
        this.bot = bot;
        this.guildOnly = true;
        this.name = "autoplaylist";
        this.arguments = "<name|NONE>";
        this.help = "設定伺服器預設播放列表";
        this.aliases = bot.getConfig().getAliases(this.name);
    }

    @Override
    public void execute(CommandEvent event) 
    {
        if(event.getArgs().isEmpty())
        {
            event.reply(event.getClient().getError()+" 請包括播放列表名稱或NONE");
            return;
        }
        if(event.getArgs().equalsIgnoreCase("none"))
        {
            Settings settings = event.getClient().getSettingsFor(event.getGuild());
            settings.setDefaultPlaylist(null);
            event.reply(event.getClient().getSuccess()+" 清除了預設播放列表 **"+event.getGuild().getName()+"**");
            return;
        }
        String pname = event.getArgs().replaceAll("\\s+", "_");
        if(bot.getPlaylistLoader().getPlaylist(pname)==null)
        {
            event.reply(event.getClient().getError()+" 無法找到 `"+pname+".txt`!");
        }
        else
        {
            Settings settings = event.getClient().getSettingsFor(event.getGuild());
            settings.setDefaultPlaylist(pname);
            event.reply(event.getClient().getSuccess()+" 預設的播放列表於 **"+event.getGuild().getName()+"** 現在是 `"+pname+"`");
        }
    }
}
