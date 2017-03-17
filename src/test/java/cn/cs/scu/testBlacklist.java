package cn.cs.scu;

import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.dao.factory.DaoFactory;
import cn.cs.scu.dao.implement.BlacklistDaoImplement;
import cn.cs.scu.domain.Blacklist;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Wanghan on 2017/3/16.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class testBlacklist {
    public static void main(String[] args) {
        DaoImplement blacklistDaoImplement = DaoFactory.getBlacklistDao();
        JSONObject json = new JSONObject("{\"user_name\":\"a\"}");

        Blacklist[] blacklists= (Blacklist[]) blacklistDaoImplement.getTable(json);
        for(Blacklist blacklist:blacklists){
            System.out.println(blacklist.getUser_id());
        }
    }
}
