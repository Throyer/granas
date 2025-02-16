package db.migration;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.DSL.using;
import static org.jooq.impl.SQLDataType.BIGINT;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
* @see https://www.jooq.org/doc/3.1/manual/sql-building/ddl-statements/
*/
public class V1739686780668__CreateTableTransactionTag extends BaseJavaMigration {
  @Override
  public void migrate(Context context) throws Exception {
    var dsl = using(context.getConnection());
    dsl.transaction(configuration -> using(configuration)
      .createTableIfNotExists("transaction_tag")
        .column("transaction_id", BIGINT.nullable(true))
        .column("tag_id", BIGINT.nullable(true))
      .constraints(
        constraint("transaction_tag_fk").foreignKey("transaction_id").references("transaction", "id"),
        constraint("tag_transaction_fk").foreignKey("tag_id").references("tag", "id"))
      .execute());
  }
}