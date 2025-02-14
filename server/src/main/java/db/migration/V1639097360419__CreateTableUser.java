package db.migration;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.DSL.currentTimestamp;
import static org.jooq.impl.DSL.using;
import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.BOOLEAN;
import static org.jooq.impl.SQLDataType.TIMESTAMP;
import static org.jooq.impl.SQLDataType.VARCHAR;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
 * @see "https://www.jooq.org/doc/3.1/manual/sql-building/ddl-statements"
 */
public class V1639097360419__CreateTableUser extends BaseJavaMigration {
  public void migrate(Context context) throws Exception {
    var dsl = using(context.getConnection());
    dsl.transaction(configuration -> using(configuration)
      .createTableIfNotExists("user")
        .column("id", BIGINT.identity(true))
        .column("name", VARCHAR(100).nullable(false))
        .column("email", VARCHAR(100).nullable(true))
        .column("deleted_email", VARCHAR(100).nullable(true))
        .column("email_confirmed", BOOLEAN.defaultValue(false))
        .column("password", VARCHAR(100).nullable(false))        
        .column("active", BOOLEAN.defaultValue(true))
        .column("created_at", TIMESTAMP.defaultValue(currentTimestamp()))
        .column("updated_at", TIMESTAMP.nullable(true))
        .column("deleted_at", TIMESTAMP.nullable(true))
        .column("created_by", BIGINT.nullable(true))
        .column("updated_by", BIGINT.nullable(true))
        .column("deleted_by", BIGINT.nullable(true))
      .constraints(
        constraint("user_pk").primaryKey("id"),
        constraint("user_unique_email").unique("email"),
        constraint("user_created_by_fk").foreignKey("created_by").references("user", "id"),
        constraint("user_updated_by_fk").foreignKey("updated_by").references("user", "id"),
        constraint("user_deleted_by_fk").foreignKey("deleted_by").references("user", "id"))
      .execute());
  }
}
