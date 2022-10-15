package com.numpyninja.lms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.numpyninja.lms.repository.entity.Batch;
import com.numpyninja.lms.repository.entity.Program;
import com.numpyninja.lms.exception.DatabaseOperationFailedException;
import com.numpyninja.lms.exception.IdNotFoundException;
import com.numpyninja.lms.model.RequestProgram;
import com.numpyninja.lms.model.ResponseBatch;
import com.numpyninja.lms.model.ResponseProgram;
import com.numpyninja.lms.repository.ProgramRepository;

@Service
public class ProgramService {

	@Autowired
	private ProgramRepository pgRepo;

	public ResponseProgram retrieveProgramWithBatchesByID(Integer program_id) {
		Optional<Program> opProgram = pgRepo.findById(program_id);
		if (!(opProgram.isPresent()))
			throw new IdNotFoundException("Could not find Program with id " + program_id);
		Program p = opProgram.get();
		List<Batch> batches = p.getBatches();
		List<ResponseBatch> resBatches = new ArrayList<ResponseBatch>();
		if (!(batches.isEmpty())) {
			for(Batch b : batches) 
				resBatches.add(new ResponseBatch(b.getBatch_id(), b.getBatch_name(), b.getBatch_desc(), b.getBatch_stat(), b.getBatch_no_of_classes()));
		}
		ResponseProgram resProgram = new ResponseProgram(p.getProgram_id(), p.getProgram_name(), p.getProgram_desc(), p.getProgram_stat(), resBatches);
		return resProgram;
	}

	public List<ResponseProgram> retrievePrograms() {
		List<Program> allPrograms = pgRepo.findAll();
		List<ResponseProgram> resPrograms = new ArrayList<ResponseProgram>();
		if (!(allPrograms.isEmpty())) {
			for(Program p : allPrograms) 
				resPrograms.add(new ResponseProgram(p.getProgram_id(), p.getProgram_name(), p.getProgram_desc(), p.getProgram_stat()));
		}
		return resPrograms;
	}

	public Optional<Program> retrieveProgramByID(Integer program_id) {
		return pgRepo.findById(program_id);
	}

	public void deleteProgramByID(Integer program_id) {
		pgRepo.deleteById(program_id);
	}

	public Integer addProgram (RequestProgram reqProgram) {
		Program newProgram = new Program();

		newProgram.setProgram_name(reqProgram.getProgram_name());
		newProgram.setProgram_stat(reqProgram.getProgram_stat());

		if(StringUtils.hasLength(reqProgram.getProgram_desc())) 
			newProgram.setProgram_desc(reqProgram.getProgram_desc());

		Integer savedProgramID;		
		try {
			savedProgramID = pgRepo.save(newProgram).getProgram_id();
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseOperationFailedException(e.getMessage());
		}
		return savedProgramID;
	}

	public ResponseProgram updateProgramByID (RequestProgram reqProgram, Program extProgram) {
		if(StringUtils.hasLength(reqProgram.getProgram_name()))
			extProgram.setProgram_name(reqProgram.getProgram_name());

		if(StringUtils.hasLength(reqProgram.getProgram_desc()))
			extProgram.setProgram_desc(reqProgram.getProgram_desc());

		if(StringUtils.hasLength(reqProgram.getProgram_stat()))
			extProgram.setProgram_stat(reqProgram.getProgram_stat());

		Program updatedProgram;
		ResponseProgram resProgram;
		try {
			updatedProgram = pgRepo.save(extProgram);
			resProgram = new ResponseProgram(updatedProgram.getProgram_id(), updatedProgram.getProgram_name(), updatedProgram.getProgram_desc(), updatedProgram.getProgram_stat());

		} catch(DataIntegrityViolationException e) {
			throw new DatabaseOperationFailedException(e.getMessage());
		}
		return resProgram;
	}

}
