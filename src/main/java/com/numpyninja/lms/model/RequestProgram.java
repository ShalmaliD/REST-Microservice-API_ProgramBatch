package com.numpyninja.lms.model;

import javax.validation.constraints.NotBlank;

public class RequestProgram {

	@NotBlank(message="Program Name is mandatory")
	String program_name;

	String program_desc;
	String program_stat;

	public RequestProgram() {}

	public RequestProgram(String program_name, String program_desc, String program_stat) {
		this.program_name = program_name;
		this.program_desc = program_desc;
		this.program_stat = program_stat;
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

	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}

	public void setProgram_desc(String program_desc) {
		this.program_desc = program_desc;
	}

	public void setProgram_stat(String program_stat) {
		this.program_stat = program_stat;
	}

	@Override
	public String toString() {
		return "RequestProgram [program_name=" + program_name + ", program_desc=" + program_desc + ", program_stat="
				+ program_stat + "]";
	}

}