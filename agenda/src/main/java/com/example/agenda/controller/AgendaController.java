package com.example.agenda.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.agenda.model.Contato;
import com.example.agenda.repository.ContatoRepository;

@RestController
@RequestMapping(path = "/api")
public class AgendaController {
   
   private ContatoRepository contatoRepository;

    @GetMapping
    public List<Contato> list(){
        return contatoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Contato> create(@RequestBody Contato contato){
        contatoRepository.save(contato);
        return new ResponseEntity<>(contato, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Optional<Contato>> getById(@PathVariable Long id){
        Optional<Contato> contato;
        try{
            contato = contatoRepository.findById(id);
            return new ResponseEntity<>(contato, HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Contato>> delete(@PathVariable Long id){
        
        try{
            contatoRepository.deleteById(id);
            return new ResponseEntity<Optional<Contato>>(HttpStatus.OK);
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<Optional<Contato>>(HttpStatus.NOT_FOUND);

        }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> update(@PathVariable Long id, @RequestBody Contato request){
        return contatoRepository.findById(id)
            .map(contato ->{
                contato.setNumero(request.getNumero());
                contato.setNome(request.getNome());
                Contato update = contatoRepository.save(contato);
                return ResponseEntity.ok().body(update);
            }).orElse(ResponseEntity.notFound().build());
      
    }

}
