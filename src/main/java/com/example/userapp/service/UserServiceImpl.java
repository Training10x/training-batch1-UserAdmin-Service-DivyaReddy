package com.example.userapp.service;

import com.example.userapp.dto.StatusResponse;
import com.example.userapp.dto.UpdateDetails;
import com.example.userapp.exception.NotFoundException;
import com.example.userapp.service.impl.UserService;
import com.example.userapp.dto.SearchResponse;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.User;
import com.example.userapp.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.userapp.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }

    @Override
    public User createUser(User user) {
        System.out.println(userRepository);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public UserResponse updateUserStatus(Long userId, StatusResponse status) {
        User user1 = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("user with this id not found "));

        if(status.getStatus()=="enabled")
            user1.setStatusisEnabled(true);

        if(status.getStatus()=="disabled")
            user1.setStatusisEnabled(false);

        User savedUser = userRepository.save(user1);

        return userMapper.toDTO(savedUser);

    }

    @Override
    public UserResponse updateUser(Long userId, UpdateDetails updateUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if(updateUser.getEmail()!=null)
            user.setEmail(updateUser.getEmail());
        if(updateUser.getFirstName()!=null)
            user.setFirstName(updateUser.getFirstName());
        if(updateUser.getLastName()!=null)
            user.setLastName(updateUser.getLastName());
        if(updateUser.getMiddleName()!=null)
            user.setMiddleName(updateUser.getMiddleName());
        if(updateUser.getRole()!=null)
            user.setRole(updateUser.getRole());
        if(updateUser.getCustomerId()!=null)
            user.setCustomerId(updateUser.getCustomerId());
        if(updateUser.getDesignation()!=null)
            user.setDesignation(updateUser.getDesignation());
        if(updateUser.getPhone()!=null)
            user.setPhone(updateUser.getPhone());


        User newUser = userRepository.save(user);

        return userMapper.toDTO(newUser);



   }

    @Override
    public SearchResponse retrieveUsers(Integer page, Integer limit, String firstName, String lastName, String phone, String role) {
        // Default pagination values
        int currentPage = (page != null) ? page : 0;
        int pageSize = (limit != null) ? limit : 10;

        // Perform search with pagination
        Page<User> userPage = userRepository.findByFirstNameContainingAndLastNameContainingAndPhoneContainingAndRoleContaining(
                firstName != null ? firstName : "",
                lastName != null ? lastName : "",
                phone != null ? phone : "",
                role != null ? role : "",
                PageRequest.of(currentPage, pageSize)
        );

        // Map results to UserResponse DTOs
        List<UserResponse> userResponses = userPage.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        // Build SearchResponse DTO
        return SearchResponse.builder()
                .users(userResponses)
                .currentPage(currentPage)
                .pageCount(userPage.getTotalPages())
                .totalCount(userPage.getTotalElements())
                .build();
    }
//
//    public SearchResponse retrieveUsers(Integer page, Integer limit, String firstName, String lastName, String email, String phone, String middleName, String designation, String role, String companyId) {
//        Specification<User> specification = new Specification<User>() {
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                List<Predicate> predicates = new ArrayList<Predicate>();
//                if(!StringUtils.isEmpty(firstName)) {
//                    predicates.add(builder.equal(root.get("firstName"), firstName));
//                }
//                if(!StringUtils.isEmpty(lastName)) {
//                    predicates.add(builder.equal(root.get("lastName"), lastName));
//                }
//                if(!StringUtils.isEmpty(email)) {
//                    predicates.add(builder.equal(root.get("email"), email));
//                }
//                if(!StringUtils.isEmpty(phone)) {
//                    predicates.add(builder.equal(root.get("phone"), phone));
//                }
//                if(!StringUtils.isEmpty(middleName)) {
//                    predicates.add(builder.equal(root.get("middleName"), middleName));
//                }
//                if(!StringUtils.isEmpty(designation)) {
//                    predicates.add(builder.equal(root.get("designation"), designation));
//                }
//                if(!StringUtils.isEmpty(role)) {
//                    predicates.add(builder.equal(root.get("role"), role));
//                }
//                if(!StringUtils.isEmpty(companyId)) {
//                    predicates.add(builder.equal(root.get("customerId"), companyId));
//                }
//
//                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//
//
//        if(page == null) {
//            page = 0;
//        }
//
//        if(limit == null) {
//            limit = 1000;
//        }
//
//        Page<User> pageData = userRepository.findAll(specification, PageRequest.of(page, limit));
//        List<UserResponse> userResponse = pageData.toList().stream().map(userObj -> {
//          return UserResponse.builder()
//                  .id(userObj.getId())
//                  .firstName(userObj.getFirstName())
//                  .middleName(userObj.getMiddleName())
//                  .lastName(userObj.getLastName())
//                  .email(userObj.getEmail())
//                  .phone(userObj.getPhone())
//                  .designation(userObj.getDesignation())
//                  .role(userObj.getRole()).build();
//        }).toList();
//
//        return SearchResponse.builder()
//                .users(userResponse)
//                .currentPage(page)
//                .totalCount(pageData.getTotalElements())
//                .pageCount(pageData.getTotalPages())
//                .build();
//    }
}
