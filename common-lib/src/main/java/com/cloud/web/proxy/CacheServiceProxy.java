package com.cloud.web.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.model.KeyValue;

@FeignClient(name = "CACHE-SERVICE")
public interface CacheServiceProxy {
	@PutMapping("/put")
	public String put(@RequestBody KeyValue keyValue);

	@GetMapping("/get")
	public String get(@RequestParam(value = "key") String key);
}
