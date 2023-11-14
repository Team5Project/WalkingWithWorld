package com.team5.WalkingWithWorld.global.config.auth;

public record CustomPrincipal(
        Long userId,
        String email,
        String name
) {
    public static CustomPrincipal of(Long userId, String email, String name) {
        return new CustomPrincipal(userId, email, name);
    }
}

