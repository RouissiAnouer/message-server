package com.app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class OutputMessage {
	 
    @Getter @Setter private String from;
    @Getter @Setter private String text;
    @Getter @Setter private String timeStamp;
    @Getter @Setter private String receiver;

 
}
