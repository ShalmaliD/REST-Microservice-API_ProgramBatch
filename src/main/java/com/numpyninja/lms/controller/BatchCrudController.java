package com.numpyninja.lms.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.numpyninja.lms.exception.BadRequestException;
import com.numpyninja.lms.exception.DataNotAvailableException;
import com.numpyninja.lms.exception.IdNotFoundException;
import com.numpyninja.lms.model.RequestBatch;
import com.numpyninja.lms.model.ResponseBatch;
import com.numpyninja.lms.repository.entity.Batch;
import com.numpyninja.lms.service.BatchService;

@RestController
@RequestMapping("/")
public class BatchCrudController {

	@Autowired
	private BatchService btService;

	/* GET: retrieve batches
	 * URL: /batches OR /batches?id=3
	 */
	@GetMapping("/batches")
	public ResponseEntity<Object> retrieveBatches(@RequestParam (value="id", required=false) Integer batch_id) {
		if (Optional.ofNullable(batch_id).orElse(0) > 0) {
			ResponseBatch resBatch = btService.retrieveBatchWithProgramByID(batch_id);
			return ResponseEntity.ok(resBatch);
		}
		else {
			List<ResponseBatch> batches = btService.retrieveBatches();
			if (batches.isEmpty())
				throw new DataNotAvailableException("No data available for Batches");
			return ResponseEntity.ok(batches);
		}
	}

	/* DELETE: delete batch by ID
	 * URL: /batches?id=3
	 */
	@DeleteMapping("/batches")
	public ResponseEntity<Object> deleteBatchByID(@RequestParam (value="id") Integer batch_id) {
		Optional<Integer> opBatchID = Optional.ofNullable(batch_id);
		if(!(opBatchID.isEmpty())) {
			Integer batchID = opBatchID.get();
			if (!(batchID > 0)) 
				throw new BadRequestException("Incorrect Value, Value for required request parameter 'id' should not be 0");

			try { 
				btService.deleteBatchByID(batch_id);
				return ResponseEntity.ok("Deleted Batch with id " + batch_id);
			} catch (Exception e) {
				throw new IdNotFoundException("Could not find Batch with id " + batch_id);
			}
		}
		else
			return ResponseEntity.badRequest().build();
	}

	/* POST: add batch
	 * URL: /batches 
	 */
	@PostMapping("/batches")
	public ResponseEntity<Object> addBatch (@Valid @RequestBody RequestBatch reqBatch) {
		RequestBatch vReqBatch = validateRequestBatch(reqBatch, "POST");
		Integer savedBatchID = btService.addBatch(vReqBatch);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().queryParam("id", savedBatchID).build().toUri();
		return ResponseEntity.created(location).build();
	}

	/* PUT: update batch by ID
	 * URL: /batches?id=3
	 */
	@PutMapping("/batches")
	public ResponseEntity<Object> updateBatchByID(@RequestParam (value="id") Integer batch_id, @RequestBody RequestBatch reqBatch) {
		Optional<Integer> opBatchID = Optional.ofNullable(batch_id);
		if(!(opBatchID.isEmpty())) {
			Integer batchID = opBatchID.get();
			if (!(batchID > 0)) 
				throw new BadRequestException("Incorrect Value, Value for required request parameter 'id' should not be 0");

			Optional<Batch> opBatch = btService.retrieveBatchByID(batchID);
			if (!(opBatch.isPresent()))
				throw new IdNotFoundException("Could not find Batch with id " + batchID);

			RequestBatch vReqBatch = validateRequestBatch(reqBatch, "PUT");
			ResponseBatch resBatch = btService.updateBatchByID(vReqBatch, opBatch.get());
			return ResponseEntity.ok(resBatch);
		}
		else
			return ResponseEntity.badRequest().build();
	}

	//validate request for POST and PUT 
	public RequestBatch validateRequestBatch(RequestBatch reqBatch, String method) {
		if (method.equals("PUT")) {
			if(StringUtils.hasLength(reqBatch.getBatch_name())) {
				if (!(StringUtils.hasLength(reqBatch.getBatch_name().trim()))) 
					throw new BadRequestException("Incorrect Value, Batch Name should not be blank");
			}
		}		

		Optional<Integer> opBatchProgramID = Optional.ofNullable(reqBatch.getBatch_program_id());
		if (opBatchProgramID.isEmpty()) {
			if (method.equals("POST"))
				throw new BadRequestException("Program ID for Batch is mandatory");
		}
		else {
			if (!(opBatchProgramID.get() > 0))
				throw new BadRequestException("Incorrect Value, Program ID for Batch should not be 0");
		}

		Optional<Integer> opClasses = Optional.ofNullable(reqBatch.getBatch_no_of_classes());
		if (opClasses.isEmpty()) {
			if (method.equals("POST"))
				reqBatch.setBatch_no_of_classes(1);
		}
		else {
			if (!(opClasses.get() > 0))
				throw new BadRequestException("Incorrect Value, No of Classes for Batch should not be 0");
		}

		if(StringUtils.hasLength(reqBatch.getBatch_stat())) {
			if (StringUtils.hasLength(reqBatch.getBatch_stat().trim())) {
				String batch_stat = reqBatch.getBatch_stat();
				if (!((batch_stat.equals("Active")) || (batch_stat.equals("Inactive"))))
					throw new BadRequestException("Incorrect Value, Batch Status should be Active or Inactive");
			}
			else
				throw new BadRequestException("Incorrect Value, Batch Status should not be blank");
		}
		else {
			if (method.equals("POST"))
				reqBatch.setBatch_stat("Active");
		}
		return reqBatch;
	}

}
