package com.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Message {
	 
	@JsonProperty("from")
    @Getter @Setter private String from;
	@JsonProperty("text")
    @Getter @Setter private String text;
	@JsonProperty("time")
    @Getter @Setter private String time;
 
}
