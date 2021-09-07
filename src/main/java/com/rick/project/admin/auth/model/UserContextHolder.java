package com.rick.project.admin.auth.model;

/**
 * @author Rick
 * @createdAt 2021-04-08 14:37:00
 */
public class UserContextHolder {

    private static final ThreadLocal<UserDTO> currentUser = new ThreadLocal<>();

    public static void set(UserDTO userDTO) {
        currentUser.set(userDTO);
    }

    public static UserDTO get() {
        return currentUser.get();
    }

    public static void remove() {
        currentUser.remove();
    }

}
