package com.tundra.github.cashmanager;

import com.tundra.finelib.FineLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class CashManager extends JavaPlugin {

    @Override
    public void onEnable() {
        FineLib.setPlugin(this);
        DBManager.getCashDB().connectSQLite("cashDB.sqlite");
        DBManager.init();
        new DetectLogin(this);
    }
    @Override
    public void onDisable() {
        DBManager.getCashDB().disconnectSQLite();
    }
}
