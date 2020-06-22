package com.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
public class Message {
	 
	@JsonProperty("from")
    @Getter @Setter private String from;
	@JsonProperty("text")
    @Getter @Setter private String text;
	@JsonProperty("time")
    @Getter @Setter private Long time;
	@JsonProperty("id")
    @Getter @Setter private String id;
	@JsonProperty("type")
	@Getter @Setter private String type;

	
}
