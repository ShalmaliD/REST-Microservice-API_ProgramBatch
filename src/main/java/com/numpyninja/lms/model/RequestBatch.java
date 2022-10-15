package com.numpyninja.lms.model;

import javax.validation.constraints.NotBlank;

public class RequestBatch {

	@NotBlank(message="Batch Name is mandatory")
	String batch_name;

	String batch_desc;
	String batch_stat;
	Integer batch_program_id;
	Integer batch_no_of_classes;

	public RequestBatch() { }

	public RequestBatch(String batch_name, String batch_desc, String batch_stat, Integer batch_program_id, Integer batch_no_of_classes) {
		this.batch_name = batch_name;
		this.batch_desc = batch_desc;
		this.batch_stat = batch_stat;
		this.batch_program_id = batch_program_id;
		this.batch_no_of_classes = batch_no_of_classes;
	}

	public String getBatch_name() {
		return batch_name;
	}

	public String getBatch_desc() {
		return batch_desc;
	}

	public String getBatch_stat() {
		return batch_stat;
	}

	public Integer getBatch_program_id() {
		return batch_program_id;
	}

	public Integer getBatch_no_of_classes() {
		return batch_no_of_classes;
	}

	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}

	public void setBatch_desc(String batch_desc) {
		this.batch_desc = batch_desc;
	}

	public void setBatch_stat(String batch_stat) {
		this.batch_stat = batch_stat;
	}

	public void setBatch_program_id(Integer batch_program_id) {
		this.batch_program_id = batch_program_id;
	}

	public void setBatch_no_of_classes(Integer batch_no_of_classes) {
		this.batch_no_of_classes = batch_no_of_classes;
	}

	@Override
	public String toString() {
		return "RequestBatch [batch_name=" + batch_name + ", batch_desc=" + batch_desc + ", batch_stat=" + batch_stat
				+ ", batch_program_id=" + batch_program_id + ", batch_no_of_classes=" + batch_no_of_classes + "]";
	}

}
