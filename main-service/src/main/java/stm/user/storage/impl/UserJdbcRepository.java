package stm.user.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import stm.user.model.Role;
import stm.user.model.User;
import stm.user.storage.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        final String sqlQuery = "INSERT INTO users (login, password, full_name) VALUES ( ?, ?, ?)";
        final String sqlQueryRole = "INSERT INTO users_roles (user_id, role) VALUES (?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            return stmt;
        }, generatedId);
        user.setId(Objects.requireNonNull(generatedId.getKey()).longValue());
        List<Role> list = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate(sqlQueryRole, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, user.getId());
                ps.setString(2, list.get(i).toString());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        return user;
    }

    @Override
    public User getById(Long userId) {
        final String sqlQuery = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeUser, userId);
    }

    @Override
    public User getByLogin(String login) {
        final String sqlQuery = "SELECT * FROM users WHERE login = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeUser, login);
    }

    private User makeUser(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String login = rs.getString("login");
        String password = rs.getString("password");
        String fullName = rs.getString("full_name");
        Set<Role> roles = setRole(id);
        return new User(id, login, password, fullName, roles);
    }

    private Set<Role> setRole(Long id) {
        final String sqlQuery = "select role from users_roles where user_id = ?";
        return new HashSet<>(jdbcTemplate.query(sqlQuery,
                (ResultSet rs, int rowNum) -> Role.valueOf(rs.getString("role")),
                id));
    }
}
