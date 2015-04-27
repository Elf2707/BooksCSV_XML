package ua.lorien.spring.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ConnectCloseJDBCService {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public NamedParameterJdbcTemplate getJdbcTamplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTamplate) {
		this.jdbcTemplate = jdbcTamplate;
	}

	public void clearAllTablesInDB() {
		
		final String delQueryPart1 = "DELETE FROM ";
		final String delQueryPart2 = " WHERE 1;";
		
		final String resetTableIdIncPart1 = "ALTER TABLE ";
		final String resetTableIdIncPart2 = " AUTO_INCREMENT = 1;";
		final String getTablesQuery = "SHOW TABLES;";

		// Get tables name to truncate
		ResultSetExtractor<List<String>> resultSet = new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<String> tables = new ArrayList<>();
				while (rs.next()) {
					tables.add(rs.getString(1));
				}
				return tables;
			}
		};

		// Clearing tables
		for (String tableName : jdbcTemplate.query(getTablesQuery, resultSet)){
			jdbcTemplate.update(delQueryPart1 + tableName + delQueryPart2, new HashMap<String,Object>() );
			jdbcTemplate.update(resetTableIdIncPart1 + tableName + resetTableIdIncPart2, new HashMap<String,Object>());
		}
	}
}
