package com.fwm.petshop.api;

import com.fwm.petshop.domain.User;
import com.fwm.petshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.awt.image.ImageWatched;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/petshop")
public class PetshopController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String get() {
        return "Get api FWM para PetShop";
    }

    @PostMapping
    public String post() {
        return "Post api FWM para PetShop";
    }

    @PutMapping
    public String put() {
        return "Put api FWM para PetShop";
    }

    @DeleteMapping
    public String delete() {
        return "Delete api FWM para PetShop";
    }

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    private Map mapErro(String tipo) {
        switch (tipo) {
            case "GET":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 404);
                        put("mensagem", "Registro não encontrado!");
                    }
                };
            case "POST":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", "Não foi possível inserir o registro!");
                    }
                };
            case "PUT":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", "Não foi possível atualizar o registro!");
                    }
                };
            case "DELETE":
                return new LinkedHashMap<String, Object>() {
                    {
                        put("status", 400);
                        put("mensagem", "Não foi possível excluir o registro!");
                    }
                };
        }

        return new LinkedHashMap();
    }

//*****************************************
//    User (Usuário)
//*****************************************

    @GetMapping("/user")
    public ResponseEntity<Iterable<User>> getUser() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Integer id) {
        Optional<User> product = userService.getById(id);

        return product.isPresent() ?
                ResponseEntity.ok(product.get()) :
                new ResponseEntity<>(mapErro("GET"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user")
    public ResponseEntity postUser(@RequestBody User user) {
        try {
            User p = userService.insert(user);

            URI location = getUri(p.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("POST"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity putUser(@PathVariable("id") Integer id, @RequestBody User user) {
        user.setId(id);
        User p = userService.update(id, user);

        return p != null ?
                ResponseEntity.ok(p) :
                new ResponseEntity<>(mapErro("PUT"), HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        boolean ok = userService.delete(id);

        return ok ?
                ResponseEntity.ok().build() :
                new ResponseEntity<>(mapErro("DELETE"), HttpStatus.BAD_REQUEST);
    }

    //*****************************************
//    Login
//*****************************************
    @GetMapping("/login")
    public ResponseEntity getLogin(@RequestBody Map login) {
        try {
            User u = userService.findUser(login);

            if ((u == null) || (u.getId() <= 0)) {

                return new ResponseEntity<>(mapErro("GET"), HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(u);
        } catch (Exception ex) {
            return new ResponseEntity<>(mapErro("GET"), HttpStatus.BAD_REQUEST);
        }
    }


}
