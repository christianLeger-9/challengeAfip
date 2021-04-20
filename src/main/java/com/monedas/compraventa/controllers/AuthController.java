package com.monedas.compraventa.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.monedas.compraventa.dto.JwtDto;
import com.monedas.compraventa.dto.LoginUsuario;
import com.monedas.compraventa.dto.UsuarioDTO;
import com.monedas.compraventa.entity.Rol;
import com.monedas.compraventa.entity.Usuario;
import com.monedas.compraventa.model.RolNombre;
import com.monedas.compraventa.security.jwt.JwtProvider;
import com.monedas.compraventa.service.RolService;
import com.monedas.compraventa.service.UsuarioService;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/")
    public ResponseEntity<?> crearUsuarioLogin(@RequestBody UsuarioDTO nuevoUsuario, BindingResult bindingResult) {
    	
        if(bindingResult.hasErrors()) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("Campos inválidos"));
        }
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("El nombre ya existe"));
        }
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("El email ya existe"));
        }
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        //todos los que se creen nuevos van a ser user solamente.
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity<>("usuario guardado", HttpStatus.CREATED);
    }
    
    @PostMapping("/crearRol")
    public ResponseEntity<?> crearRol(@RequestBody Rol rol, BindingResult bindingResult) {
    	
    	rol = new Rol(rol.getRolNombre());
        rolService.save(rol);
        return new ResponseEntity<>("Rol guardado", HttpStatus.CREATED);
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
    	JwtDto jwtDto = null;
    	if (loginUsuario.getNombreUsuario().equals(loginUsuario.getPassword())) {
	        Authentication authentication =
	                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
	        jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("Nombre de usuario o contraseña incorrectos"));
    	}
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
}
