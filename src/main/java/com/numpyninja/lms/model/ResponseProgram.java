package com.numpyninja.lms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseProgram {

	Integer program_id;
	String program_name;
	String program_desc;
	String program_stat;

	@JsonInclude(Include.NON_NULL)
	List<ResponseBatch> batches;

	public ResponseProgram() {}

	public ResponseProgram(Integer program_id, String program_name, String program_desc, String program_stat) {
		this.program_id = program_id;
		this.program_name = program_name;
		this.program_desc = program_desc;
		this.program_stat = program_stat;
	}

	public ResponseProgram(Integer program_id, String program_name, String program_desc, String program_stat, List<ResponseBatch> batches) {
		this.program_id = program_id;
		this.program_name = program_name;
		this.program_desc = program_desc;
		this.program_stat = program_stat;
		this.batches = batches;
	}

	public Integer getProgram_id() {
		return program_id;
	}

	public String getProgram_name() {
		return program_name;
	}

	public String getProgram_desc() {
		return program_desc;
	}

	public String getProgram_stat() {
		return program_stat;
	}

	public List<ResponseBatch> getBatches() {
		return batches;
	}

	public void setProgram_id(Integer program_id) {
		this.program_id = program_id;
	}

	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}

	public void setProgram_desc(String program_desc) {
		this.program_desc = program_desc;
	}

	public void setProgram_stat(String program_stat) {
		this.program_stat = program_stat;
	}

	public void setBatches(List<ResponseBatch> batches) {
		this.batches = batches;
	}

	@Override
	public String toString() {
		return "ResponseProgram [program_id=" + program_id + ", program_name=" + program_name + ", program_desc="
				+ program_desc + ", program_stat=" + program_stat + ", batches=" + batches + "]";
	}

}
