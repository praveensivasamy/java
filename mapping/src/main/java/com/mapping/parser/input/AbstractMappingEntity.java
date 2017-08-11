package com.mapping.parser.input;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.mapping.enums.MappingStatus;

@MappedSuperclass
public abstract class AbstractMappingEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private MappingStatus status;

	@Column(name = "UPLOADEDON", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date uploadTime;

	@Column(name = "TEMPLATE_FILE")
	private String uploadedFile;

	/**
	 * Decision maker if the {@link AbstractMappingEntity} is eligible to be
	 * persisted in DB
	 */
	public abstract boolean isSave();

	public MappingStatus getStatus() {
		return status;
	}

	public void setStatus(MappingStatus status) {
		this.status = status;
	}

	public String getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(String uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

}
