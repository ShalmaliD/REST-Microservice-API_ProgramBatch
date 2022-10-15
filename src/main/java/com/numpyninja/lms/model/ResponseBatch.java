package com.numpyninja.lms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseBatch {

	Integer batch_id;
	String batch_name;
	String batch_desc;
	String batch_stat;
	Integer batch_no_of_classes;

	@JsonInclude(Include.NON_NULL)
	ResponseProgram resProgram;

	public ResponseBatch() { }

	public ResponseBatch(Integer batch_id, String batch_name, String batch_desc, String batch_stat, Integer batch_no_of_classes) {
		this.batch_id = batch_id;
		this.batch_name = batch_name;
		this.batch_desc = batch_desc;
		this.batch_stat = batch_stat;
		this.batch_no_of_classes = batch_no_of_classes;
	}

	public ResponseProgram getResProgram() {
		return resProgram;
	}

	public void setResProgram(ResponseProgram resProgram) {
		this.resProgram = resProgram;
	}

	public Integer getBatch_id() {
		return batch_id;
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

	public Integer getBatch_no_of_classes() {
		return batch_no_of_classes;
	}

	public void setBatch_id(Integer batch_id) {
		this.batch_id = batch_id;
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

	public void setBatch_no_of_classes(Integer batch_no_of_classes) {
		this.batch_no_of_classes = batch_no_of_classes;
	}

	@Override
	public String toString() {
		return "ResponseBatch [batch_id=" + batch_id + ", batch_name=" + batch_name + ", batch_desc=" + batch_desc
				+ ", batch_stat=" + batch_stat + ", batch_no_of_classes=" + batch_no_of_classes + ", resProgram=" + resProgram + "]";
	}

}
