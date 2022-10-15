package com.numpyninja.lms.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_lms_batch")
public class Batch {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tbl_lms_batch_batch_id_seq")
	@SequenceGenerator(name="tbl_lms_batch_batch_id_seq", allocationSize=1)
	Integer batch_id;

	String batch_name;

	@Column(name="batch_description")
	String batch_desc;

	@Column(name="batch_status")
	String batch_stat;

	Integer batch_no_of_classes;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="batch_program_id", nullable=false)
	private Program program;

		public Batch() {}

	public Batch(Integer batch_id, String batch_name, String batch_desc, String batch_stat, Integer batch_no_of_classes) {
		this.batch_id = batch_id;
		this.batch_name = batch_name;
		this.batch_desc = batch_desc;
		this.batch_stat = batch_stat;
			this.batch_no_of_classes = batch_no_of_classes;
	}

	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
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
		return "Batch [batch_id=" + batch_id + ", batch_name=" + batch_name + ", batch_desc=" + batch_desc
				+ ", batch_stat=" + batch_stat + ", batch_no_of_classes=" + batch_no_of_classes + ", program=" + program
				+ "]";
	}

}
