package com.crai.platform.blobstorage.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties("spring.minio")
public class MinioConfigurationProperties {

	private String url;

	/**
	 * Access key (login) on Minio instance
	 */
	private String accessKey;

	/**
	 * Secret key (password) on Minio instance
	 */
	private String secretKey;

	/**
	 * If the scheme is not provided in {@code url} property, define if the
	 * connection is done via HTTP or HTTPS.
	 */
	private boolean secure;

	/**
	 * Bucket name for the application. The bucket must already exists on Minio.
	 */
	private String bucket;

	/**
	 * Metric configuration prefix which are registered on Actuator.
	 */
	private String metricName;

	/**
	 * Define the connect timeout for the Minio Client.
	 */
	private Duration connectTimeout = Duration.ofSeconds(10);

	/**
	 * Define the write timeout for the Minio Client.
	 */
	private Duration writeTimeout = Duration.ofSeconds(60);

	/**
	 * Define the read timeout for the Minio Client.
	 */
	private Duration readTimeout = Duration.ofSeconds(10);

	/**
	 * Check if the bucket exists on Minio instance. Settings this false will
	 * disable the check during the application context initialization. This
	 * property should be used for debug purpose only, because operations on Minio
	 * will not work during runtime.
	 */
	private boolean checkBucket = true;

	/**
	 * Will create the bucket if it do not exists on the Minio instance.
	 */
	private boolean createBucket = true;



}
