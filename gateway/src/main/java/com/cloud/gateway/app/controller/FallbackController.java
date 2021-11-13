package com.cloud.gateway.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

	
	
	@GetMapping("/fallback")
	public Mono<String> ping()
	{
		return Mono.just("fallback");
	}
	
	
}
