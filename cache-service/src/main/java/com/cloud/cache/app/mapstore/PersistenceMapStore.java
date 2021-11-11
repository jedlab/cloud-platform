package com.cloud.cache.app.mapstore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cloud.cache.app.CacheUtil;
import com.google.common.collect.Lists;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.MapLoaderLifecycleSupport;
import com.hazelcast.map.MapStore;
import com.jedlab.framework.spring.service.Trx;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersistenceMapStore implements MapStore<String, String>, MapLoaderLifecycleSupport {

	public static final String LOAD_SQL = "select c_value from t_cache where c_key = ? and map_name = ?";
	public static final String LOAD_ALL_SQL = "select c_key, c_value from t_cache WHERE map_name = ?  ";
	public static final String LOAD_ALL_KEYS_SQL = "select c_key from t_cache where map_name = ?";
	public static final String STORE_SQL = "insert into t_cache(c_key, c_value, map_name, c_date) values(?,?,?,?)";
	public static final String DELETE_SQL = "delete from  t_cache where c_key = ? and map_name = ?";

	private String mapName;
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionManager;
	private String hzName;

	public PersistenceMapStore(JdbcTemplate jdbcTemplate, PlatformTransactionManager ptm) {
		this.jdbcTemplate = jdbcTemplate;
		this.transactionManager = new TransactionTemplate(ptm);
		this.mapName = "PERSISTENCE";
	}

	@Override
	public String load(String key) {
		log.info("LOAD KEY {}", key);
		try {
			return jdbcTemplate.queryForObject(LOAD_SQL, new Object[] { key, mapName }, String.class);
		} catch (IncorrectResultSizeDataAccessException e) {
			log.info("IncorrectResultSizeDataAccessException {}", e.getMessage());
		}
		return null;
	}

	@Override
	public Map<String, String> loadAll(Collection<String> keys) {
		Map<String, String> map = new HashMap<String, String>();
		//
		List<Map<String, Object>> resultList = new ArrayList<>();
		StringBuilder sb = new StringBuilder(LOAD_ALL_SQL);
		if (keys != null && !keys.isEmpty()) {
			List<List<String>> partitions = Lists.partition(new ArrayList<>(keys), 999);
			for (List<String> keyList : partitions) {
				StringBuilder wh = new StringBuilder();
				int cnt = 0;
				for (Iterator<String> iterator = keyList.iterator(); iterator.hasNext();) {
					if (cnt > 0)
						wh.append(",");
					String k = (String) iterator.next();
					wh.append("'").append(CacheUtil.escapeSql(k)).append("'");
					cnt++;
				}
				sb.append(" AND c_key IN ( ").append(wh.toString()).append(" ) ");
				log.info("LOAD ALL {} with mapName {}", sb.toString(), mapName);
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(), new Object[] { mapName });
				if (list != null)
					resultList.addAll(list);
			}
		} else {
			log.info("LOAD ALL {} with mapName {}", sb.toString(), mapName);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(), new Object[] { mapName });
			if (list != null)
				resultList.addAll(list);
		}
		//
		resultList.forEach(item -> map.put("" + item.get("c_key"), "" + item.get("c_value")));
		return map;
	}

	@Override
	public Iterable<String> loadAllKeys() {
		List<String> resultList = jdbcTemplate.queryForList(LOAD_ALL_KEYS_SQL, new Object[] { mapName }, String.class);
		return resultList;
	}

	@Override
	public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
		hzName = hazelcastInstance.getName();
		log.info("init hazelcast {}", hzName);
	}

	@Override
	public void destroy() {
		log.info("destroy hazelcast {}", hzName);
	}

	@Override
	public void store(String key, String value) {
		log.info("Store Key {}", key);
		doInTrx((st) -> {
			jdbcTemplate.update(DELETE_SQL, new Object[] { key, mapName });
			jdbcTemplate.update(STORE_SQL,
					new Object[] { key, CacheUtil.escapeSql(value), mapName, CacheUtil.getCurrentDateSql() });
		});

	}

	private void doInTrx(Trx trx) {
		transactionManager.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				trx.doInTransaction(status);
			}
		});
	}

	@Override
	public void storeAll(Map<String, String> map) {
		for (Map.Entry<String, String> item : map.entrySet()) {
			doInTrx((st) -> {
				log.info("Store KEY {}", item.getKey());
				jdbcTemplate.update(DELETE_SQL, new Object[] { item.getKey(), mapName });
				jdbcTemplate.update(STORE_SQL, new Object[] { item.getKey(), CacheUtil.escapeSql(item.getValue()),
						mapName, CacheUtil.getCurrentDateSql() });
			});
		}

	}

	@Override
	public void delete(String key) {
		log.info("delete KEY {}", key);
		doInTrx((st) -> {
			jdbcTemplate.update(DELETE_SQL, new Object[] { key, mapName });
		});

	}

	@Override
	public void deleteAll(Collection<String> keys) {
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String k = (String) iterator.next();
			doInTrx((st) -> {
				log.info("delete ALL KEY {}", k);
				jdbcTemplate.update(DELETE_SQL, new Object[] { k, mapName });
			});
		}

	}

}