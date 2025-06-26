package br.com.treinaweb.springbootapi.controller;

import java.util.List;
import java.util.Optional;

import br.com.treinaweb.springbootapi.entity.Pessoa;
import br.com.treinaweb.springbootapi.repository.PessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing Pessoa entities.
 * Provides endpoints for CRUD operations on Pessoa.
 */
@RestController
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    /**
     * Constructor for PessoaController.
     * @param pessoaRepository Repository for Pessoa entities
     */
    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * Returns a list of all pessoas.
     * @return List of Pessoa
     */
    @Operation(summary = "Retorna uma lista de pessoas", security = { @SecurityRequirement(name = "BearerAuthentication") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de pessoa"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @RequestMapping(value = "/pessoa", method = RequestMethod.GET, produces = "application/json")
    public List<Pessoa> Get() {
        return pessoaRepository.findAll();
    }

    /**
     * Returns a pessoa by its id.
     * @param id Pessoa id
     * @return ResponseEntity with Pessoa or 404 if not found
     */
    @Operation(summary = "Retorna uma pessoa", security = { @SecurityRequirement(name = "BearerAuthentication") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de pessoa"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Pessoa> GetById(@PathVariable(value = "id") long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent())
            return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a new pessoa.
     * @param pessoa Pessoa to create
     * @return Created Pessoa
     */
    @Operation(summary = "Cadastra uma pessoa", security = { @SecurityRequirement(name = "BearerAuthentication") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro cadastrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Registro não localizado"),
        @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @RequestMapping(value = "/pessoa", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Pessoa Post(@Valid @RequestBody Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    /**
     * Updates an existing pessoa by id.
     * @param id Pessoa id
     * @param newPessoa Pessoa data to update
     * @return ResponseEntity with updated Pessoa or 404 if not found
     */
    @Operation(summary = "Atualiza uma pessoa", security = { @SecurityRequirement(name = "BearerAuthentication") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Registro não localizado"),
        @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Pessoa> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Pessoa newPessoa) {
        Optional<Pessoa> oldPessoa = pessoaRepository.findById(id);
        if (oldPessoa.isPresent()) {
            Pessoa pessoa = oldPessoa.get();
            pessoa.setNome(newPessoa.getNome());
            pessoaRepository.save(pessoa);
            return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a pessoa by id.
     * @param id Pessoa id
     * @return ResponseEntity with status 200 if deleted, 404 if not found
     */
    @Operation(summary = "Deleta uma pessoa", security = { @SecurityRequirement(name = "BearerAuthentication") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não localizado"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            pessoaRepository.delete(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}