package com.revplay.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileStorageProperties {

    private String audioPath;
    private String imagePath;
}
