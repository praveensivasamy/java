package com.mapping.parser.input;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.mapping.enums.MappingStatus;

@MappedSuperclass
public class BaseMappingEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private MappingStatus status;

	public MappingStatus getStatus() {
		return status;
	}

	public void setStatus(MappingStatus status) {
		this.status = status;
	}
}
