package com.cloud.cache.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonValue;
import com.hazelcast.map.IMap;
import com.hazelcast.query.impl.predicates.SqlPredicate;

@RestController
public class CacheController
{

    @Autowired
    HazelcastInstance hazelcastInstance;

    public static final String MAP_NAME = "map";
    

    @GetMapping("/put/partition")
    public ResponseEntity<String> put(@RequestHeader("cache") String cache, @RequestParam(value = "key") String key,
            @RequestParam(value = "value") String value)
    {
        String mapName = getMapName();
        IMap<String, String> map = hazelcastInstance.getMap(mapName);
        String oldValue = map.put(key, value);
        return ResponseEntity.ok(oldValue);
    }

    private String getMapName()
    {
        return MAP_NAME;
    }

    @PostMapping("/put")
    public ResponseEntity<String> post(@RequestBody String keyValueBody)
    {
        String mapName = getMapName();
        IMap<String, String> map = hazelcastInstance.getMap(mapName);
        JsonValue parse = Json.parse(keyValueBody);
        String key = parse.asObject().getString("key", "key");
        String value = parse.asObject().getString("value", "value");
        String oldValue = map.put(key, value);
        return ResponseEntity.ok(oldValue);
    }

    @PostMapping("/search")
    public ResponseEntity<List<String>> search(@RequestBody Filter filter)
    {
        String mapName = getMapName();
        IMap<String, String> map = hazelcastInstance.getMap(mapName);
        StringBuilder q = new StringBuilder();
        q.append(filter.getKey()).append(" LIKE '%").append(filter.getValue()).append("%'");
        Collection<String> values = map.values(new SqlPredicate(q.toString()));
        return ResponseEntity.ok(new ArrayList<>(values));
    }

    @GetMapping("/get")
    public ResponseEntity<String> get(@RequestParam(value = "key") String key)
    {
        String mapName = getMapName();
        IMap<String, String> map = hazelcastInstance.getMap(mapName);
        String value = map.get(key);
        if (value == null || value.length() == 0)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(value);
    }

    @PutMapping("/get")
    public ResponseEntity<String> getByKey(@RequestBody String keyBody)
    {
        String mapName = getMapName();
        IMap<String, String> map = hazelcastInstance.getMap(mapName);
        JsonValue parse = Json.parse(keyBody);
        String key = parse.asObject().getString("key", "key");
        String value = map.get(key);
        if (value == null || value.length() == 0)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(value);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam(value = "key") String key)
    {
        String mapName = getMapName();
        IMap<String, String> map = hazelcastInstance.getMap(mapName);
        String value = map.remove(key);
        return ResponseEntity.ok(value);
    }
    
    
    @PutMapping("/putWithEvictTime")
    public ResponseEntity<String> putWithEvictTime(@RequestBody String keyValueBody, @RequestParam("evictTime") Long evictTime) {
        String mapName = getMapName();
        IMap<String, String> map = hazelcastInstance.getMap(mapName);
        JsonValue parse = Json.parse(keyValueBody);
        String key = parse.asObject().getString("key", "key");
        String value = parse.asObject().getString("value", "value");
        String oldValue = map.put(key, value, evictTime, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok(oldValue);
    }



}