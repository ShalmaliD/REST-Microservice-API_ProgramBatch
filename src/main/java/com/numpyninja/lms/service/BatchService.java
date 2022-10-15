package com.numpyninja.lms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.numpyninja.lms.exception.DatabaseOperationFailedException;
import com.numpyninja.lms.exception.IdNotFoundException;
import com.numpyninja.lms.model.RequestBatch;
import com.numpyninja.lms.model.ResponseBatch;
import com.numpyninja.lms.model.ResponseProgram;
import com.numpyninja.lms.repository.BatchRepository;
import com.numpyninja.lms.repository.entity.Batch;
import com.numpyninja.lms.repository.entity.Program;

@Service
public class BatchService {

	@Autowired
	private BatchRepository btRepo;

	public ResponseBatch retrieveBatchWithProgramByID(Integer batch_id) {
		Optional<Batch> opBatch = btRepo.findById(batch_id);
		if (!(opBatch.isPresent()))
			throw new IdNotFoundException("Could not find Batch with id " + batch_id);

		Batch b = opBatch.get();
		ResponseBatch resBatch = new ResponseBatch(b.getBatch_id(), b.getBatch_name(), b.getBatch_desc(), b.getBatch_stat(), b.getBatch_no_of_classes());
		resBatch.setResProgram(new ResponseProgram(b.getProgram().getProgram_id(), b.getProgram().getProgram_name(), b.getProgram().getProgram_desc(), b.getProgram().getProgram_stat()));
		return resBatch;
	}

	public List<ResponseBatch> retrieveBatches() {
		List<Batch> allBatches = btRepo.findAll();
		List<ResponseBatch> resBatches = new ArrayList<ResponseBatch>();
		if (!(allBatches.isEmpty())) {
			for(Batch b : allBatches) 
				resBatches.add(new ResponseBatch(b.getBatch_id(), b.getBatch_name(), b.getBatch_desc(), b.getBatch_stat(), b.getBatch_no_of_classes()));
		}
		return resBatches;
	}

	public Optional<Batch> retrieveBatchByID(Integer batch_id) {
		return btRepo.findById(batch_id);
	}

	public void deleteBatchByID(Integer batch_id) {
		btRepo.deleteById(batch_id);
	}

	public Integer addBatch (RequestBatch reqBatch) {
		Batch newBatch = new Batch();

		if(StringUtils.hasLength(reqBatch.getBatch_desc())) 
			newBatch.setBatch_desc(reqBatch.getBatch_desc());

		newBatch.setBatch_name(reqBatch.getBatch_name());
		newBatch.setBatch_stat(reqBatch.getBatch_stat());
		newBatch.setBatch_no_of_classes(reqBatch.getBatch_no_of_classes());

		Program p = new Program();
		p.setProgram_id(reqBatch.getBatch_program_id());
		newBatch.setProgram(p);

		Integer savedBatchID;
		try {
			savedBatchID = btRepo.save(newBatch).getBatch_id();
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseOperationFailedException(e.getMessage());
		}
		return savedBatchID;
	}

	public ResponseBatch updateBatchByID (RequestBatch reqBatch, Batch extBatch) {

		if(StringUtils.hasLength(reqBatch.getBatch_name())) 
			extBatch.setBatch_name(reqBatch.getBatch_name());

		if(StringUtils.hasLength(reqBatch.getBatch_desc())) 
			extBatch.setBatch_desc(reqBatch.getBatch_desc());

		if(StringUtils.hasLength(reqBatch.getBatch_stat())) 
			extBatch.setBatch_stat(reqBatch.getBatch_stat());

		if(Optional.ofNullable(reqBatch.getBatch_program_id()).orElse(0) > 0) {
			Program p = new Program();
			p.setProgram_id(reqBatch.getBatch_program_id());
			extBatch.setProgram(p);
		}

		if(Optional.ofNullable(reqBatch.getBatch_no_of_classes()).orElse(0) > 0)
			extBatch.setBatch_no_of_classes(reqBatch.getBatch_no_of_classes());

		Batch updatedBatch;
		ResponseBatch resBatch;
		try {
			updatedBatch = btRepo.save(extBatch);
			resBatch = new ResponseBatch(updatedBatch.getBatch_id(), updatedBatch.getBatch_name(), updatedBatch.getBatch_desc(), updatedBatch.getBatch_stat(), updatedBatch.getBatch_no_of_classes());
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseOperationFailedException(e.getMessage());
		}
		return resBatch;
	}

}
