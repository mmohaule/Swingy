package com.mmohaule.swingy.Database;

import java.sql.*;
import java.util.ArrayList;

import com.mmohaule.swingy.Model.GameCharacter;
import com.mmohaule.swingy.Model.artifact.*;

public class DataBase {
	private static final String DATA_BASE_URL = "jdbc:sqlite:heroes.db";
	private static Connection connection;

	public static void connect() {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(DATA_BASE_URL);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		connection = conn;
		initialise();
	}

	public static void close() {
		try {
			if (connection != null)
				connection.close();
			connection = null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static Connection getConnection() {
		if (connection == null)
			connect();
		return connection;
	}

	private static void initialise() {

		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='heroes'");
			if (!result.next()) {
				// System.out.println("Creating Table....");
				stmt.execute("CREATE TABLE heroes (id integer PRIMARY KEY AUTOINCREMENT, "
						+ "name text, "
						+ "class text, "
						+ "level integer, "
						+ "xp integer, "
						+ "attack integer, "
						+ "defense integer, "
						+ "hp integer, " 
						+ "weapon_name text, "
						+ "weapon_value integer, "
						+ "helm_name text, "
						+ "helm_value integer, " 
						+ "armor_name text, "
						+ "armor_value integer)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static int insert(String name, String className, int level, int xp, int attack, int defense, int hp) {
		String sqlQuery = "INSERT INTO heroes(name, class, level, xp, attack, defense, hp) VALUES(?, ?, ?, ?, ?, ?, ?)";
		int id = 0;
		try (PreparedStatement pstmt = getConnection().prepareStatement(sqlQuery)) {
			pstmt.setString(1, name);
			pstmt.setString(2, className);
			pstmt.setInt(3, level);
			pstmt.setInt(4, xp);
			pstmt.setInt(5, attack);
			pstmt.setInt(6, defense);
			pstmt.setInt(7, hp);
			pstmt.executeUpdate();

			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name=\"heroes\"");
			if (rs.next())
				id = rs.getInt("seq");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return id;
	}

	public static ArrayList<String> selectAll() {
		String sqlQuery = "SELECT * FROM heroes";
		ArrayList<String> arrayList = new ArrayList<>();

		try (Statement stmt = getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery)) {
			for (int i = 1; rs.next(); i++) {
				arrayList.add(String.format("%d. %s (%s)", i, rs.getString("name"), rs.getString("class")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return arrayList;
	}

	public static GameCharacter selectHeroById(int id) {
		String sqlQuery = "SELECT * FROM heroes WHERE id = ?";
		GameCharacter hero = null;

		try (PreparedStatement pstmt = getConnection().prepareStatement(sqlQuery)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				GameCharacter builder = new GameCharacter();
				builder.setId(rs.getInt("id"));
				builder.setName(rs.getString("name"));
				builder.setHeroType(rs.getString("class"));
				builder.setLevel(rs.getInt("level"));
				builder.setExperience(rs.getInt("xp"));
				builder.setAttack(rs.getInt("attack"));
				builder.setDefense(rs.getInt("defense"));
				builder.setHitPoints(rs.getInt("hp"));

				if (rs.getString("weapon_name") != null)
					builder.setWeapon(new Weapon(rs.getString("weapon_name"), rs.getInt("weapon_value")));
				if (rs.getString("helm_name") != null)
					builder.setHelm(new Helm(rs.getString("helm_name"), rs.getInt("helm_value")));
				if (rs.getString("armor_name") != null)
					builder.setArmor(new Armor(rs.getString("armor_name"), rs.getInt("armor_value")));

				hero = builder;
			}
		} catch (SQLException e) {
			System.out.println("No such hero exits on that id!");
		}
		return hero;
	}

	public static void updateHero(GameCharacter hero) {
		String sqlQuery = "UPDATE heroes SET level = ?, xp = ?, attack = ?, defense = ?, hp = ? , "
				+ "weapon_name = ?, weapon_value = ?, helm_name = ?, helm_value = ?, armor_name = ?, armor_value = ? "
				+ "WHERE id = ?";

		try (PreparedStatement pstmt = getConnection().prepareStatement(sqlQuery)) {
			pstmt.setInt(1, hero.getLevel());
			pstmt.setInt(2, (int) hero.getExperience());
			pstmt.setInt(3, (int) hero.getAttack());
			pstmt.setInt(4, (int) hero.getDefense());
			pstmt.setInt(5, (int) hero.getHitPoints());

			if (hero.getWeapon() != null) {
				pstmt.setString(6, hero.getWeapon().getName());
				pstmt.setInt(7, hero.getWeapon().getPoints());
			} else {
				pstmt.setNull(6, Types.VARCHAR);
				pstmt.setNull(7, Types.INTEGER);
			}
			if (hero.getHelm() != null) {
				pstmt.setString(8, hero.getHelm().getName());
				pstmt.setInt(9, hero.getHelm().getPoints());
			} else {
				pstmt.setNull(8, Types.VARCHAR);
				pstmt.setNull(9, Types.INTEGER);
			}
			if (hero.getArmor() != null) {
				pstmt.setString(10, hero.getArmor().getName());
				pstmt.setInt(11, hero.getArmor().getPoints());
			} else {
				pstmt.setNull(10, Types.VARCHAR);
				pstmt.setNull(11, Types.INTEGER);
			}

			pstmt.setInt(12, hero.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}