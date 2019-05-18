package com.openhack.security;
import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.domain.UserRole;

import java.util.List;

import com.openhack.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;
    
    

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	UserProfile userProfile = userDao.findByEmail(username);
    	if(userProfile==null)
    		throw new UsernameNotFoundException("User not found with username : " + username);
    	
    	UserAccount userAccount = userDao.findAccountByuserId(userProfile.getId());
    	List<UserRole> userRole = userDao.findAllRolesById(userProfile.getId());

        return UserPrincipal.create(userProfile, userAccount, userRole);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
    	UserProfile userProfile = userDao.findById(id);
    	if(userProfile==null)
    		throw new UsernameNotFoundException("User not found with id : " + id);
    	UserAccount userAccount = userDao.findAccountByuserId(userProfile.getId());
    	List<UserRole> userRole = userDao.findAllRolesById(userProfile.getId());

        return UserPrincipal.create(userProfile, userAccount, userRole);
    }
}