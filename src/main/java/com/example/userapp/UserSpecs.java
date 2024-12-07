package com.example.userapp;

import com.example.userapp.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {
    public static Specification<User> firstNameEquals(String firstName) {
        return (root, query, builder) ->
                firstName == null ?
                        builder.conjunction() :
                        builder.equal(root.get("firstName"), firstName);
    }

    public static Specification<User> lastNameEquals(String lastName) {
        return (root, query, builder) ->
                lastName == null ?
                        builder.conjunction() :
                        builder.equal(root.get("lastName"), lastName);
    }

    public static Specification<User> emailEquals(String email) {
        return (root, query, builder) ->
                email == null ?
                        builder.conjunction() :
                        builder.equal(root.get("email"), email);
    }

    public static Specification<User> phoneEquals(String phone) {
        return (root, query, builder) ->
                phone == null ?
                        builder.conjunction() :
                        builder.equal(root.get("phone"), phone);
    }

    public static Specification<User> middleNameEquals(String middleName) {
        return (root, query, builder) ->
                middleName == null ?
                        builder.conjunction() :
                        builder.equal(root.get("middleName"), middleName);
    }

    public static Specification<User> designationEquals(String designation) {
        return (root, query, builder) ->
                designation == null ?
                        builder.conjunction() :
                        builder.equal(root.get("designation"), designation);
    }

    public static Specification<User> roleEquals(String role) {
        return (root, query, builder) ->
                role == null ?
                        builder.conjunction() :
                        builder.equal(root.get("role"), role);
    }

    public static Specification<User> companyIdEquals(String companyId) {
        return (root, query, builder) ->
                companyId == null ?
                        builder.conjunction() :
                        builder.equal(root.get("companyId"), companyId);
    }
}
