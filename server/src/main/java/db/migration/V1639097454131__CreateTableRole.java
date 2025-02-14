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
public class V1639097454131__CreateTableRole extends BaseJavaMigration {
  public void migrate(Context context) throws Exception {
    var dsl = using(context.getConnection());
    dsl.transaction(configuration -> using(configuration)
      .createTableIfNotExists("role")
        .column("id", BIGINT.identity(true))
        .column("name", VARCHAR(100).nullable(false))
        .column("deleted_name", VARCHAR(100).nullable(true))
        .column("short_name", VARCHAR(100).nullable(true))
        .column("deleted_short_name", VARCHAR(100).nullable(true))
        .column("description", VARCHAR(100).nullable(true))
        .column("active", BOOLEAN.defaultValue(true))
        .column("created_at", TIMESTAMP.defaultValue(currentTimestamp()))
        .column("updated_at", TIMESTAMP.nullable(true))
        .column("deleted_at", TIMESTAMP.nullable(true))
        .column("created_by", BIGINT.nullable(true))
        .column("updated_by", BIGINT.nullable(true))
        .column("deleted_by", BIGINT.nullable(true))
      .constraints(
        constraint("role_pk").primaryKey("id"),
        constraint("role_unique_name").unique("name"),
        constraint("role_unique_short_name").unique("short_name"),
        constraint("role_created_by_fk").foreignKey("created_by").references("user", "id"),
        constraint("role_updated_by_fk").foreignKey("updated_by").references("user", "id"),
        constraint("role_deleted_by_fk").foreignKey("deleted_by").references("user", "id"))
      .execute());
  }
}
