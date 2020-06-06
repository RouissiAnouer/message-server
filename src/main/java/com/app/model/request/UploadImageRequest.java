package com.app.model.request;

import java.sql.Blob;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class UploadImageRequest {
	
	@Getter @Setter private Blob image;
	@Getter @Setter private String username;

}
