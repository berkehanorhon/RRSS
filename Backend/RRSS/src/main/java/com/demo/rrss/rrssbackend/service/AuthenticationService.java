package com.demo.rrss.rrssbackend.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.UserBalance;
import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.repository.UserBalanceRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import com.demo.rrss.rrssbackend.rest.request.AuthenticationRequest;

@Service
public class AuthenticationService {
	@Autowired
	UsersRepository repository;

	@Autowired
	UserBalanceRepository balanceRepository;

    @Autowired
    private JwtUtil jwtUtil;

	public Users getUser(Long userId) {
		return repository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}

	public Users getUserByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}

	public void addUser(AuthenticationRequest request) {
		try {
			Users existingUser = getUserByUsername(request.getUsername());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username already exists");
		} catch (ResponseStatusException e) {
			if (!e.getReason().equals("User not found")) {
				throw e;
			}
		}

		Users user = new Users();
		user.setUsername(request.getUsername());
		user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setIsAdmin(false);
		user.setIsModerator(false);
		user.setIsMerchant(false);
		user.setEmail(request.getEmail());
		user.setReputation(0);
		user.setTokenExp(new java.sql.Timestamp(new Date().getTime()));
		user.setRegistrationDate(new java.sql.Timestamp(new java.util.Date().getTime()));

		String birthDateString = request.getBirthDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date birthDateUtil = format.parse(birthDateString);
			java.sql.Date birthDateSql = new java.sql.Date(birthDateUtil.getTime());
			user.setBirthDate(birthDateSql);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		repository.save(user);
		UserBalance balance = new UserBalance();
		balance.setBalance(new BigDecimal(0));
		balance.setUserId(getUserByUsername(request.getUsername()).getUserId());
		balanceRepository.save(balance);
	}

	public Users updateUser(Long userId, Users userDetails) {  // TODO yetki kontrolü eklenecek
		Users user = getUser(userId);
		user.setUsername(userDetails.getUsername());
		user.setPassword(userDetails.getPassword());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setBirthDate(userDetails.getBirthDate());
		user.setRegistrationDate(userDetails.getRegistrationDate());
		user.setIsAdmin(userDetails.getIsAdmin());
		user.setIsModerator(userDetails.getIsModerator());
		user.setIsMerchant(userDetails.getIsMerchant());
		user.setEmail(userDetails.getEmail());
		user.setReputation(userDetails.getReputation());
		user.setTokenExp(userDetails.getTokenExp());
		return repository.save(user);
	}

	public void deleteUser(Long userId) { // TODO yetki kontrolü eklenecek
		if (repository.existsById(userId)) {
			repository.deleteById(userId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
	}

	public Map<String, String> loginUser(AuthenticationRequest request) {
		Users existingUser = getUserByUsername(request.getUsername());

		if (existingUser != null && BCrypt.checkpw(request.getPassword(), existingUser.getPassword())) {
			Map<String, String> response = new HashMap<>();
			Date new_tokenExpDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
			Date cur_tokenExpDate = new Date(existingUser.getTokenExp().getTime());
			if (new Date().after(cur_tokenExpDate)){
				cur_tokenExpDate = new_tokenExpDate;
				existingUser.setTokenExp(new java.sql.Timestamp(cur_tokenExpDate.getTime()));
				repository.save(existingUser);
				UserBalance balance = balanceRepository.findById(existingUser.getUserId()).get();
				balance.setBalance(balance.getBalance().add(new BigDecimal(100)));
				balanceRepository.save(balance);
			}
			response.put("token", jwtUtil.generateToken(existingUser, cur_tokenExpDate));
			response.put("userId", existingUser.getUserId().toString());
			return response;
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
		}
	}
}