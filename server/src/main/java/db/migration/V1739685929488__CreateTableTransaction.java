package db.migration;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.DSL.currentTimestamp;
import static org.jooq.impl.DSL.using;
import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.DECIMAL;
import static org.jooq.impl.SQLDataType.TIMESTAMP;
import static org.jooq.impl.SQLDataType.VARCHAR;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
* @see https://www.jooq.org/doc/3.1/manual/sql-building/ddl-statements/
*/
public class V1739685929488__CreateTableTransaction extends BaseJavaMigration {
  @Override
  public void migrate(Context context) throws Exception {
    var dsl = using(context.getConnection());
    dsl.transaction(configuration -> using(configuration)
      .createTableIfNotExists("transaction")
        .column("id", BIGINT.identity(true))
        .column("description", VARCHAR(225).nullable(false))
        .column("amount", DECIMAL(10, 2).nullable(false))
        .column("at", TIMESTAMP.nullable(true))
        .column("type", VARCHAR(225).nullable(false))
        .column("created_at", TIMESTAMP.defaultValue(currentTimestamp()))
        .column("updated_at", TIMESTAMP.nullable(true))
        .column("deleted_at", TIMESTAMP.nullable(true))
        .column("created_by", BIGINT.nullable(true))
        .column("updated_by", BIGINT.nullable(true))
        .column("deleted_by", BIGINT.nullable(true))
      .constraints(
        constraint("transaction_pk").primaryKey("id"),
        constraint("transaction_created_by_fk").foreignKey("created_by").references("user", "id"),
        constraint("transaction_updated_by_fk").foreignKey("updated_by").references("user", "id"),
        constraint("transaction_deleted_by_fk").foreignKey("deleted_by").references("user", "id"))
      .execute());
  }
}