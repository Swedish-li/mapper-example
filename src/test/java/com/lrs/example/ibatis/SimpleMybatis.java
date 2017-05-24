package com.lrs.example.ibatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.ibatis.type.BigDecimalTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lrs.example.ibatis.plugin.PageQueryInterceptor;
import com.lrs.example.model.Employee;

public class SimpleMybatis {
	private static Logger log = LoggerFactory.getLogger(SimpleMybatis.class);;
	static {
		LogFactory.useLog4JLogging();
	}

	@Test
	public void testInterface() {

		// MyBatis配置
		final Configuration configuration = getConfiguration();
		// 拦截器
		configuration.addInterceptor(new PageQueryInterceptor());

		final Executor executor = getExcutor(configuration);

		SqlSource sqlSource = new StaticSqlSource(configuration, "select * from emp");

		MappedStatement ms = new MappedStatement.Builder(configuration, "com.lrs.selectAll", sqlSource,
				SqlCommandType.SELECT).resultMaps(getResultMaps(configuration)).cache(getCache()).build();
		configuration.addMappedStatement(ms);
		SqlSession sqlSession = new DefaultSqlSession(configuration, executor, false);
		
		List<Employee> list = sqlSession.selectList("selectAll",new RowBounds(2,3));

		for (Employee employee : list) {
			log.info(employee.toString());
		}
		sqlSession.close();
	}

	// SQL 执行 Executor
	private Executor getExcutor(Configuration configuration) {
		// 数据源
		DataSource dataSource = getDataSource();
		// 事物管理
		Transaction transaction = new JdbcTransaction(dataSource, TransactionIsolationLevel.READ_COMMITTED, false);

		return configuration.newExecutor(transaction, ExecutorType.SIMPLE);
	}

	// Mybatis配置
	private Configuration getConfiguration() {
		final Configuration configuration = new Configuration();
		configuration.setCacheEnabled(true);
		configuration.setAggressiveLazyLoading(false);
		configuration.setLazyLoadingEnabled(false);
		configuration.setLogImpl(Log4jImpl.class);
		return configuration;
	}

	// 数据源
	private DataSource getDataSource() {
		Properties prop = null;

		try {
			prop = Resources.getResourceAsProperties("config/jdbc.properties");
		} catch (IOException e) {
			log.error("配置文件读取失败！", e);
			throw new RuntimeException(e);
		}
		// 数据源
		DataSource dataSource = new UnpooledDataSource(prop.getProperty("jdbc.driver"), prop.getProperty("jdbc.url"),
				prop.getProperty("jdbc.username"), prop.getProperty("jdbc.password"));
		return dataSource;
	}

	// 缓存
	private Cache getCache() {
		final Cache countryCache = new SynchronizedCache(
				new SerializedCache(new LoggingCache(new LruCache(new PerpetualCache("employee_cache")))));
		return countryCache;
	}

	// 返回映射
	private List<ResultMap> getResultMaps(Configuration config) {
		// 响应映射
		ResultMap resultMap = new ResultMap.Builder(config, "defaultResultMap", Employee.class,
				new ArrayList<ResultMapping>() {

					private static final long serialVersionUID = 1L;

					{
						add(new ResultMapping.Builder(config, "id", "empno", int.class).build());
						add(new ResultMapping.Builder(config, "name", "ename", String.class).build());
						add(new ResultMapping.Builder(config, "job", "job", String.class).build());
						add(new ResultMapping.Builder(config, "mgr", "mgr", int.class).build());
						add(new ResultMapping.Builder(config, "hiredate", "hiredate", java.sql.Date.class).build());
						add(new ResultMapping.Builder(config, "sal","sal",new BigDecimalTypeHandler()).build());
						add(new ResultMapping.Builder(config, "comm","comm",new BigDecimalTypeHandler()).build());
						add(new ResultMapping.Builder(config, "deptNo","dept_no",Integer.class).build());
					}
				}).build();

		List<ResultMap> resultMaps = new ArrayList<ResultMap>() {

			private static final long serialVersionUID = 1L;

			{
				add(resultMap);
			}
		};
		return resultMaps;
	}

	// 创建MappedStatement,Sql入参，Sql返回参数映射，注册的类型处理器
	private MappedStatement getMappedStatement(Configuration config, SqlSource sqlSource) {
		List<ParameterMapping> parameterMappings = new ArrayList<>();
		final TypeHandlerRegistry registry = config.getTypeHandlerRegistry();
		ParameterMapping parameterMapping = new ParameterMapping.Builder(config, "id",
				registry.getTypeHandler(int.class)).build();
		parameterMappings.add(parameterMapping);

		ParameterMap.Builder paramBuilder = new ParameterMap.Builder(config, "defaultParameterMap", Employee.class,
				parameterMappings);

		// 构造MappedStatement
		MappedStatement.Builder msBuilder = new MappedStatement.Builder(config, "com.lrs.selectEmployee", sqlSource,
				SqlCommandType.SELECT);
		msBuilder.parameterMap(paramBuilder.build());

		msBuilder.resultMaps(getResultMaps(config));
		msBuilder.cache(getCache());

		return msBuilder.build();
	}
}
