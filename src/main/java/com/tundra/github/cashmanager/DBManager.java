package com.tundra.github.cashmanager;

import com.tundra.finelib.database.sqlite.SQLite;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
    private static final SQLite cashDB = new SQLite();

    protected static void init(){
        try {
            if (!cashDB.hasTable("cash")) {
                cashDB.executeUpdate("CREATE TABLE cash(uuid TEXT UNIQUE NOT NULL, cash INTEGER DEFAULT 0 NOT NULL)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean existPlayer(Player player){
        try {
            PreparedStatement ps = cashDB.prepareStatement("SELECT COUNT(uuid) FROM cash WHERE uuid = ?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if (rs.getInt(1) == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addPlayer(Player player){
        try {
            if (!existPlayer(player)) {
                PreparedStatement ps = cashDB.prepareStatement("INSERT INTO cash(uuid) VALUES(?)");
                ps.setString(1, player.getUniqueId().toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removePlayer(Player player){
        try {
            if (existPlayer(player)) {
                PreparedStatement ps = cashDB.prepareStatement("DELETE FROM cash WHERE uuid = ?");
                ps.setString(1, player.getUniqueId().toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCash(Player player){
        if (existPlayer(player)){
            try {
                PreparedStatement ps = cashDB.prepareStatement("SELECT * FROM cash WHERE uuid = ?");
                ps.setString(1, player.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                return rs.getInt("cash");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException(player + " doesn't have cash!");
    }

    public static void setCash(Player player, int cashValue){
        if (existPlayer(player)){
            try {
                PreparedStatement ps = cashDB.prepareStatement("UPDATE cash SET cash = ? WHERE uuid = ?");
                ps.setInt(1, cashValue);
                ps.setString(2, player.getUniqueId().toString());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean hasCash(Player player, int cashValue){
        if (!existPlayer(player)) throw new IllegalArgumentException(player + " doesn't have cash!");
        return getCash(player) >= cashValue;
    }

    protected static SQLite getCashDB() {
        return cashDB;
    }
}
