package db.migration;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
* @see https://www.jooq.org/doc/3.1/manual/sql-building/ddl-statements/
*/
public class V1739685916738__CreateTableTag extends BaseJavaMigration {
  @Override
  public void migrate(Context context) throws Exception {
    var create = using(context.getConnection());
    create.transaction(configuration -> using(configuration)
      .createTableIfNotExists("tag")
        .column("id", BIGINT.identity(true))
        .column("name", VARCHAR(255).nullable(false))
        .column("created_at", TIMESTAMP.defaultValue(currentTimestamp()))
        .column("updated_at", TIMESTAMP.nullable(true))
        .column("deleted_at", TIMESTAMP.nullable(true))
        .column("created_by", BIGINT.nullable(true))
        .column("updated_by", BIGINT.nullable(true))
        .column("deleted_by", BIGINT.nullable(true))
      .constraints(
        constraint("tag_pk").primaryKey("id"),
        constraint("tag_created_by_fk").foreignKey("created_by").references("user", "id"),
        constraint("tag_updated_by_fk").foreignKey("updated_by").references("user", "id"),
        constraint("tag_deleted_by_fk").foreignKey("deleted_by").references("user", "id"))
      .execute());
  }
}