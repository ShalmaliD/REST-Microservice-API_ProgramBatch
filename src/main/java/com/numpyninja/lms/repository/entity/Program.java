package com.numpyninja.lms.repository.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_lms_program")
public class Program {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tbl_lms_program_program_id_seq")
	@SequenceGenerator(name="tbl_lms_program_program_id_seq", allocationSize=1)
	Integer program_id;

	String program_name;

	@Column(name="program_description")
	String program_desc;

	@Column(name="program_status")
	String program_stat;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="program", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Batch> batches;

	public Program() {}

	public Program(Integer program_id, String program_name, String program_desc, String program_stat,
			List<Batch> batches) {
		this.program_id = program_id;
		this.program_name = program_name;
		this.program_desc = program_desc;
		this.program_stat = program_stat;
		this.batches = batches;
	}

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	public void removeBatches() {
		batches.removeAll(batches);		
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

	@Override
	public String toString() {
		return "Program [program_id=" + program_id + ", program_name=" + program_name + ", program_desc=" + program_desc
				+ ", program_stat=" + program_stat + ", batches=" + batches + "]";
	}

}