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

import com.numpyninja.lms.repository.entity.Program;
import com.numpyninja.lms.exception.BadRequestException;
import com.numpyninja.lms.exception.DataNotAvailableException;
import com.numpyninja.lms.exception.IdNotFoundException;
import com.numpyninja.lms.model.RequestProgram;
import com.numpyninja.lms.model.ResponseProgram;
import com.numpyninja.lms.service.ProgramService;

@RestController
@RequestMapping("/")
public class ProgramCrudController {

	@Autowired
	private ProgramService pgService;

	/* GET: retrieve programs
	 * URL: /programs OR /programs?id=3
	 */
	@GetMapping("/programs")
	public ResponseEntity<Object> retrievePrograms(@RequestParam (value="id", required=false) Integer program_id) {
		if (Optional.ofNullable(program_id).orElse(0) > 0) {
			ResponseProgram resProgram = pgService.retrieveProgramWithBatchesByID(program_id);
			return ResponseEntity.ok(resProgram);
		}
		else {
			List<ResponseProgram> programs = pgService.retrievePrograms();
			if (programs.isEmpty())
				throw new DataNotAvailableException("No data available for Programs");
			return ResponseEntity.ok(programs);
		}
	}

	/* DELETE: delete program by ID
	 * URL: /programs?id=3
	 */
	@DeleteMapping("/programs")
	public ResponseEntity<Object> deleteProgramByID(@RequestParam (value="id") Integer program_id) {
		Optional<Integer> opProgramID = Optional.ofNullable(program_id);
		if(!(opProgramID.isEmpty())) {
			Integer programID = opProgramID.get();
			if (!(programID > 0)) 
				throw new BadRequestException("Incorrect Value, Value for required request parameter 'id' should not be 0");

			try { 
				pgService.deleteProgramByID(program_id);
				return ResponseEntity.ok("Deleted Program with id " + program_id);
			} catch (Exception e) {
				throw new IdNotFoundException("Could not find Program with id " + program_id);
			}
		}
		else 			
			return ResponseEntity.badRequest().build();
	}

	/* POST: add program
	 * URL: /programs
	 */
	@PostMapping("/programs")
	public ResponseEntity<Object> addProgram (@Valid @RequestBody RequestProgram reqProgram) {
		RequestProgram vReqProgram = validateRequestProgram(reqProgram, "POST");
		Integer savedProgramID = pgService.addProgram(vReqProgram);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().queryParam("id", savedProgramID).build().toUri();
		return ResponseEntity.created(location).build();
	}

	/* PUT: update program by ID
	 * URL: /programs?id=3
	 */
	@PutMapping("/programs")
	public ResponseEntity<Object> updateProgramByID(@RequestParam (value="id") Integer program_id, @RequestBody RequestProgram reqProgram) {
		Optional<Integer> opProgramID = Optional.ofNullable(program_id);
		if(!(opProgramID.isEmpty())) {
			Integer programID = opProgramID.get();
			if (!(programID > 0)) 
				throw new BadRequestException("Incorrect Value, Value for required request parameter 'id' should not be 0");

			Optional<Program> opProgram = pgService.retrieveProgramByID(programID);
			if (!(opProgram.isPresent()))
				throw new IdNotFoundException("Could not find Program with id " + program_id);

			RequestProgram vReqProgram = validateRequestProgram(reqProgram, "PUT");
			ResponseProgram resProgram = pgService.updateProgramByID(vReqProgram, opProgram.get());
			return ResponseEntity.ok(resProgram);
		}
		else 			
			return ResponseEntity.badRequest().build();
	}

	//validate request for POST and PUT 
	public RequestProgram validateRequestProgram(RequestProgram reqProgram, String method) {
		if (method.equals("PUT")) {
			if(StringUtils.hasLength(reqProgram.getProgram_name())) {
				if (!(StringUtils.hasLength(reqProgram.getProgram_name().trim()))) 
					throw new BadRequestException("Incorrect Value, Program Name should not be blank");
			}
		}

		if(StringUtils.hasLength(reqProgram.getProgram_stat())) {
			if (StringUtils.hasLength(reqProgram.getProgram_stat().trim())) {
				String program_stat = reqProgram.getProgram_stat();
				if (!((program_stat.equals("Active")) || (program_stat.equals("Inactive"))))
					throw new BadRequestException("Incorrect Value, Program Status should be Active or Inactive");
			}
			else
				throw new BadRequestException("Incorrect Value, Program Status should not be blank");
		}
		else {
			if (method.equals("POST"))
				reqProgram.setProgram_stat("Active");
		}
		return reqProgram;
	}
}
