package cn.cs.scu;

import cn.cs.scu.dao.DaoImplement;
import cn.cs.scu.dao.factory.DaoFactory;
import cn.cs.scu.domain.Blacklist;

import java.util.ArrayList;

/**
 * Created by Wanghan on 2017/3/16.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class testUpdateBlacklist {
    public static  void main (String[] args){
        Blacklist blacklist1 = new Blacklist();
        blacklist1.setUser_id("7");
        blacklist1.setUser_name("asd");
        Blacklist blacklist2 = new Blacklist();
        blacklist2.setUser_id("8");
        blacklist2.setUser_name("xdv");

        ArrayList<Blacklist> arrayList = new ArrayList<>();
        arrayList.add(blacklist1);
        arrayList.add(blacklist2);

        DaoImplement blacklistDaoImplement = DaoFactory.getBlacklistDao();

        blacklistDaoImplement.updateTable(arrayList.toArray(new Blacklist[0]));

    }
}
