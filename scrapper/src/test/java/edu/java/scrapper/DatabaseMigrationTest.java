package edu.java.scrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseMigrationTest extends IntegrationTest {

    @SneakyThrows
    @Test
    public void migrationLinkShouldCorrectWork() {
        try (Connection connection = POSTGRES.createConnection("");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM link")) {
            ResultSet resultSet = statement.executeQuery();

            Assertions.assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("link_id");
            Assertions.assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("url");
            Assertions.assertThat(resultSet.getMetaData().getColumnName(3)).isEqualTo("description");
            Assertions.assertThat(resultSet.getMetaData().getColumnName(4)).isEqualTo("updated_at");
            Assertions.assertThat(resultSet.getMetaData().getColumnName(5)).isEqualTo("last_checked_at");
        }
    }

    @SneakyThrows
    @Test
    public void migrationChatShouldCorrectWork() {
        try (Connection connection = POSTGRES.createConnection("");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat");) {
            ResultSet resultSet = statement.executeQuery();
            Assertions.assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("chat_id");
        }
    }
}
