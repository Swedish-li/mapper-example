package com.lrs.example.ibatis.plugin;

import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

public class PageQuerySqlSource implements SqlSource {

	private String sql;
	private List<ParameterMapping> parameterMappings;
	private Configuration configuration;
	private SqlSource original;

	private RowBounds rowBounds;

	@SuppressWarnings("unchecked")
	public PageQuerySqlSource(SqlSource sqlSource, RowBounds rowBounds) {
		this.original = sqlSource;
		this.rowBounds = rowBounds;
		MetaObject metaObject = SystemMetaObject.forObject(sqlSource);
		this.sql = (String) metaObject.getValue("sql");
		this.parameterMappings = (List<ParameterMapping>) metaObject.getValue("parameterMappings");
		this.configuration = (Configuration) metaObject.getValue("configuration");

	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {

		return new BoundSql(configuration, getPageSql(sql, rowBounds), parameterMappings, parameterObject);

	}

	/**
	 * 获取分页Sql
	 * 
	 * @see com.github.pagehelper.dialect.helper.OracleDialect
	 * @param querySql
	 * @param rowBounds
	 * @return
	 */
	private String getPageSql(String querySql, RowBounds rowBounds) {
		StringBuilder sqlBuilder = new StringBuilder(querySql.length() + 120);

		int start = rowBounds.getOffset();
		int end = start + rowBounds.getLimit();

		if (start > 0) {
			sqlBuilder.append("SELECT * FROM ( ");
		}
		if (end > 0) {
			sqlBuilder.append(" SELECT TMP_PAGE.*, ROWNUM ROW_ID FROM ( ");
		}
		sqlBuilder.append(querySql);
		if (end > 0) {
			sqlBuilder.append(" ) TMP_PAGE WHERE ROWNUM <= ");
			sqlBuilder.append(end);
		}
		if (start > 0) {
			sqlBuilder.append(" ) WHERE ROW_ID > ");
			sqlBuilder.append(start);
		}
		return sqlBuilder.toString();
	}

}
