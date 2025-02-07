package com.pramod.service.interfac;

import com.pramod.dto.LoginRequest;
import com.pramod.dto.Response;
import com.pramod.entity.User;

public interface IUserService {
	
	Response register(User LoginRequest);
	Response login(LoginRequest loginRequest);
	Response getAllUser();
	Response getUserBookingHistory(String userId);
	Response deleteUser(String userid);
	Response getUserById(String userId);
	Response getMyInfo(String email);


}
