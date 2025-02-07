package com.pramod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pramod.dto.LoginRequest;
import com.pramod.dto.Response;
import com.pramod.dto.UserDto;
import com.pramod.entity.User;
import com.pramod.exception.OurException;
import com.pramod.repo.UserRepository;
import com.pramod.service.interfac.IUserService;
import com.pramod.utils.JWTUtils;
import com.pramod.utils.Utils;
@Service
public class UserService implements IUserService{
	@Autowired
		private UserRepository userRepository;
	@Autowired
		private PasswordEncoder passwordEncoder;
	@Autowired
		private JWTUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Override
	public Response register(User user) {
		
		Response response=new Response();
		try {
			if(user.getRole()== null || user.getRole().isBlank()) {
				user.setRole("USER");
			}
			if(userRepository.existsByEmail(user.getEmail())) {
				throw new OurException(user.getEmail()+" user is Already exist");
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser=userRepository.save(user);
			UserDto userDto=Utils.mapUserEntityToUserDTO(savedUser);
			response.setStatusCode(200);
			response.setUser(userDto);
		} catch (OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during UesrResitration "+e.getMessage());
			
		}
		return response;
	}

	@Override
	public Response login(LoginRequest loginRequest) {
		Response response =new Response();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
			var user =userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new OurException("User not found"));
			var token=jwtUtils.generateToken(user);
			response.setStatusCode(200);
			response.setToken(token);
			response.setRole(user.getRole());
			response.setExpirationTime("7 Days");
			response.setMessage("successfull");
		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during UesrResitration "+e.getMessage());
			
			
		}
		return response;
	}

	@Override
	public Response getUserBookingHistory(String userId) {
		Response response=new Response();
		try {
			User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not Found"));
			UserDto userDto=Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
			response.setStatusCode(200);
			response.setUser(userDto);
			response.setMessage("SuccessFull");
		}catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during UesrResitration "+e.getMessage());
			
			
		}
		
		return response;
	}

	

	@Override
	public Response deleteUser(String userId) {
		Response response=new Response();
		try {
			userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not Found"));
			userRepository.deleteById(Long.valueOf(userId));
		
			response.setStatusCode(200);
			
			response.setMessage("SuccessFull");
		}catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during UesrResitration "+e.getMessage());
			
			
		}
		
		return response;
	}

	@Override
	public Response getUserById(String userId) {
		Response response=new Response();
		try {
		User user=	userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not Found"));
			UserDto userDto=Utils.mapUserEntityToUserDTO(user);
		
			response.setStatusCode(200);
			response.setUser(userDto);
			
			response.setMessage("SuccessFull");
		}catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during UesrResitration "+e.getMessage());
			
			
		}
		
		return response;
	}

	@Override
	public Response getMyInfo(String email) {
		Response response=new Response();
		try {
		User user=	userRepository.findByEmail(email).orElseThrow(()->new OurException("User not Found"));
			UserDto userDto=Utils.mapUserEntityToUserDTO(user);
		
			response.setStatusCode(200);
			response.setUser(userDto);
			
			response.setMessage("SuccessFull");
		}catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occured during UesrResitration "+e.getMessage());
			
			
		}
		
		return response;
	}

	@Override
	public Response getAllUser() {
		Response response=new Response();
		try {
			List<User> user=userRepository.findAll();
			List<UserDto> userDtoList=Utils.mapUserListEntityToUserListDTO(user);
			response.setStatusCode(200);
			response.setUserList(userDtoList);
			response.setMessage("SuccessFull");
		}
		catch (Exception e) {
			response.setStatusCode(404);
			response.setMessage("Error Occured during UesrResitration "+e.getMessage());
			
		}
		
		return response;
	}

	

	

}
